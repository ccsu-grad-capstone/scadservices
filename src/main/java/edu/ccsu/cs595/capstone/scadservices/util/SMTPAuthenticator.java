package edu.ccsu.cs595.capstone.scadservices.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Class to authenticate the user name and password to send email
 * 
 * @author kapperar
 *
 */

public class SMTPAuthenticator extends Authenticator {
	
	  private final PasswordAuthentication passwordAuthentication;

	  SMTPAuthenticator(String username, String password) {
	    this.passwordAuthentication = new PasswordAuthentication(username, password);
	  }

	  @Override
	protected PasswordAuthentication getPasswordAuthentication() {
	    return passwordAuthentication;
	  }

}

