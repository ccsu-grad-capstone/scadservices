package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.SCADLeaguePlayerApi;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerListDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.exception.MissingParameterException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.SCADLeaguePlayerService;
import edu.ccsu.cs595.capstone.scadservices.util.HeaderHelper;

public class SCADLeaguePlayerApiImpl implements SCADLeaguePlayerApi {

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	HeaderHelper hHlpr;
	
	@Inject
	SCADLeaguePlayerService slpSvc;
	
	@Override
	public Response getSCADLeaguePlayer(Long id) throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerDto result = slpSvc.getSCADLeaguePlayer(id);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getSCADLeaguePlayersBySCADLeague(Long scadLeagueId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerListDto result = slpSvc.getSCADLeaguePlayersBySCADLeague(scadLeagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getSCADLeagueMyPlayers(Long scadLeagueId)
			throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerListDto result = slpSvc.getSCADLeagueMyPlayers(scadLeagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}
	
	@Override
	public Response getSCADLeagueMyPlayersByYahooLeague(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {
		
		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerListDto result = slpSvc.getSCADLeagueMyPlayersByYahooLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getSCADLeaguePlayersByYahooLeague(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerListDto result = slpSvc.getSCADLeaguePlayersByYahooLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getSCADLeaguePlayerBySCADLeagueAndPlayer(Long scadLeagueId, Long id)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerDto result = slpSvc.getSCADLeaguePlayerBySCADLeagueAndPlayer(scadLeagueId, id);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getSCADLeaguePlayerByYahooLeagueAndPlayer(Long leagueId, Long playerId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerDto result = slpSvc.getSCADLeaguePlayerByYahooLeagueAndPlayer(leagueId, playerId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}
	
	@Override
	public Response getSCADLeaguePlayersBySCADLeagueAndTeam(Long scadLeagueId, Long scadTeamId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerListDto result = slpSvc.getSCADLeaguePlayersBySCADLeagueAndTeam(scadLeagueId, scadTeamId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getSCADLeaguePlayersByYahooLeagueAndTeam(Long leagueId, Long teamId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerListDto result = slpSvc.getSCADLeaguePlayersByYahooLeagueAndTeam(leagueId, teamId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response createSCADLeaguePlayer(SCADLeaguePlayerDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		String missingParam = getMissingRequiredParamsForCreatePlayer(proposed);
		
		if (Objects.nonNull(missingParam)) {
			
			throw new MissingParameterException(missingParam);
			
		} else {
			
			SCADLeaguePlayerDto result = slpSvc.createSCADLeaguePlayer(proposed);

			if (Objects.isNull(result)) {

				return Response.status(Response.Status.NOT_FOUND).build();
			}

			return Response.status(Response.Status.OK).entity(result).build();
			
		}

	}

	@Override
	public Response updateSCADLeaguePlayer(Long id, SCADLeaguePlayerDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		String missingParam = getMissingRequiredParamsForUpdatePlayer(proposed);
		
		if (Objects.nonNull(missingParam)) {
			
			throw new MissingParameterException(missingParam);
			
		} else {
			
			SCADLeaguePlayerDto result = slpSvc.updateSCADLeaguePlayer(id, proposed);

			if (Objects.isNull(result)) {

				return Response.status(Response.Status.NOT_FOUND).build();
			}

			return Response.status(Response.Status.OK).entity(result).build();
			
		}
		
	}

	@Override
	public Response deleteSCADLeaguePlayer(Long id)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		slpSvc.deleteSCADLeaguePlayer(id);

		return Response.status(Response.Status.OK).entity(Boolean.class).build();

		
	}
	
	private String getMissingRequiredParamsForCreatePlayer(SCADLeaguePlayerDto proposed) {

		String missingParam = null;
		if (Objects.isNull(proposed)) {
			missingParam = "proposed object";
		} else {
			if (Objects.isNull(proposed.getYahooLeaguePlayerId())) {
				missingParam = "Yahoo League Player Id";
			} else if (Objects.isNull(proposed.getYahooLeagueId()))  {
				missingParam = "Yahoo League Id";
			} else if (Objects.isNull(proposed.getScadLeagueId())) {
				missingParam = "SCAD League Id";
			}
		}

		return missingParam;

	}

	private String getMissingRequiredParamsForUpdatePlayer(SCADLeaguePlayerDto proposed) {

		String missingParam = null;
		if (Objects.isNull(proposed)) {
			missingParam = "proposed object";
		}

		return missingParam;

	}

}
