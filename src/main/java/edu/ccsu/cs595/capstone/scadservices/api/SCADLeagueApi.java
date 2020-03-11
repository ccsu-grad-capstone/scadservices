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
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.exception.MissingParameterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ApplicationScoped
@Path(EndpointConstants.SCADLEAGUE)
@Api(value = EndpointConstants.SCADLEAGUE, tags = "SCAD League", description = "Get User SCAD League details")
public interface SCADLeagueApi {
   
	@GET
	@Path("/default")
    @ApiOperation(value = "Get User default SCAD League", notes = "Returns User default SCAD League", response = SCADLeagueDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserDefaultSCADLeague() throws AuthorizationFailedException, RuntimeException;
	
	@GET
	@Path("/all")
    @ApiOperation(value = "Get User SCAD Leagues", notes = "Returns User SCAD Leagues", response = SCADLeagueListDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserAllSCADLeagues() throws AuthorizationFailedException, RuntimeException;
	
	@GET
	@Path("/{id}")
    @ApiOperation(value = "Get User SCAD League by id", notes = "Returns User SCAD League by id", response = SCADLeagueDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserSCADLeague(
    		@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) 
    		@PathParam(EndpointConstants.ID) Long id
    		) throws AuthorizationFailedException, RuntimeException;
	
	@POST
    @ApiOperation(value = "Create a new SCAD League resource", notes = "Add League additional settings in SCAD system", response = SCADLeagueDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response createSCADLeague(
    		@ApiParam(value = EndpointConstants.POSTPROPOSED, required = true)
    		SCADLeagueDto proposed
    		) throws MissingParameterException, AuthorizationFailedException, RuntimeException;
	
	@PUT
	@Path("/{id}")
    @ApiOperation(value = "Update a SCAD League resource", notes = "Update League additional settings in SCAD system", response = SCADLeagueDto.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response updateSCADLeague(
    		@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) 
    		@PathParam(value = EndpointConstants.ID) Long id,
    		@ApiParam(value = EndpointConstants.PUTPROPOSED, required = true)
    		SCADLeagueDto proposed
    		) throws MissingParameterException, AuthorizationFailedException, RuntimeException;
	
	@DELETE
	@Path("/{id}")
    @ApiOperation(value = "Delete a SCAD League resource", notes = "Delete League additional settings from SCAD system", response = Boolean.class)
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteSCADLeague(
    		@ApiParam(value = EndpointConstants.RESOURCE_ID, required = true) 
    		@PathParam(value = EndpointConstants.ID) Long id
    		) throws MissingParameterException, AuthorizationFailedException, RuntimeException;
    
}
