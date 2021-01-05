package com.rdand.webscrapper.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rdand.webscrapper.document.WebPages;

@Repository
public interface WebpagesRepository extends MongoRepository<WebPages, Long> {

	WebPages findByPageURL(String url);

	List<WebPages> findByRootUrl(String url);

}