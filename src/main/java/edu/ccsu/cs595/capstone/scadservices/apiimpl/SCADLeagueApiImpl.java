package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.SCADLeagueApi;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.exception.MissingParameterException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.SCADLeagueService;

public class SCADLeagueApiImpl implements SCADLeagueApi {

	@Inject
	SCADLeagueService slSvc;

	@Inject
	SCADSecurityManager sm;
	
	@Override
	public Response getUserDefaultSCADLeague() throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Response getUserAllSCADLeagues() throws AuthorizationFailedException, RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getUserSCADLeague(Long id) throws AuthorizationFailedException, RuntimeException {
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

}
