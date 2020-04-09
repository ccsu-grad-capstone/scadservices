package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.SCADLeagueTeamApi;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamListDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.exception.MissingParameterException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.SCADLeagueTeamService;
import edu.ccsu.cs595.capstone.scadservices.util.HeaderHelper;

public class SCADLeagueTeamApiImpl implements SCADLeagueTeamApi {

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	HeaderHelper hHlpr;
	
	@Inject
	SCADLeagueTeamService sltSvc;
	
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
	public Response getSCADLeagueTeamsBySCADLeague(Long scadLeagueId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueTeamListDto result = sltSvc.getSCADLeagueTeamsBySCADLeague(scadLeagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}
	
	@Override
	public Response getSCADLeagueMyTeam(Long scadLeagueId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueTeamDto result = sltSvc.getSCADLeagueMyTeam(scadLeagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}

	@Override
	public Response getSCADLeagueMyTeamByYahooLeague(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueTeamDto result = sltSvc.getSCADLeagueMyTeamByYahooLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}
	
	@Override
	public Response getSCADLeagueTeamsByYahooLeague(Long leagueId)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueTeamListDto result = sltSvc.getSCADLeagueTeamsByYahooLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

	}

	@Override
	public Response getSCADLeagueTeamBySCADLeagueAndTeam(Long scadLeagueId, Long id)
			throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueTeamDto result = sltSvc.getSCADLeagueTeamBySCADLeagueAndTeam(scadLeagueId, id);

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

}
