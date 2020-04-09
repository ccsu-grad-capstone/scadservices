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
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamListDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.exception.MissingParameterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ApplicationScoped
@Path(EndpointConstants.SCAD)
@Api(value = EndpointConstants.SCADLEAGUETEAM, tags = "SCAD League Team APIs", description = "Get League Team settings from SCAD system")
public interface SCADLeagueTeamApi {

	@GET
	@Path(EndpointConstants.SCADLEAGUETEAM + "/{id}")
	@ApiOperation(value = "Get specific Team from SCAD by id", notes = "Returns Team from SCAD by id", response = SCADLeagueTeamDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueTeam(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(EndpointConstants.ID) Long id)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/{scadLeagueId}" + EndpointConstants.SCADLEAGUETEAM + "/all")
	@ApiOperation(value = "Gets all Teams from SCAD by scadLeagueId", notes = "Returns all Teams from SCAD by scadLeagueId", response = SCADLeagueTeamListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueTeamsBySCADLeague(
			@ApiParam(value = EndpointConstants.SCADLEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.SCADLEAGUEID) Long scadLeagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/{scadLeagueId}" + EndpointConstants.SCADLEAGUETEAM + "/myTeam")
	@ApiOperation(value = "Get MyTeam from SCAD by scadLeagueId", notes = "Returns MyTeam from SCAD by scadLeagueId", response = SCADLeagueTeamDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueMyTeam(
			@ApiParam(value = EndpointConstants.SCADLEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.SCADLEAGUEID) Long scadLeagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/yahoo/{leagueId}" + EndpointConstants.SCADLEAGUETEAM + "/myTeam")
	@ApiOperation(value = "Get MyTeam from SCAD by yahoo leagueId", notes = "Returns MyTeam from SCAD by yahoo leagueId", response = SCADLeagueTeamDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueMyTeamByYahooLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/yahoo/{leagueId}" + EndpointConstants.SCADLEAGUETEAM + "/all")
	@ApiOperation(value = "Gets all Teams from SCAD by yahoo leagueId", notes = "Returns all Teams from SCAD by yahoo leagueId", response = SCADLeagueTeamListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueTeamsByYahooLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/{scadLeagueId}" + EndpointConstants.SCADLEAGUETEAM + "/{id}")
	@ApiOperation(value = "Get Team from SCAD by scadLeagueId and id", notes = "Returns Team from SCAD by scadLeagueId and id", response = SCADLeagueTeamDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueTeamBySCADLeagueAndTeam(
			@ApiParam(value = EndpointConstants.SCADLEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.SCADLEAGUEID) Long scadLeagueId,
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(EndpointConstants.ID) Long id)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/yahoo/{leagueId}" + EndpointConstants.SCADLEAGUETEAM + "/{teamId}")
	@ApiOperation(value = "Get Team from SCAD by yahoo leagueId and teamId", notes = "Returns Team from SCAD by yahoo leagueId and teamId", response = SCADLeagueTeamDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueTeamByYahooLeagueAndTeam(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId,
			@ApiParam(value = EndpointConstants.TEAM_RESOURCE_ID, required = true) @PathParam(EndpointConstants.TEAMID) Long teamId)
			throws AuthorizationFailedException, RuntimeException;

	@POST
	@Path(EndpointConstants.SCADLEAGUETEAM)
	@ApiOperation(value = "Create a new Team resource", notes = "Add Team additional settings in SCAD system", response = SCADLeagueTeamDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response createSCADLeagueTeam(
			@ApiParam(value = EndpointConstants.POSTPROPOSED, required = true) SCADLeagueTeamDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@PUT
	@Path(EndpointConstants.SCADLEAGUETEAM + "/{id}")
	@ApiOperation(value = "Update a Team resource", notes = "Update Team additional settings in SCAD system", response = SCADLeagueTeamDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateSCADLeagueTeam(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(value = EndpointConstants.ID) Long id,
			@ApiParam(value = EndpointConstants.PUTPROPOSED, required = true) SCADLeagueTeamDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@DELETE
	@Path(EndpointConstants.SCADLEAGUETEAM + "/{id}")
	@ApiOperation(value = "Delete a Team resource", notes = "Delete Team additional settings from SCAD system", response = Boolean.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteSCADLeagueTeam(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(value = EndpointConstants.ID) Long id)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

}
