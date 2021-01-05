package com.rdand.webscrapper.document;

import java.io.Serializable;
import java.util.List;

public class PageMetaData implements Serializable {

	private static final long serialVersionUID = -135638188901574159L;

	private List<String> links;

	private List<String> images;

	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

}
