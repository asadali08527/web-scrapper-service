package com.rdand.webscrapper.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rdand.webscrapper.document.WebPages;
import com.rdand.webscrapper.document.Websites;
import com.rdand.webscrapper.repository.WebpagesRepository;
import com.rdand.webscrapper.repository.WebsitesRepository;
import com.rdand.webscrapper.service.CacheService;
import com.rdand.webscrapper.service.PageExtractorService;

@Service
public class PageExtractorServiceImpl implements PageExtractorService {

	@Autowired
	private CacheService cacheService;

	@Autowired
	private WebsitesRepository websitesRepository;

	@Autowired
	private WebpagesRepository webpagesRepository;

	@Autowired
	private MailServiceImpl mailServiceImpl;

	@Value("${mail.to}")
	private String to;

	@Value("${template.path}")
	private String templatePath;

	@Value("${file.output.path}")
	private String outputPath;

	@Override
	public void extractAndMail(String url) throws Exception {
		if (cacheService.getPageMetaData(url) == null)
			throw new Exception();
		List<WebPages> webPages = webpagesRepository.findByRootUrl(url);
		Websites websites = websitesRepository.findByUrl(url);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("url", url);
		jsonObject.put("content", websites.getContents());
		jsonObject.put("links", websites.getMetadata().getLinks());
		jsonObject.put("images", websites.getMetadata().getImages());
		jsonObject.put("pages", webPages);
		try {
			String path = outputPath + "output_" + System.currentTimeMillis() + ".json";
			FileWriter file = new FileWriter(path);
			file.write(jsonObject.toJSONString());
			Map<String, Object> variables = new HashMap<>();
			variables.put("url", url);
			mailServiceImpl.sendMailTLS(to, "Scrapped Content", templatePath, "scrapAttachmentTemplate.ftl", variables,
					new File(path));
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
