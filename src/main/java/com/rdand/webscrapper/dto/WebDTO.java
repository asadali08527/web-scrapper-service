package com.rdand.webscrapper.dto;

import java.util.List;

public class WebDTO {
	private String url;
	private List<Sections> sections;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Sections> getSections() {
		return sections;
	}

	public void setSections(List<Sections> sections) {
		this.sections = sections;
	}

}
