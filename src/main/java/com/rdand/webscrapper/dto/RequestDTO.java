package com.rdand.webscrapper.dto;

import java.io.Serializable;
import java.util.List;

public class RequestDTO implements Serializable {
	private List<WebDTO> websites;

	public List<WebDTO> getWebsites() {
		return websites;
	}

	public void setWebsites(List<WebDTO> websites) {
		this.websites = websites;
	}

}
