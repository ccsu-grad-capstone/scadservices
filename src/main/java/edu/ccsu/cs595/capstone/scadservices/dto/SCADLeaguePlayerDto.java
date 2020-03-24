package edu.ccsu.cs595.capstone.scadservices.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SCADLeaguePlayerDto extends AuditedDto {
	
	private Long id;
	private Long yahooLeaguePlayerId;
	private Long yahooLeagueId;
	private Long scadLeagueId;
	private Long salary;
	private Boolean isFranchiseTag; 
	
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

	@Override
	public String toString() {
		return "SCADLeaguePlayerDto [yahooLeaguePlayerId=" + yahooLeaguePlayerId + ", yahooLeagueId=" + yahooLeagueId
				+ ", scadLeagueId=" + scadLeagueId + ", salary=" + salary + ", isFranchiseTag=" + isFranchiseTag + "]";
	}

}
