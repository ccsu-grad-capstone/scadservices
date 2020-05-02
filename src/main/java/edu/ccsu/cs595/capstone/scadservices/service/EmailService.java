package edu.ccsu.cs595.capstone.scadservices.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.util.EmailAdapter;
import edu.ccsu.cs595.capstone.scadservices.util.SCADMail;
import edu.ccsu.cs595.capstone.scadservices.util.YahooClientBuilder;

@Stateless
public class EmailService {

	private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);
	private static final String FAKEMGRS = "rameshkappera@my.ccsu.edu;pmurray@my.ccsu.edu;lauzonryd@my.ccsu.edu";
	//private static final String FAKEMGRS = "rameshkappera@my.ccsu.edu";

	@Inject
	LeagueService lSvc;

	@Inject
	EmailAdapter adapter;
	
	@Inject
	YahooClientBuilder yahoo;
	
	public String sendEmailToRegisteredUsers(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		String result = null;
		try {
			SCADMail mail = this.builSCADMailForRegisteredUsers(leagueId);
			if (Objects.nonNull(mail)) {
				LOG.info("Sending email to recipient(s)={}  cc'ed={}, for leagueId={} and LeagueName={}", mail.getRecipient(), mail.getSender(), mail.getLeagueId(), mail.getLeagueName());
				mail.setRecipient(FAKEMGRS); //Need to be removed.
				adapter.sendEmail(mail);
				result = this.buildResult("s", null);
			} else {
				result = this.buildResult("f", "SCAD mail object is empty");
			}
		} catch (RuntimeException ex) {
			result = this.buildResult("f", ex.getMessage());
		}
		
		return result;
		
	}
	
	public String sendRequestEmail(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		String result = null;
		try {
			SCADMail mail = this.builSCADMailForCommissioner(leagueId);
			if (Objects.nonNull(mail)) {
				LOG.info("Sending email to recipient(s)={}  cc'ed={}, for leagueId={} and LeagueName={}", mail.getRecipient(), mail.getSender(), mail.getLeagueId(), mail.getLeagueName());
				mail.setRecipient(FAKEMGRS); //Need to be removed.
				adapter.sendEmail(mail);
				result = this.buildResult("s", null);
			} else {
				result = this.buildResult("f", "SCAD mail object is empty");
			}
		} catch (RuntimeException ex) {
			result = this.buildResult("f", ex.getMessage());
		}
		
		return result;

		
	}
	
	private String buildResult(String result, String error) {
		
		String msgDetails = null;
		String msgBegin = "{\"key\":" ;
		String msgEnd = "}";
		String msgBodyKey = ", \"msg\":";
		String msgKey = null;
		String msgBody = null;
		if ("s".equals(result)) {
			msgKey = "\"success\"";
			msgBody = "\"Email sent successfully\"";
			msgDetails = msgBegin + msgKey + msgBodyKey + msgBody + msgEnd;
		} else {
			msgKey = "\"failure\"";
			msgBody = error;
			msgDetails = msgBody;
		}
		return msgDetails;
		
	}
	
	private SCADMail builSCADMailForRegisteredUsers(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		SCADMail scadMail = null;
		
		if (Objects.nonNull(leagueId)) {

				String ylStrg = lSvc.getYahooLeague(leagueId);
				JsonObject ylObj = new JsonParser().parse(ylStrg).getAsJsonObject();
				String ylNm = ylObj.get("name").getAsString();
				String ytsStrg = lSvc.getYahooLeagueTeams(leagueId);
				JsonObject ytsObj = new JsonParser().parse(ytsStrg).getAsJsonObject();
				JsonArray ytsArray = ytsObj.get("teams").getAsJsonArray();
				String mgrEmails = null;
				for (int i = 0; i < ytsArray.size(); i++) {
					JsonObject ytObj = ytsArray.get(i).getAsJsonObject();
					Long teamId = ytObj.get("team_id").getAsLong();
					JsonArray mgrsArray = ytObj.get("managers").getAsJsonArray();
					JsonObject mgrObj0 = mgrsArray.get(0).getAsJsonObject();
					JsonObject mgrObj = mgrObj0.get("manager").getAsJsonObject();
					String mgrEmail = null;
					try {
						mgrEmail = mgrObj.get("email").getAsString();
					} catch (RuntimeException ex) {
						LOG.error("No email exists for teamId={} for leagueId={}, error={}",teamId, leagueId, ex.getMessage());
					}
					if (Objects.nonNull(mgrEmail)) {
						if (Objects.isNull(mgrEmails)) {
							mgrEmails = mgrEmail + ";";
						} else {
							mgrEmails = mgrEmails + mgrEmail + ";";
						}
					}
				}
				scadMail = new SCADMail();
				scadMail.setLeagueId(leagueId);
				scadMail.setLeagueName(ylNm);
				scadMail.setSender(yahoo.getYahooUserEmail());
				scadMail.setRecipient(mgrEmails);
				String subject = ylNm + " (" + leagueId + ")" + " has been registered with SCAD";
				scadMail.setSubject(subject);
				String text = "The commissioner for " + ylNm + " (" + leagueId + ")" + " has registered your Yahoo Fantasy Football league with SCAD.\n\n";
				text = text + "Click the link below to log in and get started!\n\n\n" ;
				text = text + "https://scad-ui.firebaseapp.com\n\n\n";
				//text = text + "<a href='https://scad-ui.firebaseapp.com'>SCAD-UI</a>\n\n\n";
				text = text + getDateSent() + "\n\n";
				scadMail.setText(text);
				
		}
		
		return scadMail;
		
		
	}
	
	private SCADMail builSCADMailForCommissioner(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		SCADMail scadMail = null;
		
		if (Objects.nonNull(leagueId)) {

				String ylStrg = lSvc.getYahooLeague(leagueId);
				JsonObject ylObj = new JsonParser().parse(ylStrg).getAsJsonObject();
				String ylNm = ylObj.get("name").getAsString();
				String ytsStrg = lSvc.getYahooLeagueTeams(leagueId);
				JsonObject ytsObj = new JsonParser().parse(ytsStrg).getAsJsonObject();
				JsonArray ytsArray = ytsObj.get("teams").getAsJsonArray();
				String commEmail = null;
				for (int i = 0; i < ytsArray.size(); i++) {
					JsonObject ytObj = ytsArray.get(i).getAsJsonObject();
					Long teamId = ytObj.get("team_id").getAsLong();
					JsonArray mgrsArray = ytObj.get("managers").getAsJsonArray();
					JsonObject mgrObj0 = mgrsArray.get(0).getAsJsonObject();
					JsonObject mgrObj = mgrObj0.get("manager").getAsJsonObject();
					String mgrEmail = null;
					String isComm = null;
					try {
						mgrEmail = mgrObj.get("email").getAsString();
					} catch (RuntimeException ex) {
						LOG.error("No email exists for teamId={} for leagueId={}, error={}", teamId, leagueId, ex.getMessage());
					}
					try {
						isComm = mgrObj.get("is_commissioner").getAsString();
					} catch (RuntimeException ex) {
						LOG.error("This team {} manager is not commissioner for leagueId={}, error={}", teamId, leagueId, ex.getMessage());
					}
					if (Objects.nonNull(isComm)) {
						commEmail = mgrEmail;
						break;
					}
				}
				scadMail = new SCADMail();
				scadMail.setLeagueId(leagueId);
				scadMail.setLeagueName(ylNm);
				scadMail.setSender(yahoo.getYahooUserEmail());
				scadMail.setRecipient(commEmail);
				String subject = "SCAD League Registration Request";
				scadMail.setSubject(subject);
				String text = "A member of " + ylNm + " (" + leagueId + ")" + " is interested in registering your Yahoo Fantasy Football league with SCAD.\n\n";
				text = text + "Click the link below for more information.\n\n\n" ;
				text = text + "https://scad-ui.firebaseapp.com\n\n\n";
				//text = text + "<a href='https://scad-ui.firebaseapp.com'>SCAD-UI</a>\n\n\n";
				text = text + getDateSent() + "\n\n";
				scadMail.setText(text);
				
		}
		
		return scadMail;
		
		
	}
	
	private String getDateSent() {
		
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
		String formattedString = localDateTime.format(formatter);
		return ("Date Sent: " + formattedString);
		
	}
	
}
