package edu.ccsu.cs595.capstone.scadservices.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ccsu.cs595.capstone.scadservices.api.LeagueApi;
import edu.ccsu.cs595.capstone.scadservices.api.SCADLeagueApi;
import edu.ccsu.cs595.capstone.scadservices.api.UserApi;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;

@Stateless
public class DashboardService {

	private static final Logger LOG = LoggerFactory.getLogger(DashboardService.class);

	@Inject
	UserApi userApi;

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	LeagueApi lApi;
	
	@Inject
	SCADLeagueApi slApi;
	
	public String getDashboardDetails() {
		
		String dbDetails = null;
		//Needs implementation
		return dbDetails;
		
	}
	
}
