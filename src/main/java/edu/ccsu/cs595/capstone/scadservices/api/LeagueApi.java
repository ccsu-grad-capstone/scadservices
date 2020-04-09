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
@Path(EndpointConstants.YAHOO)
@Api(value = EndpointConstants.LEAGUE, tags = "Yahoo APIs", description = "Get league, team and player settings from yahoo")
public interface LeagueApi {

	@GET
	@Path(EndpointConstants.LEAGUE + "/all")
	@ApiOperation(value = "Gets user Leagues from yahoo by season", notes = "Returns user Leagues from yahoo by season", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserLeaguesBySeason() throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path(EndpointConstants.LEAGUE + "/commissioner/all")
	@ApiOperation(value = "Gets user Leagues of which a user is a commissioner by season", notes = "Returns user Leagues from yahoo as league commissioner by season", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserLeaguesAsCommissionerBySeason() throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path(EndpointConstants.LEAGUE + "/{leagueId}")
	@ApiOperation(value = "Get specific League from yahoo by leagueId", notes = "Returns specific League from yahoo by leagueId", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getYahooLeague(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path(EndpointConstants.LEAGUE + "/{leagueId}/teams")
	@ApiOperation(value = "Gets all Teams from yahoo by leagueId", notes = "Returns all Teams from yahoo by leagueId", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getYahooLeagueTeams(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path(EndpointConstants.LEAGUE + "/{leagueId}/myTeam")
	@ApiOperation(value = "Gets MyTeam from yahoo by leagueId", notes = "Returns MyTeam from yahoo by leagueId", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getYahooLeagueMyTeam(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path(EndpointConstants.LEAGUE + "/{leagueId}/myPlayers")
	@ApiOperation(value = "Gets MyPlayers from yahoo by leagueId", notes = "Returns MyPlayers from yahoo by leagueId", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getYahooLeagueMyPlayers(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;
	
	@GET
	@Path(EndpointConstants.LEAGUE + "/{leagueId}/settings")
	@ApiOperation(value = "Gets Settings from yahoo by leagueId", notes = "Returns Settings from yahoo by leagueId", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getYahooLeagueSettings(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path(EndpointConstants.LEAGUE + "/{leagueId}/standings")
	@ApiOperation(value = "Gets Standings from yahoo by leagueId", notes = "Returns Standings from yahoo by leagueId", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getYahooLeagueStandings(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path(EndpointConstants.LEAGUE + "/{leagueId}/team/{teamId}")
	@ApiOperation(value = "Gets Team & Roster details from yahoo by leagueId and teamId", notes = "Returns Team & Roster details from yahoo by leagueId and teamId", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getYahooLeagueTeamAndRoster(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId,
			@ApiParam(value = EndpointConstants.TEAM_RESOURCE_ID, required = true) @PathParam(EndpointConstants.TEAMID) Long teamId,
			@ApiParam(value = "NFL week associated with desired roster.") @QueryParam("week") Long week)
			throws AuthorizationFailedException, RuntimeException;

	@GET
	@Path(EndpointConstants.LEAGUE + "/{leagueId}/players")
	@ApiOperation(value = "Gets all Players from yahoo by leagueId", notes = "Returns all Players from yahoo by leagueId", response = String.class)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getYahooLeaguePlayers(
			@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
			throws AuthorizationFailedException, RuntimeException;

}
