package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.SCADLeagueApi;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueListDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.exception.MissingParameterException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.SCADLeagueService;
import edu.ccsu.cs595.capstone.scadservices.util.HeaderHelper;

public class SCADLeagueApiImpl implements SCADLeagueApi {

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	HeaderHelper hHlpr;
	
	@Inject
	SCADLeagueService slSvc;
	
	@Override
	public Response getDefaultUserSCADLeagueBySeason() throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueDto result = slSvc.getDefaultUserSCADLeagueBySeason();

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();
		
	}
	
	@Override
	public Response getUserSCADLeaguesBySeason() throws AuthorizationFailedException, RuntimeException {

		hHlpr.isHeaderAccessValid(sm);
		
		SCADLeagueListDto result = slSvc.getUserSCADLeaguesBySeason();

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
		
		SCADLeagueDto result = slSvc.getSCADLeagueByYahooLeague(leagueId);

		if (Objects.isNull(result)) {

			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(result).build();

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

}
