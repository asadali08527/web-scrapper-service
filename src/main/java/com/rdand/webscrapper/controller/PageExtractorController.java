package com.rdand.webscrapper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rdand.webscrapper.service.PageExtractorService;

@RestController
@RequestMapping("/web")
public class PageExtractorController {
	@Autowired
	private PageExtractorService pageExtractorService;

	// Search articles by author name
	@RequestMapping(value = "/extract", method = RequestMethod.POST)
	public ResponseEntity<?> scrapWebPages(@RequestParam String url) {
		try {
			pageExtractorService.extractAndMail(url);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
}
