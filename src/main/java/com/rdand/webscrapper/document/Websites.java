package com.rdand.webscrapper.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author Asad.ali
 *
 */
@Document(collection = "websites")
public class Websites implements Serializable {

	private static final long serialVersionUID = -1353383638101574159L;

	@Transient
	public static final String SEQUENCE_NAME = "website_sequence";

	@Id
	private long id;

	private String contents;

	@Indexed(unique = true)
	private String url;

	private PageMetaData metadata;

	private Date createdDate;

	public Websites() {
	}

	public Websites(String contents, String url, PageMetaData metadata) {
		super();
		this.contents = contents;
		this.url = url;
		this.metadata = metadata;
		this.createdDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Websites [id=" + id + ", contents=" + contents + ", url=" + url + ", metadata=" + metadata
				+ ", createdDate=" + createdDate + "]";
	}

}
