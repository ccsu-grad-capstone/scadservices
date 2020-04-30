package edu.ccsu.cs595.capstone.scadservices.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SCADLeaguePlayerDto extends AuditedDto {
	
	private Long id;
	private Long yahooLeaguePlayerId;
	private Long yahooLeagueId;
	private Long scadLeagueId;
	private Long yahooTeamId;
	private Long scadTeamId;
	private Long salary;
	private Boolean isFranchiseTag;
	private Long renewSCADLeaguePlayerId;
	private Long previousYearSalary;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getYahooLeaguePlayerId() {
		return yahooLeaguePlayerId;
	}

	public void setYahooLeaguePlayerId(Long yahooLeaguePlayerId) {
		this.yahooLeaguePlayerId = yahooLeaguePlayerId;
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

	public Long getYahooTeamId() {
		return yahooTeamId;
	}

	public void setYahooTeamId(Long yahooTeamId) {
		this.yahooTeamId = yahooTeamId;
	}

	public Long getScadTeamId() {
		return scadTeamId;
	}

	public void setScadTeamId(Long scadTeamId) {
		this.scadTeamId = scadTeamId;
	}

	public Long getRenewSCADLeaguePlayerId() {
		return renewSCADLeaguePlayerId;
	}

	public void setRenewSCADLeaguePlayerId(Long renewSCADLeaguePlayerId) {
		this.renewSCADLeaguePlayerId = renewSCADLeaguePlayerId;
	}

	public Long getPreviousYearSalary() {
		return previousYearSalary;
	}

	public void setPreviousYearSalary(Long previousYearSalary) {
		this.previousYearSalary = previousYearSalary;
	}

	@Override
	public String toString() {
		return "SCADLeaguePlayerDto [id=" + id + ", yahooLeaguePlayerId=" + yahooLeaguePlayerId + ", yahooLeagueId="
				+ yahooLeagueId + ", scadLeagueId=" + scadLeagueId + ", yahooTeamId=" + yahooTeamId + ", scadTeamId="
				+ scadTeamId + ", salary=" + salary + ", isFranchiseTag=" + isFranchiseTag
				+ ", renewSCADLeaguePlayerId=" + renewSCADLeaguePlayerId + ", previousYearSalary=" + previousYearSalary
				+ "]";
	}
	
}
