package edu.ccsu.cs595.capstone.scadservices.api;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.EndpointConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@ApplicationScoped
@Path(EndpointConstants.LEAGUE)
@Api(value = EndpointConstants.LEAGUE, tags = "League", description = "Get User Leagues")
public interface LeagueApi {
   
	@GET
	@Path("/{id}")
    @ApiOperation(value = "Get User League from yahoo by \"league_id\"", notes = "Returns User League from yahoo by \"league_id\"", response = String.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getLeague(@PathParam(EndpointConstants.ID) Long id) throws RuntimeException;
	
	@GET
	@Path("/all")
    @ApiOperation(value = "Get User Leagues from yahoo", notes = "Returns User Leagues from yahoo", response = String.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getAllLeagues() throws RuntimeException;
	
    
}
