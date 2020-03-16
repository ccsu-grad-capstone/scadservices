package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.LeagueApi;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.LeagueService;
import edu.ccsu.cs595.capstone.scadservices.util.HeaderHelper;

public class LeagueApiImpl implements LeagueApi {

	@Inject
	LeagueService lSvc;

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	HeaderHelper hHlpr;
	
	@Override
	public Response getUserAllLeagues() throws AuthorizationFailedException, RuntimeException {

//		if ((Objects.isNull(sm.getIDTOKEN())) || (Objects.isNull(sm.getACCESSTOKEN()))) {
//			return Response.status(Response.Status.UNAUTHORIZED).entity("Missing SCAD idToken or accessToken").build();
//		}
//		
		hHlpr.isHeaderAccessValid(sm);
		
		String result = lSvc.getUserAllLeagues();

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getUserLeague( Long leagueId) throws AuthorizationFailedException, RuntimeException {


//		if ((Objects.isNull(sm.getIDTOKEN())) || (Objects.isNull(sm.getACCESSTOKEN()))) {
//			return Response.status(Response.Status.UNAUTHORIZED).entity("Missing SCAD idToken or accessToken").build();
//		}
		
		hHlpr.isHeaderAccessValid(sm);

		String result = lSvc.getUserLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getUserLeagueTeams(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getUserLeagueSettings(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getUserLeagueStandings(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getUserLeagueTeamAndRoaster(Long leagueId, Long teamId) throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getUserLeaguePlayers(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}


}
