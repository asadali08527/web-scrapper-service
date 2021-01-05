package com.rdand.webscrapper.service;

import java.util.List;

import com.rdand.webscrapper.dto.WebDTO;

public interface ScraperService {

	void reScrap(List<WebDTO> webDTOList, boolean reconcile);

	void scrap(List<WebDTO> webDTOList);
}
