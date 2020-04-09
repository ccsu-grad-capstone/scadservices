package edu.ccsu.cs595.capstone.scadservices.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ccsu.cs595.capstone.scadservices.api.LeagueApi;
import edu.ccsu.cs595.capstone.scadservices.api.SCADLeagueApi;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.util.SCADMail;
import edu.ccsu.cs595.capstone.scadservices.util.SendMail;
import edu.ccsu.cs595.capstone.scadservices.util.YahooClientBuilder;

@Stateless
public class EmailService {

	private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	LeagueApi lApi;
	
	@Inject
	SCADLeagueApi slApi;
	
	@Inject
	YahooClientBuilder yahoo;
	
	public String sendEmailToRegisteredUsers(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		String result = "{Email sent successfully}";
		
		SCADMail mail = new SCADMail();
		mail.setSender("krameshreddy@yahoo.com");
		mail.setRecipient("rameshkappera@gmail.com");
		mail.setSubject("Test SCAD mail");
		mail.setText("Test SCAD mail body");
		
		SendMail sendMail = new SendMail();

			sendMail.sendMail(mail);
		
		return result;
		
	}
	
	public String sendRequestEmail(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		return "{Email sent successfully}";
	}
	
}
