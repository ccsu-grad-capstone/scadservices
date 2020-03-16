package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.SCADLeagueApi;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.exception.MissingParameterException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.SCADLeaguePlayerService;
import edu.ccsu.cs595.capstone.scadservices.service.SCADLeagueService;
import edu.ccsu.cs595.capstone.scadservices.service.SCADLeagueTeamService;
import edu.ccsu.cs595.capstone.scadservices.util.HeaderHelper;

public class SCADLeagueApiImpl implements SCADLeagueApi {

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	HeaderHelper hHlpr;
	
	@Inject
	SCADLeagueService slSvc;
	
	@Inject
	SCADLeagueTeamService sltSvc;
	
	@Inject
	SCADLeaguePlayerService slpSvc;
	
	@Override
	public Response getDefaultSCADLeague() throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueDto result = slSvc.getDefaultSCADLeagueByYahooGame();

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}
	
	@Override
	public Response getAllSCADLeagues() throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getSCADLeague(Long id) throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createSCADLeague(SCADLeagueDto proposed) throws MissingParameterException, AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateSCADLeague(Long id, SCADLeagueDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteSCADLeague(Long id) throws MissingParameterException, AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getSCADLeagueByYahooLeagueId(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getSCADLeagueTeam(Long id) throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAllSCADLeagueTeamsBySCADLeagueId(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAllSCADLeagueTeamsByYahooLeagueId(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAllSCADLeagueTeamsBySCADLeagueIdAndId(Long leagueId, Long id)
			throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAllSCADLeagueTeamsByYahooLeagueIdAndTeamId(Long leagueId, Long teamId)
			throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createSCADLeagueTeam(SCADLeagueTeamDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateSCADLeagueTeam(Long id, SCADLeagueTeamDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteSCADLeagueTeam(Long id)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getSCADLeaguePlayer(Long id) throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAllSCADLeaguePlayersBySCADLeagueId(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAllSCADLeaguePlayersByYahooLeagueId(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAllSCADLeaguePlayersBySCADLeagueIdAndId(Long leagueId, Long id)
			throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAllSCADLeaguePlayersByYahooLeagueIdAndPlayerId(Long leagueId, Long playerId)
			throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createSCADLeaguePlayer(SCADLeaguePlayerDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateSCADLeaguePlayer(Long id, SCADLeaguePlayerDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteSCADLeaguePlayer(Long id)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

}
