package com.rdand.webscrapper.event.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.rdand.webscrapper.document.Websites;
import com.rdand.webscrapper.service.impl.SequenceGeneratorServiceImpl;

@Component
public class WebsiteEntityListener extends AbstractMongoEventListener<Websites> {

	private SequenceGeneratorServiceImpl sequenceGenerator;

	@Autowired
	public WebsiteEntityListener(SequenceGeneratorServiceImpl sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Websites> event) {
		if (event.getSource().getId() < 1) {
			event.getSource().setId(sequenceGenerator.generateSequence(Websites.SEQUENCE_NAME));
		}
	}

}
