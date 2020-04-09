package edu.ccsu.cs595.capstone.scadservices.util;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SendMail {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

	private static final String host = "smtp.mailtrap.io";
	private static final int port = 25;
	private static final String fromEmail = "ccsumoviebooking@gmail.com";
	private static final String username = "ccsumoviebooking@gmail.com";
	private static final String password = "ccsu1234";
	

	public void sendMail(SCADMail mail) {

		Properties prop = new Properties();
		
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);

	    Session session = Session.getInstance(prop, new Authenticator() {
	    	@Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
	    });
	  session.setDebug(true);

	   try {

	       Message message = new MimeMessage(session);
	       message.setFrom(new InternetAddress(fromEmail));
	       message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getRecipient()));
	       message.setSubject(mail.getSubject());

	       String msg = "This is my first email using JavaMailer";

	       MimeBodyPart mimeBodyPart = new MimeBodyPart();
	       mimeBodyPart.setContent(msg, "text/html");

	       MimeBodyPart attachmentBodyPart = new MimeBodyPart();
	       attachmentBodyPart.attachFile(new File("pom.xml"));

	       Multipart multipart = new MimeMultipart();
	       multipart.addBodyPart(mimeBodyPart);
	       multipart.addBodyPart(attachmentBodyPart);

	       message.setContent(multipart);

	       Transport.send(message);

	 } catch (Exception e) {
	     e.printStackTrace();
	 }
	}

}
