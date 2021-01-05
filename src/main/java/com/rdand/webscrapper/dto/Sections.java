package com.rdand.webscrapper.dto;

import java.util.List;

public class Sections {
	private String name;
	private String url;
	private boolean processAllLink;
	private List<Sections> subSections;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isProcessAllLink() {
		return processAllLink;
	}

	public void setProcessAllLink(boolean processAllLink) {
		this.processAllLink = processAllLink;
	}

	public List<Sections> getSubSections() {
		return subSections;
	}

	public void setSubSections(List<Sections> subSections) {
		this.subSections = subSections;
	}

	@Override
	public String toString() {
		return "Sections [name=" + name + ", url=" + url + ", processAllLink=" + processAllLink + "]";
	}

}
