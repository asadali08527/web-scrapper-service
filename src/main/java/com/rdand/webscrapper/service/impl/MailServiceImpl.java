package com.rdand.webscrapper.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class MailServiceImpl {

	@Value("${mail.username}")
	private String username;

	@Value("${mail.password}")
	private String password;

	private static Properties buildProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		// props.put("http_proxy", "https://test1:com%401234@172.19.7.14:8080");

		return props;
	}

	public Properties getProperties() {
		Properties props = new Properties();
		return props;
	}

	public void sendMailTLS(String to, String subject, String mail_file_location, String mail_file_name,
			Map<String, Object> variables) throws IOException, TemplateException, MessagingException {
		String body = buildTemplate(mail_file_location, mail_file_name, variables);
		sendMailTLS(to, subject, body);
	}

	public String buildTemplate(String mail_file_location, String mail_file_name, Map<String, Object> variables)
			throws IOException, TemplateException {
		Configuration cfg = new Configuration();
		try (Writer out = new StringWriter()) {
			cfg.setDirectoryForTemplateLoading(new File(mail_file_location));
			Template template = cfg.getTemplate(mail_file_name);
			template.process(variables, out);
			return out.toString();
		}
	}

	public void sendMailTLS(String to, String subject, String body) throws MessagingException {
		Properties props = buildProperties();

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(to));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);

		BodyPart bodypart = new MimeBodyPart();
		bodypart.setContent(body, "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(bodypart);

		message.setContent(multipart, "text/html");

		Transport.send(message);
	}

	public void sendMailTLS(String to, String subject, String mail_file_location, String mail_file_name,
			Map<String, Object> variables, File fileToBeAttached)
			throws MessagingException, IOException, TemplateException {
		Properties props = buildProperties();
		String body = buildTemplate(mail_file_location, mail_file_name, variables);
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(to));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);

		BodyPart bodypart = new MimeBodyPart();
		bodypart.setContent(body, "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(bodypart);

		DataSource source = new FileDataSource(fileToBeAttached); // ex : "C:\\test.pdf"
		MimeBodyPart attachmentBodyPart = new MimeBodyPart();
		attachmentBodyPart.setDataHandler(new DataHandler(source));
		attachmentBodyPart.setFileName(fileToBeAttached.getName());

		multipart.addBodyPart(attachmentBodyPart); // add the attachement part

		message.setContent(multipart, "text/html");

		Transport.send(message);
	}

	public void sendMailTLS(String to, String subject, String body, List<File> filesToBeAttached)
			throws MessagingException {
		Properties props = buildProperties();

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(to));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);

		BodyPart bodypart = new MimeBodyPart();
		bodypart.setContent(body, "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(bodypart);

		if (filesToBeAttached != null) {
			for (File fileToBeAttached : filesToBeAttached) {
				DataSource source = new FileDataSource(fileToBeAttached); // ex : "C:\\test.pdf"
				MimeBodyPart attachmentBodyPart = new MimeBodyPart();
				attachmentBodyPart.setDataHandler(new DataHandler(source));
				attachmentBodyPart.setFileName(fileToBeAttached.getName());
				multipart.addBodyPart(attachmentBodyPart); // add the attachement part
			}
		}

		message.setContent(multipart, "text/html");

		Transport.send(message);
	}
}
