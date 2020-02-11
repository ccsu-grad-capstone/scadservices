package edu.ccsu.cs595.capstone.scadservices.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class MissingParameterMapper implements ExceptionMapper<MissingParameterException> {

	@Override
	public Response toResponse(MissingParameterException e) {
		return Response.ok(e.getMessage()).build();
	}
	

}
