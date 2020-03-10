package edu.ccsu.cs595.capstone.scadservices.config;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;

@Provider
public class RequestCORSFilter implements ContainerRequestFilter{
	
	private static final Logger LOG = LoggerFactory.getLogger(RequestCORSFilter.class);

	@Inject
	SCADSecurityManager	sm;
	
	@SuppressWarnings("static-access")
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		sm.setIDTOKEN(requestContext.getHeaderString(sm.ID_TOKEN_HEADER));
		sm.setACCESSTOKEN(requestContext.getHeaderString(sm.ACCESS_TOKEN_HEADER));
		sm.setAUTHORIZATION(requestContext.getHeaderString(sm.AUTHORIZATION_HEADER));
		LOG.info("ID_TOKEN={}",sm.getIDTOKEN());
		LOG.info("ACCESS_TOKEN={}",sm.getACCESSTOKEN());
		LOG.info("AUTHORIZATION={}",sm.getAUTHORIZATION());
//		if ((Objects.isNull(sm.getIDTOKEN())) || (Objects.isNull(sm.getACCESSTOKEN()))) {
//			throw new RuntimeException("id_token and access_token are required for every SCAD request");
//		}
	}
	
	

}
