package edu.ccsu.cs595.capstone.scadservices.resource;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.rest.UserApi;
import edu.ccsu.cs595.capstone.scadservices.service.UserService;


public class UserApiImpl implements UserApi {
	
	@Inject
	UserService usrSvc;
	
    public Response get(String email) {
		
    	UserDto result = usrSvc.getUser(email);
    	
    	if (Objects.isNull(result)) {
    		
    		return Response.ok(result).build();
    	}
    	
       return Response.ok(result).build();
    }
	    
//    public Response getAll() {
//    
//    	return Response.ok(UserDto().build();
//
//    }
	
}
