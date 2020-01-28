package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.api.UserApi;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.service.UserService;


public class UserApiImpl implements UserApi {
	
	@Inject
	UserService usrSvc;
	
    public Response get(String email) {
		
    	UserDto result = usrSvc.getUser(email);
    	
    	if (Objects.isNull(result)) {
    		
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	
       return Response.status(Response.Status.OK).entity(result).build();
       
    }

	@Override
	public Response getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response isValidUser(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response create(UserDto proposed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response update(Long id, UserDto proposed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	    
//    public Response getAll() {
//    
//    	return Response.ok(UserDto().build();
//
//    }
	
}
