package com.rdand.webscrapper.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rdand.webscrapper.document.PageMetaData;
import com.rdand.webscrapper.repository.RedisRepository;
import com.rdand.webscrapper.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService {

	@Autowired
	private RedisRepository redisRepository;

	@Override
	public void cachePageMetaData(String url, PageMetaData pageMetaData) {
		redisRepository.cachePageMetaData(url, pageMetaData);
	}

	@Override
	public Object getPageMetaData(String url) {
		return redisRepository.getPageMetaData(url);
	}

}
