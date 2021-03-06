package edu.ccsu.cs595.capstone.scadservices.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SCADLeagueTeamDto extends AuditedDto {
	
	private Long id;
	private Long yahooLeagueTeamId;
	private Long yahooLeagueId;
	private Long scadLeagueId;
	private Long salary;
	private Boolean isFranchiseTag; 
	private Long exceptionIn;
	private Long exceptionOut;
	private Long renewSCADLeagueTeamId;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getYahooLeagueTeamId() {
		return yahooLeagueTeamId;
	}

	public void setYahooLeagueTeamId(Long yahooLeagueTeamId) {
		this.yahooLeagueTeamId = yahooLeagueTeamId;
	}

	public Long getYahooLeagueId() {
		return yahooLeagueId;
	}

	public void setYahooLeagueId(Long yahooLeagueId) {
		this.yahooLeagueId = yahooLeagueId;
	}

	public Long getScadLeagueId() {
		return scadLeagueId;
	}

	public void setScadLeagueId(Long scadLeagueId) {
		this.scadLeagueId = scadLeagueId;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	public Boolean getIsFranchiseTag() {
		return isFranchiseTag;
	}

	public void setIsFranchiseTag(Boolean isFranchiseTag) {
		this.isFranchiseTag = isFranchiseTag;
	}

	public Long getExceptionIn() {
		return exceptionIn;
	}

	public void setExceptionIn(Long exceptionIn) {
		this.exceptionIn = exceptionIn;
	}

	public Long getExceptionOut() {
		return exceptionOut;
	}

	public void setExceptionOut(Long exceptionOut) {
		this.exceptionOut = exceptionOut;
	}
	
	public Long getRenewSCADLeagueTeamId() {
		return renewSCADLeagueTeamId;
	}

	public void setRenewSCADLeagueTeamId(Long renewSCADLeagueTeamId) {
		this.renewSCADLeagueTeamId = renewSCADLeagueTeamId;
	}

	@Override
	public String toString() {
		return "SCADLeagueTeamDto [id=" + id + ", yahooLeagueTeamId=" + yahooLeagueTeamId + ", yahooLeagueId="
				+ yahooLeagueId + ", scadLeagueId=" + scadLeagueId + ", salary=" + salary + ", isFranchiseTag="
				+ isFranchiseTag + ", exceptionIn=" + exceptionIn + ", exceptionOut=" + exceptionOut
				+ ", renewSCADLeagueTeamId=" + renewSCADLeagueTeamId + "]";
	}

}
