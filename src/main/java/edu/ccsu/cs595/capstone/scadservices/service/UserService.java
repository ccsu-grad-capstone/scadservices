package edu.ccsu.cs595.capstone.scadservices.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.util.GUIDGenerator;


@Stateless
public class UserService {
	
	
	@Inject
	SCADSecurityManager	sm;
	
	@Inject
	GUIDGenerator gen;

	private Client restClient = ClientBuilder.newClient();

	private static final String REST_URI = "https://api.login.yahoo.com";

}
