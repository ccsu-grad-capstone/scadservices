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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.EndpointConstants;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.dto.UserListDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ApplicationScoped
@Path(EndpointConstants.USER)
@Api(value = EndpointConstants.USER, tags = "User", description = "Get the User")
public interface UserApi {
   
	@GET
	@Path("/email")
    @ApiOperation(value = "Get a User by email", notes = "Returns a User by email", response = UserDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response get(
    					@ApiParam(value = EndpointConstants.REGEMAIL, required = true)
    					@QueryParam(EndpointConstants.EMAIL) String email
    					);
	
	@GET
	@Path("/all")
    @ApiOperation(value = "Get all Users", notes = "Returns all Users", response = UserListDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getAll();
	
	@GET
	@Path("/valid")
    @ApiOperation(value = "Validate a User", notes = "Validate a User with provided email and password", response = Boolean.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response isValidUser(
								@ApiParam(value = EndpointConstants.REGEMAIL, required = true)
								@QueryParam(EndpointConstants.EMAIL) String email,
								@ApiParam(value = EndpointConstants.PWD, required = true)
								@QueryParam(EndpointConstants.PASSWORD) String password
    							);
    
	@POST
    @ApiOperation(value = "Create a new User resource", notes = "Create a new User for SCAD system", response = UserDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
    						@ApiParam(value = EndpointConstants.POSTPROPOSED, required = true)
    						UserDto proposed
    						);
	
	@PUT
	@Path("/{id}")
    @ApiOperation(value = "Update a User resource", notes = "Update proposed User changes for SCAD system", response = UserDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
    public Response update(
    						@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) 
    						@PathParam(value = EndpointConstants.ID) Long id,
							@ApiParam(value = EndpointConstants.PUTPROPOSED, required = true)
							UserDto proposed
    					);

	@DELETE
	@Path("/{id}")
    @ApiOperation(value = "Delete a User resource", notes = "Delete a user from SCAD system", response = Boolean.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response delete(
    						@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) 
    						@PathParam(value = EndpointConstants.ID) Long id
    					);
	
    
}
