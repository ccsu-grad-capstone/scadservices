package edu.ccsu.cs595.capstone.scadservices.api;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.EndpointConstants;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ApplicationScoped
@Path(EndpointConstants.LEAGUE)
@Api(value = EndpointConstants.LEAGUE, tags = "League", description = "Get User League details")
public interface LeagueApi {

	@GET
	@Path("/all")
	@ApiOperation(value = "Get User Leagues from yahoo", notes = "Returns User Leagues from yahoo", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserAllLeagues() throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/commissioner/all")
	@ApiOperation(value = "Get User Leagues from yahoo as League Commissioner", notes = "Returns User Leagues from yahoo as League Commissioner", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserAllLeaguesAsCommissioner() throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/{leagueId}/teams")
	@ApiOperation(value = "Get selected League all Teams from yahoo", notes = "Returns selected League all Teams from yahoo", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserLeagueTeams(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/{leagueId}")
	@ApiOperation(value = "Get User League from yahoo by \"league_id\"", notes = "Returns User League from yahoo by \"league_id\"", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/{leagueId}/settings")
	@ApiOperation(value = "Get User League Settings from yahoo by \"league_id\"", notes = "Returns User League Settings from yahoo by \"league_id\"", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserLeagueSettings(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/{leagueId}/standings")
	@ApiOperation(value = "Get User League Standings from yahoo by \"league_id\"", notes = "Returns User League Standings from yahoo by \"league_id\"", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserLeagueStandings(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/{leagueId}/team/{teamId}/roster")
	@ApiOperation(value = "Get User League Team & Roster details from yahoo by \"league_id\" and \"teamId\"", notes = "Returns User League Team & Roster details from yahoo by \"league_id\" and \"teamId\"", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserLeagueTeamAndRoster(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId,
			@ApiParam(value = EndpointConstants.TEAM_RESOURCE_ID, required = true) @PathParam(EndpointConstants.TEAMID) Long teamId,
			@ApiParam(value = "NFL week associated with desired roster.") @QueryParam("week") Long week)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path("/{leagueId}/players")
	@ApiOperation(value = "Get User League Players from yahoo by \"league_id\"", notes = "Returns User League Players from yahoo by \"league_id\"", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserLeaguePlayers(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

}
