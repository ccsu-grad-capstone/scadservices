package edu.ccsu.cs595.capstone.scadservices.util;

import java.util.Objects;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.DashboardService;

public class HeaderHelper {
	
	private static final Logger LOG = LoggerFactory.getLogger(DashboardService.class);

	@SuppressWarnings("static-access")
	public Response isHeaderAccessValid(SCADSecurityManager sm) throws AuthorizationFailedException, RuntimeException {
		
		if ((Objects.isNull(sm.getIDTOKEN())) || (Objects.isNull(sm.getACCESSTOKEN()))) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Missing SCAD idToken or accessToken").build();
		}
		
		LOG.info("Passed valid ID and Access Tokens in URL header");
		return null;
		
	}

}
