package edu.ccsu.cs595.capstone.scadservices.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.EndpointConstants;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ApplicationScoped
@Path(EndpointConstants.USER)
@Api(value = EndpointConstants.USER, description = "Get the User", tags = "User")
public interface UserApi {
   
	@GET
    @ApiOperation(value = "Get the User by email", notes = "Returns the User details by email", response = UserDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response get(
    					@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true)
    					@QueryParam(EndpointConstants.EMAIL) String email
    					);
    
//    @GET
//    @ApiOperation(value = "Get all Users", notes = "Returns all Users", response = UserListDto.class)
//    @Produces({ MediaType.APPLICATION_JSON })
//    public Response getAll();
    
}
