package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.EmailApi;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.EmailService;
import edu.ccsu.cs595.capstone.scadservices.util.HeaderHelper;

public class EmailApiImpl implements EmailApi {

	@Inject
	EmailService eSvc;

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	HeaderHelper hHlpr;
	
	@Override
	public Response sendEmailToRegisteredUsers(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		String result = eSvc.sendEmailToRegisteredUsers(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response sendRequestEmail(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		String result = eSvc.sendRequestEmail(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

}
