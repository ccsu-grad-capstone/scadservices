package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
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
	public Response getUserLeaguesBySeason() throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);
		
		String result = lSvc.getUserLeaguesBySeason();

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getUserLeaguesAsCommissionerBySeason() throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);

		String result = lSvc.getUserLeaguesAsCommissionerBySeason();

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getYahooLeague(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);

		String result = lSvc.getYahooLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getYahooLeagueTeams(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);

		String result = lSvc.getYahooLeagueTeams(leagueId);

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
	
	}

	@Override
	public Response getYahooLeagueSettings(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);

		String result = lSvc.getYahooLeagueSettings(leagueId);

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
	
	}

	@Override
	public Response getYahooLeagueStandings(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);

		String result = lSvc.getYahooLeagueStandings(leagueId);

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.status(Response.Status.OK).entity(result).build();
	
	}

	@Override
	public Response getYahooLeagueTeamAndRoster(Long leagueId, Long teamId, @QueryParam("week") Long week) throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);


		String result = lSvc.getYahooLeagueTeamAndRoster(leagueId, teamId, week);

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
	
	}

	@Override
	public Response getYahooLeaguePlayers(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);

		String result = lSvc.getYahooLeaguePlayers(leagueId);

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
	
	}

	@Override
	public Response getYahooLeagueMyTeam(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);

		String result = lSvc.getYahooLeagueMyTeam(leagueId);

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getYahooLeagueMyPlayers(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);

		String result = lSvc.getYahooLeagueMyPlayers(leagueId);

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getYahooLeagueTeamPlayers(Long leagueId, Long teamId)
			throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);

		String result = lSvc.getYahooLeagueTeamPlayers(leagueId, teamId);

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}
	
	


}
