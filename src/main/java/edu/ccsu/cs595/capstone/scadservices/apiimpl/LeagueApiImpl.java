package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.LeagueApi;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.LeagueService;

@SuppressWarnings("static-access")
public class LeagueApiImpl implements LeagueApi {

	@Inject
	LeagueService lSvc;

	@Inject
	SCADSecurityManager sm;

	@Override
	public Response getLeague( Long id) throws RuntimeException {


		if ((Objects.isNull(sm.getIDTOKEN())) || (Objects.isNull(sm.getACCESSTOKEN()))) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Missing SCAD idToken or accessToken").build();
		}

		String result = lSvc.getLeague(id);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getAllLeagues() throws RuntimeException {

		if ((Objects.isNull(sm.getIDTOKEN())) || (Objects.isNull(sm.getACCESSTOKEN()))) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Missing SCAD idToken or accessToken").build();
		}
		
		String result = lSvc.getAllLeagues();

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

}
