package edu.ccsu.cs595.capstone.scadservices.api;

import javax.enterprise.context.ApplicationScoped;
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
import edu.ccsu.cs595.capstone.scadservices.exception.MissingParameterException;
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
    public Response getUserAllLeagues() throws RuntimeException;
	
	@GET
	@Path("/{leagueId}/teams")
    @ApiOperation(value = "Get selected League all Teams from yahoo", notes = "Returns selected League all Teams from yahoo", response = String.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserLeagueTeams(
    		@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) 
    		@PathParam(EndpointConstants.LEAGUEID) Long leagueId
    		) throws RuntimeException;
	
	@GET
	@Path("/{leagueId}")
    @ApiOperation(value = "Get User League from yahoo by \"league_id\"", notes = "Returns User League from yahoo by \"league_id\"", response = String.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserLeague(
    		@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) 
    		@PathParam(EndpointConstants.LEAGUEID) Long leagueId
    		) throws RuntimeException;

	@GET
	@Path("/{leagueId}/settings")
    @ApiOperation(value = "Get User League Settings from yahoo by \"league_id\"", notes = "Returns User League Settings from yahoo by \"league_id\"", response = String.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserLeagueSettings(
    		@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) 
    		@PathParam(EndpointConstants.LEAGUEID) Long leagueId
    		) throws RuntimeException;

	@GET
	@Path("/{leagueId}/standings")
    @ApiOperation(value = "Get User League Standings from yahoo by \"league_id\"", notes = "Returns User League Standings from yahoo by \"league_id\"", response = String.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserLeagueStandings(
    		@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) 
    		@PathParam(EndpointConstants.LEAGUEID) Long leagueId
    		) throws RuntimeException;
	
	@GET
	@Path("/{leagueId}/team/{teamId}/roster")
    @ApiOperation(value = "Get User League Team & Roster details from yahoo by \"league_id\" and \"teamId\"", notes = "Returns User League Team & Roster details from yahoo by \"league_id\" and \"teamId\"", response = String.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserLeagueTeamAndRoaster(
    		@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) 
    		@PathParam(EndpointConstants.LEAGUEID) Long leagueId, 
    		@ApiParam(value = EndpointConstants.TEAM_RESOURCE_ID, required = true) 
    		@PathParam(EndpointConstants.TEAMID) Long teamId
    		) throws RuntimeException;
	
	@GET
	@Path("/{leagueId}/players")
    @ApiOperation(value = "Get User League Players from yahoo by \"league_id\"", notes = "Returns User League Players from yahoo by \"league_id\"", response = String.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserLeaguePlayers(
    		@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) 
    		@PathParam(EndpointConstants.LEAGUEID) Long leagueId
    		) throws RuntimeException;
	
	@GET
	@Path("/scadleague/all")
    @ApiOperation(value = "Get User SCAD Leagues", notes = "Returns User SCAD Leagues", response = SCADLeagueListDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserAllSCADLeagues() throws RuntimeException;
	
	@GET
	@Path("/scadleague/{id}")
    @ApiOperation(value = "Get User SCAD League by id", notes = "Returns User SCAD League by id", response = SCADLeagueDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserSCADLeague(
    		@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) 
    		@PathParam(EndpointConstants.ID) Long id
    		) throws RuntimeException;
	
	@POST
	@Path("/scadleague")
    @ApiOperation(value = "Create a new SCAD League resource", notes = "Add League additional settings in SCAD system", response = SCADLeagueDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response createSCADLeague(
    		@ApiParam(value = EndpointConstants.POSTPROPOSED, required = true)
    		SCADLeagueDto proposed
    		) throws MissingParameterException, RuntimeException;
	
	@PUT
	@Path("/scadleague/{id}")
    @ApiOperation(value = "Update a SCAD League resource", notes = "Update League additional settings in SCAD system", response = SCADLeagueDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response updateSCADLeague(
    		@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) 
    		@PathParam(value = EndpointConstants.ID) Long id,
    		@ApiParam(value = EndpointConstants.PUTPROPOSED, required = true)
    		SCADLeagueDto proposed
    		) throws MissingParameterException, RuntimeException;
	
	@DELETE
	@Path("/scadleague/{id}")
    @ApiOperation(value = "Delete a SCAD League resource", notes = "Delete League additional settings from SCAD system", response = Boolean.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteSCADLeague(
    		@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) 
    		@PathParam(value = EndpointConstants.ID) Long id
    		) throws MissingParameterException, RuntimeException;
    
}
