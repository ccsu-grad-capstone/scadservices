package edu.ccsu.cs595.capstone.scadservices.api;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.EndpointConstants;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ApplicationScoped
@Path(EndpointConstants.SCAD)
@Api(value = EndpointConstants.SCADEMAIL, tags = "Email", description = "Email Service")
public interface EmailApi {
   
	@POST
	@Path(EndpointConstants.SCADEMAIL + "/registered/{leagueId}")
    @ApiOperation(value = "Send email to league registered users", notes = "Returns success details", response = String.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response sendEmailToRegisteredUsers(@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
    		throws AuthorizationFailedException, RuntimeException;

	@POST
	@Path(EndpointConstants.SCADEMAIL + "/request/{leagueId}")
    @ApiOperation(value = "Send email to league owner", notes = "Returns success details", response = String.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response sendRequestEmail(@ApiParam(value = EndpointConstants.LEAGUE_RESOURCE_ID, required = true) @PathParam(EndpointConstants.LEAGUEID) Long leagueId)
    		throws AuthorizationFailedException, RuntimeException;

	
}
