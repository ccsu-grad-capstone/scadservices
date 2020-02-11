package edu.ccsu.cs595.capstone.scadservices.security;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Singleton
public class SCADSecurityManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(SCADSecurityManager.class);
	
	public static final String ID_TOKEN_HEADER = "id_token";
	public static final String ACCESS_TOKEN_HEADER = "access_token";
	
	private static ThreadLocal<String> IDTOKEN = new ThreadLocal<>();
	private static ThreadLocal<String> ACCESSTOKEN = new ThreadLocal<>();

	public static String getIDTOKEN() {
		return IDTOKEN.get();
	}
	public static void setIDTOKEN(String iDTOKEN) {
		IDTOKEN.set(iDTOKEN);;
	}
	public static String getACCESSTOKEN() {
		return ACCESSTOKEN.get();
	}
	public static void setACCESSTOKEN(String aCCESSTOKEN) {
		ACCESSTOKEN.set(aCCESSTOKEN);
	}

}
