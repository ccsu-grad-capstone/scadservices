package edu.ccsu.cs595.capstone.scadservices.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ccsu.cs595.capstone.scadservices.api.LeagueApi;
import edu.ccsu.cs595.capstone.scadservices.api.SCADLeagueApi;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
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
		
		return "{Email sent successfully}";
	}
	
	public String sendRequestEmail(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		return "{Email sent successfully}";
	}
	
}
