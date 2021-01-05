package com.rdand.webscrapper.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.rdand.webscrapper.document.PageMetaData;

@Repository
public class RedisRepository {

	@Autowired
	private RedisTemplate<String, PageMetaData> redisTemplate;

	public void cachePageMetaData(String key, PageMetaData value) {
		redisTemplate.opsForValue().set(key, value);
		// redisTemplate.expire(key, 60, TimeUnit.SECONDS);
	}

	public Object getPageMetaData(String key) {
		return redisTemplate.opsForValue().get(key);
	}

}
