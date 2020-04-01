package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.LeagueApi;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.LeagueService;
import edu.ccsu.cs595.capstone.scadservices.util.HeaderHelper;

@SuppressWarnings("static-access")
public class LeagueApiImpl implements LeagueApi {

	@Inject
	LeagueService lSvc;

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	HeaderHelper hHlpr;

	@Override
	public Response getUserAllLeagues() throws AuthorizationFailedException, RuntimeException {
		hHlpr.isHeaderAccessValid(sm);
		String result = null;
		try {
			result = lSvc.getUserAllLeagues();
		} catch (IOException ex) {
			//This will hit if something is wrong with getting the dummy data from the file system.
		}

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getUserLeague(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		hHlpr.isHeaderAccessValid(sm);
		String result = null;
		try {
			result = lSvc.getUserLeague(leagueId);
		} catch (IOException ex) {
			//This will hit if something is wrong with getting the dummy data from the file system.
		}

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getUserLeagueTeams(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		hHlpr.isHeaderAccessValid(sm);
        String result = null;
        try {
            result = lSvc.getUserLeagueTeams(leagueId);
        } catch (IOException ex) {
            // Foo
        }

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
	}

	@Override
	public Response getUserLeagueSettings(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		hHlpr.isHeaderAccessValid(sm);
		String result = null;

		try {
			result = lSvc.getUserLeagueSettings(leagueId);
		} catch (IOException ex) {
			// Could not get the dummy data.
		}

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
	}

	@Override
	public Response getUserLeagueStandings(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		hHlpr.isHeaderAccessValid(sm);
		String result = null;

		try {
			result = lSvc.getUserLeagueStandings(leagueId);
		} catch (IOException ex) {
			// Could not get the dummy data.
		}

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.status(Response.Status.OK).entity(result).build();
	}

	@Override
	public Response getUserLeagueTeamAndRoster(Long leagueId, Long teamId, @QueryParam("week") Long week) throws AuthorizationFailedException, RuntimeException {
		hHlpr.isHeaderAccessValid(sm);


		String result = lSvc.getUserLeagueTeamAndRoster(leagueId, teamId, week);

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
	}

	@Override
	public Response getUserLeaguePlayers(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		hHlpr.isHeaderAccessValid(sm);

		String result = lSvc.getUserLeaguePlayers(leagueId);

		if (Objects.isNull(result)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
	}
}
