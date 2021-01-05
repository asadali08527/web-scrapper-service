package com.rdand.webscrapper.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rdand.webscrapper.document.Websites;

@Repository
public interface WebsitesRepository extends MongoRepository<Websites, Long> {

	Websites findByUrl(String url);

}