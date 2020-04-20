package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.DashboardApi;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.DashboardService;
import edu.ccsu.cs595.capstone.scadservices.util.HeaderHelper;

public class DashboardApiImpl implements DashboardApi {

	@Inject
	DashboardService dbSvc;

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	HeaderHelper hHlpr;
	
	@Override
	public Response getDashboardDetails() throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		String result = dbSvc.getDashboardDetails();

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

}
