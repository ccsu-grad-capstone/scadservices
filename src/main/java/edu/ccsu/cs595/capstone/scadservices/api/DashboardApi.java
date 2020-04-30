package edu.ccsu.cs595.capstone.scadservices.api;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.EndpointConstants;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@ApplicationScoped
@Path(EndpointConstants.SCAD)
@Api(value = EndpointConstants.DASHBOARD, tags = "Dashboard", description = "Get SCAD Dashboard details")
public interface DashboardApi {
   
	@GET
	@Path(EndpointConstants.DASHBOARD + "/details")
    @ApiOperation(value = "Gets Dashboard details for SCAD UI", notes = "Returns Dashboard details for SCAD UI", response = String.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getDashboardDetails() throws AuthorizationFailedException, RuntimeException;
	
}
