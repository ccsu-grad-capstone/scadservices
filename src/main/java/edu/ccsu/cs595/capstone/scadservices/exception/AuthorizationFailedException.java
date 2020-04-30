package edu.ccsu.cs595.capstone.scadservices.exception;

public class AuthorizationFailedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public AuthorizationFailedException(String parameter) {
		super("Access Token Error: " + parameter );
    }

}
