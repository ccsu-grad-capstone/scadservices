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
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerListDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.exception.MissingParameterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ApplicationScoped
@Path(EndpointConstants.SCAD)
@Api(value = EndpointConstants.SCADLEAGUEPLAYER, tags = "SCAD League Player APIs", description = "Get League Player settings from SCAD system")
public interface SCADLeaguePlayerApi {

	@GET
	@Path(EndpointConstants.SCADLEAGUEPLAYER + "/{id}")
	@ApiOperation(value = "Get specific Player from SCAD by id", notes = "Returns Player from SCAD by id", response = SCADLeaguePlayerDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeaguePlayer(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(EndpointConstants.ID) Long id)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/{scadLeagueId}" + EndpointConstants.SCADLEAGUEPLAYER + "/all")
	@ApiOperation(value = "Get all Players from SCAD by scadLeagueId", notes = "Returns all Players from SCAD by scadLeagueId", response = SCADLeaguePlayerListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeaguePlayersBySCADLeague(
			@ApiParam(value = EndpointConstants.SCADLEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.SCADLEAGUEID) Long scadLeagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/{scadLeagueId}" + EndpointConstants.SCADLEAGUEPLAYER + "/myPlayers")
	@ApiOperation(value = "Gets MyPlayers from SCAD by scadLeagueId", notes = "Returns MyPlayers from SCAD by scadLeagueId", response = SCADLeaguePlayerListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueMyPlayers(
			@ApiParam(value = EndpointConstants.SCADLEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.SCADLEAGUEID) Long scadLeagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/yahoo/{leagueId}" + EndpointConstants.SCADLEAGUEPLAYER + "/myPlayers")
	@ApiOperation(value = "Gets MyPlayers from SCAD by yahoo leagueId", notes = "Returns MyPlayers from SCAD by yahoo leagueId", response = SCADLeaguePlayerListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueMyPlayersByYahooLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/yahoo/{leagueId}" + EndpointConstants.SCADLEAGUEPLAYER + "/all")
	@ApiOperation(value = "Gets all Players from SCAD by yahoo leagueId", notes = "Returns all Players from SCAD by yahoo leagueId", response = SCADLeaguePlayerListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeaguePlayersByYahooLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/{scadLeagueId}" + EndpointConstants.SCADLEAGUEPLAYER + "/{id}")
	@ApiOperation(value = "Get Player from SCAD by scadLeagueId and id", notes = "Returns Player from SCAD by scadLeagueId and id", response = SCADLeaguePlayerDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeaguePlayerBySCADLeagueAndPlayer(
			@ApiParam(value = EndpointConstants.SCADLEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.SCADLEAGUEID) Long scadLeagueId,
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(EndpointConstants.ID) Long id)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/league/yahoo/{leagueId}" + EndpointConstants.SCADLEAGUEPLAYER + "/{playerId}")
	@ApiOperation(value = "Get Player from SCAD by yahoo leagueId and playerId", notes = "Returns Player from SCAD by yahoo leagueId and playerId", response = SCADLeaguePlayerDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeaguePlayerByYahooLeagueAndPlayer(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId,
			@ApiParam(value = EndpointConstants.PLAYER_RESOURCE_ID, required = true) @PathParam(EndpointConstants.PLAYERID) Long playerId)
			throws AuthorizationFailedException, RuntimeException;
	
	@GET
	@Path("/league/{scadLeagueId}" + EndpointConstants.SCADLEAGUETEAM +"/{scadTeamId}" + EndpointConstants.SCADLEAGUEPLAYER + "/players")
	@ApiOperation(value = "Gets Players from SCAD by scadLeagueId and scadTeamId", notes = "Returns Players from SCAD by scadLeagueId and scadTeamId", response = SCADLeaguePlayerListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeaguePlayersBySCADLeagueAndTeam(
			@ApiParam(value = EndpointConstants.SCADLEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.SCADLEAGUEID) Long scadLeagueId,
			@ApiParam(value = EndpointConstants.SCADLEAGUETEAM_RESOURCE_ID, required = true) @PathParam(EndpointConstants.SCADTEAMID) Long scadTeamId)
			throws AuthorizationFailedException, RuntimeException;
	
	@GET
	@Path("/league/yahoo/{leagueId}" + EndpointConstants.SCADLEAGUETEAM +"/{teamId}" + EndpointConstants.SCADLEAGUEPLAYER + "/players")
	@ApiOperation(value = "Gets Players from SCAD by yahoo leagueId and teamId", notes = "Returns Players from SCAD by yahoo leagueId and teamId", response = SCADLeaguePlayerListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeaguePlayersByYahooLeagueAndTeam(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId,
			@ApiParam(value = EndpointConstants.TEAM_RESOURCE_ID, required = true) @PathParam(EndpointConstants.TEAMID) Long teamId)
			throws AuthorizationFailedException, RuntimeException;

	@POST
	@Path(EndpointConstants.SCADLEAGUEPLAYER)
	@ApiOperation(value = "Create a new SCAD League Player resource", notes = "Add League Player additional settings in SCAD system", response = SCADLeaguePlayerDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response createSCADLeaguePlayer(
			@ApiParam(value = EndpointConstants.POSTPROPOSED, required = true) SCADLeaguePlayerDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@PUT
	@Path(EndpointConstants.SCADLEAGUEPLAYER + "/{id}")
	@ApiOperation(value = "Update a SCAD League Player resource", notes = "Update League Player additional settings in SCAD system", response = SCADLeaguePlayerDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateSCADLeaguePlayer(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(value = EndpointConstants.ID) Long id,
			@ApiParam(value = EndpointConstants.PUTPROPOSED, required = true) SCADLeaguePlayerDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@DELETE
	@Path(EndpointConstants.SCADLEAGUEPLAYER + "/{id}")
	@ApiOperation(value = "Delete a SCAD League Player resource", notes = "Delete League Player additional settings from SCAD system", response = Boolean.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteSCADLeaguePlayer(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(value = EndpointConstants.ID) Long id)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;
	
}
