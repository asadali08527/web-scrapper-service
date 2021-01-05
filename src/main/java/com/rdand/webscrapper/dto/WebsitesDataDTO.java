package com.rdand.webscrapper.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WebsitesDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -135338101574159L;

	private Long id;

	private String name;

	private String contents;

	private String url;

	private PageMetaDataDTO metadata;

	private List<WebsitesDataDTO> pages;

	private Date createdDate;

	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PageMetaDataDTO getMetadata() {
		return metadata;
	}

	public void setMetadata(PageMetaDataDTO metadata) {
		this.metadata = metadata;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<WebsitesDataDTO> getPages() {
		return pages;
	}

	public void setPages(List<WebsitesDataDTO> pages) {
		this.pages = pages;
	}

}
