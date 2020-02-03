package edu.ccsu.cs595.capstone.scadservices.exception;

public class MissingParameterException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public MissingParameterException(String parameter) {
		super("Parameter " + parameter + " is required.");
    }

}
