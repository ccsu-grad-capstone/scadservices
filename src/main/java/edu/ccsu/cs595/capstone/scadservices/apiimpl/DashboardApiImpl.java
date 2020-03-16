package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.DashboardApi;
import edu.ccsu.cs595.capstone.scadservices.api.LeagueApi;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.DashboardService;
import edu.ccsu.cs595.capstone.scadservices.service.LeagueService;

@SuppressWarnings("static-access")
public class DashboardApiImpl implements DashboardApi {

	@Inject
	DashboardService dbSvc;

	@Inject
	SCADSecurityManager sm;
	
	@Override
	public Response getDashboardDetails() throws AuthorizationFailedException, RuntimeException {

		if ((Objects.isNull(sm.getIDTOKEN())) || (Objects.isNull(sm.getACCESSTOKEN()))) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Missing SCAD idToken or accessToken").build();
		}
		
		String result = dbSvc.getDashboardDetails();

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

}
