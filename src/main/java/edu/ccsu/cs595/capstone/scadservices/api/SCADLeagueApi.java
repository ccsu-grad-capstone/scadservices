package edu.ccsu.cs595.capstone.scadservices.api;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.EndpointConstants;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueListDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.exception.MissingParameterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ApplicationScoped
@Path(EndpointConstants.SCAD)
@Api(value = EndpointConstants.LEAGUE, tags = "SCAD League APIs", description = "Get League settings from SCAD system")
public interface SCADLeagueApi {

	@GET
	@Path(EndpointConstants.LEAGUE + "/default")
	@ApiOperation(value = "Get default user League from SCAD by season", notes = "Returns default user League from SCAD by season", response = SCADLeagueDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDefaultUserSCADLeagueBySeason() throws AuthorizationFailedException, RuntimeException;

	@PUT
	@Path(EndpointConstants.LEAGUE + "/default/update/{id}")
	@ApiOperation(value = "Update a SCAD League default indicator for specified Id", notes = "Update League default indicator in SCAD system", response = SCADLeagueDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateSCADLeagueDefaultIndicator(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(value = EndpointConstants.ID) Long id)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path(EndpointConstants.LEAGUE + "/all")
	@ApiOperation(value = "Get user Leagues from SCAD by season", notes = "Returns user Leagues from SCAD by season", response = SCADLeagueListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserSCADLeaguesBySeason() throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path(EndpointConstants.LEAGUE+ "/{id}")
	@ApiOperation(value = "Get League from SCAD by id", notes = "Returns League from SCAD by id", response = SCADLeagueDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeague(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(EndpointConstants.ID) Long id)
			throws AuthorizationFailedException, RuntimeException;

	@POST
	@ApiOperation(value = "Create a new SCAD League resource", notes = "Add League additional settings in SCAD system", response = SCADLeagueDto.class)
	@Path(EndpointConstants.LEAGUE)
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response createSCADLeague(
			@ApiParam(value = EndpointConstants.POSTPROPOSED, required = true) SCADLeagueDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@PUT
	@Path(EndpointConstants.LEAGUE + "/{id}")
	@ApiOperation(value = "Update a SCAD League resource", notes = "Update League additional settings in SCAD system", response = SCADLeagueDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateSCADLeague(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(value = EndpointConstants.ID) Long id,
			@ApiParam(value = EndpointConstants.PUTPROPOSED, required = true) SCADLeagueDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@DELETE
	@Path(EndpointConstants.LEAGUE + "/{id}")
	@ApiOperation(value = "Delete a SCAD League resource", notes = "Delete League additional settings from SCAD system", response = Boolean.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteSCADLeague(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(value = EndpointConstants.ID) Long id)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@GET
	@Path(EndpointConstants.LEAGUE + "/yahoo/{leagueId}")
	@ApiOperation(value = "Get League from SCAD by yahoo leagueId", notes = "Returns League from SCAD by yahoo leagueId", response = SCADLeagueDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueByYahooLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

}
