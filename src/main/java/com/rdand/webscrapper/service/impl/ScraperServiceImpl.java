package com.rdand.webscrapper.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rdand.webscrapper.document.PageMetaData;
import com.rdand.webscrapper.document.WebPages;
import com.rdand.webscrapper.document.Websites;
import com.rdand.webscrapper.dto.Sections;
import com.rdand.webscrapper.dto.WebDTO;
import com.rdand.webscrapper.repository.WebpagesRepository;
import com.rdand.webscrapper.repository.WebsitesRepository;
import com.rdand.webscrapper.service.CacheService;
import com.rdand.webscrapper.service.ScraperService;

@Service
public class ScraperServiceImpl implements ScraperService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WebsitesRepository websitesRepository;

	@Autowired
	private WebpagesRepository webpagesRepository;

	@Autowired
	private CacheService cacheService;

	@Override
	public void scrap(List<WebDTO> webDTOList) {
		webDTOList.forEach(e -> scrapWebsite(e, false));
	}

	@Override
	public void reScrap(List<WebDTO> webDTOList, boolean reconcile) {
		webDTOList.forEach(e -> scrapWebsite(e, reconcile));
	}

	@Transactional
	private void scrapWebsite(WebDTO webDTO, boolean isReconcile) {
		if (webDTO.getUrl() != null) {
			try {
				String url = webDTO.getUrl();
				if (cacheService.getPageMetaData(url) == null || isReconcile) {
					Document document = Jsoup.connect(url).get();
					if (document != null) {
						Websites website = prepareWebsite(url, document);
						website = websitesRepository.save(website);
						cacheService.cachePageMetaData(url, website.getMetadata());
						scrapPages(url, document, webDTO.getSections(), isReconcile);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error("Exception raised while scrapping website={}, error={}", webDTO.getUrl(), e.getMessage());
			}
		}
	}

	private void scrapPages(String rootUrl, Document document, List<Sections> sections, boolean isReconcile) {
		for (Sections section : sections) {
			LOGGER.info("Iterating section {}", section);
			scrapWebPages(rootUrl, document, section, isReconcile);
		}
	}

	private Websites prepareWebsite(String url, Document document) {

		Websites websites = websitesRepository.findByUrl(url);
		if (websites == null)
			websites = new Websites();
		websites.setUrl(url);
		websites.setContents(document.body().outerHtml());
		websites.setMetadata(prepareMetaData(document));
		return websites;
	}

	private void scrapWebPages(String rootUrl, Document document, Sections section, boolean reconcile) {
		List<WebPages> webPages = new ArrayList<WebPages>();
		Elements linksOnPage = document.select("a[href]");
		linksOnPage.forEach(page -> {
			if (page != null) {
				if (section != null && (section.isProcessAllLink()
						|| (page.hasText() && page.text().equalsIgnoreCase(section.getName())
								&& neitherBlankNorNull(page.attr("abs:href"))))) {
					LOGGER.info("Since to process All link, so iterating next link {}", page.attr("abs:href"));
					try {
						String url = page.attr("abs:href");
						if (cacheService.getPageMetaData(url) == null || reconcile) {
							WebPages webPage = prepareWebPage(url, section.getName(), rootUrl);
							cacheService.cachePageMetaData(url, webPage.getMetadata());
							webPages.add(webPage);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		webpagesRepository.saveAll(webPages);

	}

	private WebPages prepareWebPage(String url, String pageName, String rootUrl) throws IOException {
		Document subDocument = Jsoup.connect(url).get();
		WebPages webPage = webpagesRepository.findByPageURL(url);
		if (webPage == null)
			webPage = new WebPages();
		webPage.setRootUrl(rootUrl);
		webPage.setPageURL(url);
		webPage.setMetadata(prepareMetaData(subDocument));
		webPage.setContents(subDocument.outerHtml());
		webPage.setCreatedDate(new Date());
		webPage.setPageName(pageName);
		return webPage;
	}

	private boolean neitherBlankNorNull(String attr) {
		return attr != null && !attr.isEmpty();
	}

	private PageMetaData prepareMetaData(Document document) {
		List<String> images = new ArrayList<String>();
		List<String> links = new ArrayList<String>();
		Elements media = document.select("[src]");
		media.forEach(e -> {
			if (neitherBlankNorNull(e.attr("abs:src")))
				images.add(e.attr("abs:src"));
		});
		Elements elementLinks = document.select("a[href]");
		elementLinks.forEach(e -> {
			if (neitherBlankNorNull(e.attr("abs:href")))
				links.add(e.attr("abs:href"));
		});
		PageMetaData pageMetaData = new PageMetaData();
		pageMetaData.setImages(images);
		pageMetaData.setLinks(links);
		return pageMetaData;
	}

}
