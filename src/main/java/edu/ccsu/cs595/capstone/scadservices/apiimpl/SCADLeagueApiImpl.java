package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.SCADLeagueApi;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueListDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerListDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamListDto;
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

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueListDto result = slSvc.getAllSCADLeaguesByYahooGame();

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
	
	}

	@Override
	public Response getSCADLeague(Long id) throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueDto result = slSvc.getSCADLeague(id);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response createSCADLeague(SCADLeagueDto proposed) throws MissingParameterException, AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		String missingParam = getMissingRequiredParamsForCreate(proposed);
		
		if (Objects.nonNull(missingParam)) {
			
			throw new MissingParameterException(missingParam);
			
		} else {
			
			SCADLeagueDto result = slSvc.createSCADLeague(proposed);

			if (Objects.isNull(result)) {

				return Response.status(Response.Status.NOT_FOUND).build();
			}

			return Response.status(Response.Status.OK).entity(result).build();
			
		}
		
	}

	@Override
	public Response updateSCADLeague(Long id, SCADLeagueDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {
	
		hHlpr.isHeaderAccessValid(sm);
		
		String missingParam = getMissingRequiredParamsForUpdate(proposed);
		
		if (Objects.nonNull(missingParam)) {
			
			throw new MissingParameterException(missingParam);
			
		} else {
			
			SCADLeagueDto result = slSvc.updateSCADLeague(id, proposed);

			if (Objects.isNull(result)) {

				return Response.status(Response.Status.NOT_FOUND).build();
			}

			return Response.status(Response.Status.OK).entity(result).build();
			
		}
		
	}

	@Override
	public Response deleteSCADLeague(Long id) throws MissingParameterException, AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		slSvc.deleteSCADLeague(id);

		return Response.status(Response.Status.OK).entity(Boolean.class).build();
		
	}

	@Override
	public Response getSCADLeagueByYahooLeague(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueDto result = slSvc.getSCADLeagueByYahooGameAndLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getSCADLeagueTeam(Long id) throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueTeamDto result = sltSvc.getSCADLeagueTeam(id);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getAllSCADLeagueTeamsBySCADLeague(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueTeamListDto result = sltSvc.getAllSCADLeagueTeamsBySCADLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getAllSCADLeagueTeamsByYahooLeague(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueTeamListDto result = sltSvc.getAllSCADLeagueTeamsByYahooLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getSCADLeagueTeamBySCADLeagueAndTeam(Long leagueId, Long id)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueTeamDto result = sltSvc.getSCADLeagueTeamBySCADLeagueAndTeam(leagueId, id);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getSCADLeagueTeamByYahooLeagueAndTeam(Long leagueId, Long teamId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueTeamDto result = sltSvc.getSCADLeagueTeamByYahooLeagueAndTeam(leagueId, teamId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response createSCADLeagueTeam(SCADLeagueTeamDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		String missingParam = getMissingRequiredParamsForCreateTeam(proposed);
		
		if (Objects.nonNull(missingParam)) {
			
			throw new MissingParameterException(missingParam);
			
		} else {
			
			SCADLeagueTeamDto result = sltSvc.createSCADLeagueTeam(proposed);

			if (Objects.isNull(result)) {

				return Response.status(Response.Status.NOT_FOUND).build();
			}

			return Response.status(Response.Status.OK).entity(result).build();
			
		}

	}

	@Override
	public Response updateSCADLeagueTeam(Long id, SCADLeagueTeamDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		String missingParam = getMissingRequiredParamsForUpdateTeam(proposed);
		
		if (Objects.nonNull(missingParam)) {
			
			throw new MissingParameterException(missingParam);
			
		} else {
			
			SCADLeagueTeamDto result = sltSvc.updateSCADLeagueTeam(id, proposed);

			if (Objects.isNull(result)) {

				return Response.status(Response.Status.NOT_FOUND).build();
			}

			return Response.status(Response.Status.OK).entity(result).build();
			
		}

	}

	@Override
	public Response deleteSCADLeagueTeam(Long id)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		sltSvc.deleteSCADLeagueTeam(id);

		return Response.status(Response.Status.OK).entity(Boolean.class).build();

	}

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
	public Response getAllSCADLeaguePlayersBySCADLeague(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerListDto result = slpSvc.getAllSCADLeaguePlayersBySCADLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getAllSCADLeaguePlayersByYahooLeague(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerListDto result = slpSvc.getAllSCADLeaguePlayersByYahooLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getSCADLeaguePlayerBySCADLeagueAndPlayer(Long leagueId, Long id)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeaguePlayerDto result = slpSvc.getSCADLeaguePlayerBySCADLeagueAndPlayer(leagueId, id);

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
	
	private String getMissingRequiredParamsForCreate(SCADLeagueDto proposed) {

		String missingParam = null;
		if (Objects.isNull(proposed)) {
			missingParam = "proposed object";
		} else {
			if (Objects.isNull(proposed.getYahooLeagueId())) {
				missingParam = "Yahoo League Id";
			}
		}

		return missingParam;

	}

	private String getMissingRequiredParamsForUpdate(SCADLeagueDto proposed) {

		String missingParam = null;
		if (Objects.isNull(proposed)) {
			missingParam = "proposed object";
		}

		return missingParam;

	}
	
	private String getMissingRequiredParamsForCreateTeam(SCADLeagueTeamDto proposed) {

		String missingParam = null;
		if (Objects.isNull(proposed)) {
			missingParam = "proposed object";
		} else {
			if (Objects.isNull(proposed.getYahooLeagueTeamId())) {
				missingParam = "Yahoo League Team Id";
			} else if (Objects.isNull(proposed.getYahooLeagueId()))  {
				missingParam = "Yahoo League Id";
			} else if (Objects.isNull(proposed.getScadLeagueId())) {
				missingParam = "SCAD League Id";
			}
		}

		return missingParam;

	}

	private String getMissingRequiredParamsForUpdateTeam(SCADLeagueTeamDto proposed) {

		String missingParam = null;
		if (Objects.isNull(proposed)) {
			missingParam = "proposed object";
		}

		return missingParam;

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

	@Override
	public Response getSCADLeagueMyTeamBySCADLeague(Long leagueId, Long yahooTeamId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueTeamDto result = sltSvc.getSCADLeagueMyTeamBySCADLeague(leagueId, yahooTeamId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

}
