package com.rdand.webscrapper.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rdand.webscrapper.dto.WebDTO;
import com.rdand.webscrapper.service.ScraperService;

@RestController
@RequestMapping("/web")
public class ScraperController {

	@Autowired
	ScraperService scraperService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/scrap", method = RequestMethod.POST)
	public ResponseEntity<?> scrapWebPages(@RequestBody List<WebDTO> webDTOList) {
		logger.info("/scrap request recieved for input body={}", webDTOList);
		scraperService.scrap(webDTOList);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = "/scrap/reconcile", method = RequestMethod.POST)
	public ResponseEntity<?> reconcile(@RequestBody List<WebDTO> webDTOList) {
		logger.info("/scrap/reconcile request recieved for input body={}", webDTOList);
		scraperService.reScrap(webDTOList, true);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
