package com.rdand.webscrapper.service;

import com.rdand.webscrapper.document.PageMetaData;

public interface CacheService {

	public void cachePageMetaData(String url, PageMetaData pageMetaData);

	public Object getPageMetaData(String url);
}
