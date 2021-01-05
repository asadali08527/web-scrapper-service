package com.rdand.webscrapper.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "webpages")
public class WebPages {

	private static final long serialVersionUID = -135638101574159L;

	@Transient
	public static final String SEQUENCE_NAME = "web_pages_sequence";

	@Id
	private long id;

	@Indexed
	private String rootUrl;

	private String pageName;

	private String pageURL;

	private String contents;

	private PageMetaData metadata;

	private Date createdDate;

	public WebPages() {
	}

	public WebPages(String rootUrl, String pageName, String pageUrl, String contents, PageMetaData metadata) {
		super();
		this.rootUrl = rootUrl;
		this.pageName = pageName;
		this.setPageURL(pageUrl);
		this.contents = contents;
		this.metadata = metadata;
		this.createdDate = new Date();
	}

	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public PageMetaData getMetadata() {
		return metadata;
	}

	public void setMetadata(PageMetaData metadata) {
		this.metadata = metadata;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
