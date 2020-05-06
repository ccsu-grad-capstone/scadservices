package edu.ccsu.cs595.capstone.scadservices.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ccsu.cs595.capstone.scadservices.service.DashboardService;

/**
 * 
 * Email adapter to email SCAD managers.
 * 
 * @author kapperar
 *
 */

public class GoogleSMTPEmailAdapter implements EmailAdapter {
	
	private static final Logger LOG = LoggerFactory.getLogger(DashboardService.class);
	
	private static final String FROMEMAILDN = "SCAD Admin Team";
	private static final String FROMEMAIL = "scad650@gmail.com";
	private static final String EMAILUSER = "scad650@gmail.com";
	private static final String PASSWORD = "scad1234!";
	private static final String SMTPHOST = "smtp.gmail.com";
	private static final String SMTPPORT = "465";
	private static final String TOEMAILLOCAL = "krameshreddy@yahoo.com";
	
	public void sendEmail(SCADMail mail) throws RuntimeException {
		
		String toEmail = null;
		Properties props = new Properties();

		props.put("mail.smtp.user", FROMEMAIL);
		props.put("mail.smtp.host", SMTPHOST);
		props.put("mail.smtp.port", SMTPPORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", SMTPPORT);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		SMTPAuthenticator auth = new SMTPAuthenticator(EMAILUSER, PASSWORD);
		Session session = Session.getInstance(props, auth);
		session.setDebug(true);
		
		if (Objects.isNull(mail.getRecipient())) {
			toEmail = TOEMAILLOCAL;
		} else {
			toEmail = mail.getRecipient();
		}
		String[] recipientList = toEmail.split(";");
		InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];

		MimeMessage msg = new MimeMessage(session);
		try {
			int counter = 0;
			for (String recipient : recipientList) {
			    recipientAddress[counter] = new InternetAddress(recipient.trim());
			    counter++;
			}			
			msg.setSubject(mail.getSubject());
			msg.setFrom(new InternetAddress(FROMEMAIL, FROMEMAILDN));
			msg.setRecipients(Message.RecipientType.TO, recipientAddress);
			if (Objects.nonNull(mail.getSender())) {
				msg.setRecipients(Message.RecipientType.CC, mail.getSender());
			}
			msg.setText(mail.getText());
			msg.setSentDate(new Date());
			Transport transport = session.getTransport("smtps");
			transport.connect(SMTPHOST, Integer.valueOf(SMTPPORT), EMAILUSER, PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			LOG.info("Sent email successfully to recipient(s)={}  cc'ed={}, for league={} - {}", mail.getRecipient(), mail.getSender(), mail.getLeagueId(), mail.getLeagueName());

		} catch (AddressException e) {
			LOG.error("Email error: recipient(s)={}  cc'ed={}, for league={} - {}, {}", mail.getRecipient(), mail.getSender(), mail.getLeagueId(), mail.getLeagueName(), e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (MessagingException e) {
			LOG.error("Email error: recipient(s)={}  cc'ed={}, for league={} - {}, {}", mail.getRecipient(), mail.getSender(), mail.getLeagueId(), mail.getLeagueName(), e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			LOG.error("Email error: recipient(s)={}  cc'ed={}, for league={} - {}, {}", mail.getRecipient(), mail.getSender(), mail.getLeagueId(), mail.getLeagueName(), e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

	}

}
