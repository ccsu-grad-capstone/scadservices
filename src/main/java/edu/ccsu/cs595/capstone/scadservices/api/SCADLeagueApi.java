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
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerListDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamListDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.exception.MissingParameterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ApplicationScoped
@Path(EndpointConstants.SCADLEAGUE)
@Api(value = EndpointConstants.SCADLEAGUE, tags = "SCAD League", description = "Get SCAD League details")
public interface SCADLeagueApi {

	// Leagues

	@GET
	@Path("/default")
	@ApiOperation(value = "Get default SCAD League", notes = "Returns default SCAD League by season", response = SCADLeagueDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDefaultSCADLeague() throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/all")
	@ApiOperation(value = "Get SCAD Leagues", notes = "Returns SCAD Leagues by season", response = SCADLeagueListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllSCADLeagues() throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/{id}")
	@ApiOperation(value = "Get SCAD League by id", notes = "Returns SCAD League by id", response = SCADLeagueDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeague(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(EndpointConstants.ID) Long id)
			throws AuthorizationFailedException, RuntimeException;

	@POST
	@ApiOperation(value = "Create a new SCAD League resource", notes = "Add League additional settings in SCAD system", response = SCADLeagueDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response createSCADLeague(
			@ApiParam(value = EndpointConstants.POSTPROPOSED, required = true) SCADLeagueDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@PUT
	@Path("/{id}")
	@ApiOperation(value = "Update a SCAD League resource", notes = "Update League additional settings in SCAD system", response = SCADLeagueDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateSCADLeague(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(value = EndpointConstants.ID) Long id,
			@ApiParam(value = EndpointConstants.PUTPROPOSED, required = true) SCADLeagueDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Delete a SCAD League resource", notes = "Delete League additional settings from SCAD system", response = Boolean.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteSCADLeague(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(value = EndpointConstants.ID) Long id)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/yahoo/{leagueId}")
	@ApiOperation(value = "Get SCAD League by yahoo league id", notes = "Returns SCAD League by yahoo league id", response = SCADLeagueDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueByYahooLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	// League Teams

	@GET
	@Path(EndpointConstants.SCADLEAGUETEAM + "/{id}")
	@ApiOperation(value = "Get SCAD League Team by id", notes = "Returns SCAD League Team by id", response = SCADLeagueTeamDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueTeam(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(EndpointConstants.ID) Long id)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/{leagueId}" + EndpointConstants.SCADLEAGUETEAM + "/all")
	@ApiOperation(value = "Get SCAD League Teams by SCAD league id", notes = "Returns SCAD League Teams by SCAD league id", response = SCADLeagueTeamListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllSCADLeagueTeamsBySCADLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/yahoo/{leagueId}" + EndpointConstants.SCADLEAGUETEAM + "/all")
	@ApiOperation(value = "Get SCAD League Teams by yahoo league id", notes = "Returns SCAD League Teams by yahoo league id", response = SCADLeagueTeamListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllSCADLeagueTeamsByYahooLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/{leagueId}" + EndpointConstants.SCADLEAGUETEAM + "/{id}")
	@ApiOperation(value = "Get SCAD League Teams by SCAD league id and id", notes = "Returns SCAD League Teams by SCAD league id and id", response = SCADLeagueTeamListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueTeamBySCADLeagueAndTeam(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId,
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(EndpointConstants.ID) Long id)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/yahoo/{leagueId}" + EndpointConstants.SCADLEAGUETEAM + "/{teamId}")
	@ApiOperation(value = "Get SCAD League Teams by yahoo league id and team id", notes = "Returns SCAD League Teams by yahoo league id and team id", response = SCADLeagueTeamListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeagueTeamByYahooLeagueAndTeam(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId,
			@ApiParam(value = EndpointConstants.TEAM_RESOURCE_ID, required = true) @PathParam(EndpointConstants.TEAMID) Long teamId)
			throws AuthorizationFailedException, RuntimeException;

	@POST
	@Path(EndpointConstants.SCADLEAGUETEAM)
	@ApiOperation(value = "Create a new SCAD League Team resource", notes = "Add League Team additional settings in SCAD system", response = SCADLeagueTeamDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response createSCADLeagueTeam(
			@ApiParam(value = EndpointConstants.POSTPROPOSED, required = true) SCADLeagueTeamDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@PUT
	@Path(EndpointConstants.SCADLEAGUETEAM + "/{id}")
	@ApiOperation(value = "Update a SCAD League Team resource", notes = "Update League Team additional settings in SCAD system", response = SCADLeagueTeamDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateSCADLeagueTeam(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(value = EndpointConstants.ID) Long id,
			@ApiParam(value = EndpointConstants.PUTPROPOSED, required = true) SCADLeagueTeamDto proposed)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	@DELETE
	@Path(EndpointConstants.SCADLEAGUETEAM + "/{id}")
	@ApiOperation(value = "Delete a SCAD League Team resource", notes = "Delete League Team additional settings from SCAD system", response = Boolean.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteSCADLeagueTeam(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(value = EndpointConstants.ID) Long id)
			throws MissingParameterException, AuthorizationFailedException, RuntimeException;

	// League Players

	@GET
	@Path(EndpointConstants.SCADLEAGUEPLAYER + "/{id}")
	@ApiOperation(value = "Get SCAD League Player by id", notes = "Returns SCAD League Player by id", response = SCADLeaguePlayerDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeaguePlayer(
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(EndpointConstants.ID) Long id)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/{leagueId}" + EndpointConstants.SCADLEAGUEPLAYER + "/all")
	@ApiOperation(value = "Get SCAD League Players by SCAD league id", notes = "Returns SCAD League Players by SCAD league id", response = SCADLeaguePlayerListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllSCADLeaguePlayersBySCADLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/yahoo/{leagueId}" + EndpointConstants.SCADLEAGUEPLAYER + "/all")
	@ApiOperation(value = "Get SCAD League Players by yahoo league id", notes = "Returns SCAD League Players by yahoo league id", response = SCADLeaguePlayerListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllSCADLeaguePlayersByYahooLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/{leagueId}" + EndpointConstants.SCADLEAGUEPLAYER + "/{id}")
	@ApiOperation(value = "Get SCAD League Players by SCAD league id and id", notes = "Returns SCAD League Players by SCAD league id and id", response = SCADLeaguePlayerListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeaguePlayerBySCADLeagueAndPlayer(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId,
			@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) @PathParam(EndpointConstants.ID) Long id)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/yahoo/{leagueId}" + EndpointConstants.SCADLEAGUEPLAYER + "/{playerId}")
	@ApiOperation(value = "Get SCAD League Players by yahoo league id and player id", notes = "Returns SCAD League Players by yahoo league id and player id", response = SCADLeaguePlayerListDto.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSCADLeaguePlayerByYahooLeagueAndPlayer(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId,
			@ApiParam(value = EndpointConstants.PLAYER_RESOURCE_ID, required = true) @PathParam(EndpointConstants.PLAYERID) Long playerId)
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
