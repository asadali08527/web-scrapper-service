package com.rdand.webscrapper.event.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.rdand.webscrapper.document.WebPages;
import com.rdand.webscrapper.document.Websites;
import com.rdand.webscrapper.service.impl.SequenceGeneratorServiceImpl;

@Component
public class WebPagesEntityListener extends AbstractMongoEventListener<WebPages> {

	private SequenceGeneratorServiceImpl sequenceGenerator;

	@Autowired
	public WebPagesEntityListener(SequenceGeneratorServiceImpl sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public void onBeforeConvert(BeforeConvertEvent<WebPages> event) {
		if (event.getSource().getId() < 1) {
			event.getSource().setId(sequenceGenerator.generateSequence(WebPages.SEQUENCE_NAME));
		}
	}

}
