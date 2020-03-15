package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.api.UserApi;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.UserService;

@SuppressWarnings("static-access")
public class UserApiImpl implements UserApi {

	@Inject
	UserService usrSvc;

	@Inject
	SCADSecurityManager sm;

	//
//	@Override
//	public Response getAll() {
//
//		UserListDto result = usrSvc.getAllUsers();
//
//		if (Objects.isNull(result)) {
//
//			return Response.status(Response.Status.NOT_FOUND).build();
//		}
//
//		return Response.status(Response.Status.OK).entity(result).build();
//
//	}
//
//	@Override
//	public Response isValidUser(String email, String password) {
//
//		Boolean result = usrSvc.isValidUser(email, password);
//
//		return Response.status(Response.Status.OK).entity(result).build();
//
//	}
//
//	@Override
//	public Response create(UserDto proposed) throws MissingParameterException, RuntimeException {
//
//		String missingParam = getMissingRequiredParams(proposed);
//		if (Objects.nonNull(missingParam)) {
//			throw new MissingParameterException(missingParam);
//		} else {
//			try {
//		    	UserDto result = usrSvc.createUser(proposed);
//		    	return Response.status(Response.Status.OK).entity(result).build();
//			} catch (Exception e) {
//				return Response.ok(e.getMessage()).build();
//			}
//		}
//		
//	}
//
//	@Override
//	public Response update(Long id, UserDto proposed) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Response delete(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	String getMissingRequiredParams(UserDto proposed) {
//
//		String missingParam = null;
//		if (Objects.isNull(proposed)) {
//			missingParam = "proposed object";
//		} else {
//			if (Objects.isNull(proposed.getFirstName())) {
//				missingParam = "first name";
//			} else if (Objects.isNull(proposed.getLastName())) {
//				missingParam = "last name";
//			} else if (Objects.isNull(proposed.getEmail())) {
//				missingParam = "email";
//			} else if (Objects.isNull(proposed.getPassword())) {
//				missingParam = "password";
//			}
//
//		}
//
//		return missingParam;
//
//	}

	@Override
	public Response getUserInfo() throws RuntimeException {
		String idToken = sm.getIDTOKEN();

		if ((Objects.isNull(idToken))) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Missing SCAD idToken").build();
		}

		// Verify the JWT
		if (!usrSvc.verifyUser(idToken)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("SCAD user JWT failed to verify").build();
		}

		UserDto userDto = usrSvc.createUserDtoFromJwt(idToken);

		return Response.status(Response.Status.OK).entity(userDto).build();
		
	}

}
