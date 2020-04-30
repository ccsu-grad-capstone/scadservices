package edu.ccsu.cs595.capstone.scadservices.util;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SCADMail {
	
	private Long leagueId;
	private String leagueName;
	private String sender;
	private String recipient;
    private String subject;
    private String text;
    
	public Long getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "SCADMail [leagueId=" + leagueId + ", leagueName=" + leagueName + ", sender=" + sender + ", recipient="
				+ recipient + ", subject=" + subject + ", text=" + text + "]";
	}

}
