package com.rdand.webscrapper.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScraperHelper {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private String pageUrl;
	private Integer pageParseTimeoutMillis;
	private List<String> detailsSearchTags;
	private List<String> linksSearchTags;

	public ScraperHelper(String pageUrl, Integer pageParseTimeoutMillis, List<String> detailsSearchTags,
			List<String> linksSearchTags) {
		super();
		this.pageUrl = pageUrl;
		this.pageParseTimeoutMillis = pageParseTimeoutMillis;
		this.detailsSearchTags = detailsSearchTags;
		this.linksSearchTags = linksSearchTags;
	}

	public CompletableFuture<List<Map<String, String>>> fetchAllLinkMetaDetailsFromPage() {
		List<Map<String, String>> metaDetailsList = new ArrayList<>();
		return CompletableFuture.supplyAsync(() -> {
			try {
				Set<String> links = getAllLinksFromPage();
				return links;
			} catch (Exception e) {
				log.error("err", e);
			}
			return null;
		}).thenApplyAsync(links -> {
			links.forEach(link -> {
				CompletableFuture<Map<String, String>> detailsFuture = CompletableFuture.supplyAsync(() -> {
					try {
						return getLinkDetails(link);
					} catch (Exception e) {
						log.error("err", e);
					}
					return null;
				});

				try {
					metaDetailsList.add(detailsFuture.get());
				} catch (Exception e) {
					log.error("err", e);
				}
			});

			return metaDetailsList;
		}).toCompletableFuture();

	}

	private Map<String, String> getLinkDetails(String url) throws IOException {
		Document doc = Jsoup.parse(new URL(url), pageParseTimeoutMillis);
		Map<String, String> tagDetails = new HashMap<>();
		detailsSearchTags.forEach(tag -> {
			tagDetails.put(tag, doc.select(tag).get(0).attr("content"));
		});
		return tagDetails;
	}

	private Set<String> getAllLinksFromPage() throws MalformedURLException, IOException {
		Document doc = Jsoup.parse(new URL(pageUrl), pageParseTimeoutMillis);
		return searchLinkTags(doc, linksSearchTags);
	}

	private Set<String> searchLinkTags(Document doc, List<String> searchTags) {
		Set<String> links = new HashSet<>();
		searchTags.forEach(tag -> {
			Elements elems = doc.select(tag);
			elems.forEach(e -> {
				System.out.println(e);
//				links.add(e.select("a[href]").attr("href"));
			});
		});
		return links;
	}

}
