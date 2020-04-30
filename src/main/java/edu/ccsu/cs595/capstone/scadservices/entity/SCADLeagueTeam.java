package edu.ccsu.cs595.capstone.scadservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * The persistent class for the SCAD_LEAGUE_TEAM database table in SCAD schema.
 */

@Entity
@Table(name = "scad_league_team", schema = "scad_schema")
public class SCADLeagueTeam extends AuditedEntity {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long yahooLeagueTeamId;
	private Long yahooLeagueId;
	private Long scadLeagueId;
	private Long salary;
	private Boolean isFranchiseTag;
	private Long exceptionIn;
	private Long exceptionOut;
	private Long renewSCADLeagueTeamId;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "scad_league_team_id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "yahoo_league_team_id", nullable = false)
	public Long getYahooLeagueTeamId() {
		return yahooLeagueTeamId;
	}

	public void setYahooLeagueTeamId(Long yahooLeagueTeamId) {
		this.yahooLeagueTeamId = yahooLeagueTeamId;
	}

	@Column(name = "yahoo_league_id", nullable = false)
	public Long getYahooLeagueId() {
		return yahooLeagueId;
	}

	public void setYahooLeagueId(Long yahooLeagueId) {
		this.yahooLeagueId = yahooLeagueId;
	}

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "scad_league_id", nullable = false)
	@Column(name = "scad_league_id", nullable = false)
	public Long getScadLeagueId() {
		return scadLeagueId;
	}

	public void setScadLeagueId(Long scadLeagueId) {
		this.scadLeagueId = scadLeagueId;
	}

	@Column(name = "salary", nullable = false)
	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	@Column(name = "franchise_tag", nullable = false)
	public Boolean getIsFranchiseTag() {
		return isFranchiseTag;
	}

	public void setIsFranchiseTag(Boolean isFranchiseTag) {
		this.isFranchiseTag = isFranchiseTag;
	}

	@Column(name = "exception_in", nullable = false)
	public Long getExceptionIn() {
		return exceptionIn;
	}

	public void setExceptionIn(Long exceptionIn) {
		this.exceptionIn = exceptionIn;
	}

	@Column(name = "exception_out", nullable = false)
	public Long getExceptionOut() {
		return exceptionOut;
	}

	public void setExceptionOut(Long exceptionOut) {
		this.exceptionOut = exceptionOut;
	}

	@Column(name = "renew_scad_league_team_id", nullable = false)
	public Long getRenewSCADLeagueTeamId() {
		return renewSCADLeagueTeamId;
	}

	public void setRenewSCADLeagueTeamId(Long renewSCADLeagueTeamId) {
		this.renewSCADLeagueTeamId = renewSCADLeagueTeamId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((exceptionIn == null) ? 0 : exceptionIn.hashCode());
		result = prime * result + ((exceptionOut == null) ? 0 : exceptionOut.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isFranchiseTag == null) ? 0 : isFranchiseTag.hashCode());
		result = prime * result + ((renewSCADLeagueTeamId == null) ? 0 : renewSCADLeagueTeamId.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		result = prime * result + ((scadLeagueId == null) ? 0 : scadLeagueId.hashCode());
		result = prime * result + ((yahooLeagueId == null) ? 0 : yahooLeagueId.hashCode());
		result = prime * result + ((yahooLeagueTeamId == null) ? 0 : yahooLeagueTeamId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SCADLeagueTeam other = (SCADLeagueTeam) obj;
		if (exceptionIn == null) {
			if (other.exceptionIn != null)
				return false;
		} else if (!exceptionIn.equals(other.exceptionIn))
			return false;
		if (exceptionOut == null) {
			if (other.exceptionOut != null)
				return false;
		} else if (!exceptionOut.equals(other.exceptionOut))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isFranchiseTag == null) {
			if (other.isFranchiseTag != null)
				return false;
		} else if (!isFranchiseTag.equals(other.isFranchiseTag))
			return false;
		if (renewSCADLeagueTeamId == null) {
			if (other.renewSCADLeagueTeamId != null)
				return false;
		} else if (!renewSCADLeagueTeamId.equals(other.renewSCADLeagueTeamId))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		if (scadLeagueId == null) {
			if (other.scadLeagueId != null)
				return false;
		} else if (!scadLeagueId.equals(other.scadLeagueId))
			return false;
		if (yahooLeagueId == null) {
			if (other.yahooLeagueId != null)
				return false;
		} else if (!yahooLeagueId.equals(other.yahooLeagueId))
			return false;
		if (yahooLeagueTeamId == null) {
			if (other.yahooLeagueTeamId != null)
				return false;
		} else if (!yahooLeagueTeamId.equals(other.yahooLeagueTeamId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SCADLeagueTeam [id=" + id + ", yahooLeagueTeamId=" + yahooLeagueTeamId + ", yahooLeagueId="
				+ yahooLeagueId + ", scadLeagueId=" + scadLeagueId + ", salary=" + salary + ", isFranchiseTag="
				+ isFranchiseTag + ", exceptionIn=" + exceptionIn + ", exceptionOut=" + exceptionOut
				+ ", renewSCADLeagueTeamId=" + renewSCADLeagueTeamId + "]";
	}

}
