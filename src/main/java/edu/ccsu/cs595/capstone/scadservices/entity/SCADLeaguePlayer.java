package edu.ccsu.cs595.capstone.scadservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * The persistent class for the SCAD_LEAGUE_PLAYER database table in SCAD
 * schema.
 */

@Entity
@Table(name = "scad_league_player", schema = "scad_schema")
public class SCADLeaguePlayer extends AuditedEntity {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long yahooLeaguePlayerId;
	private Long yahooLeagueId;
	private Long scadLeagueId;
	private Long salary;
	private Boolean isFranchiseTag;

	@Id
	@Column(name = "scad_league_player_id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "yahoo_league_player_id", nullable = false)
	public Long getYahooLeaguePlayerId() {
		return yahooLeaguePlayerId;
	}

	public void setYahooLeaguePlayerId(Long yahooLeaguePlayerId) {
		this.yahooLeaguePlayerId = yahooLeaguePlayerId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isFranchiseTag == null) ? 0 : isFranchiseTag.hashCode());
		result = prime * result + ((yahooLeagueId == null) ? 0 : yahooLeagueId.hashCode());
		result = prime * result + ((yahooLeaguePlayerId == null) ? 0 : yahooLeaguePlayerId.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		result = prime * result + ((scadLeagueId == null) ? 0 : scadLeagueId.hashCode());
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
		SCADLeaguePlayer other = (SCADLeaguePlayer) obj;
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
		if (yahooLeagueId == null) {
			if (other.yahooLeagueId != null)
				return false;
		} else if (!yahooLeagueId.equals(other.yahooLeagueId))
			return false;
		if (yahooLeaguePlayerId == null) {
			if (other.yahooLeaguePlayerId != null)
				return false;
		} else if (!yahooLeaguePlayerId.equals(other.yahooLeaguePlayerId))
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
		return true;
	}

	@Override
	public String toString() {
		return "SCADLeaguePlayer [id=" + id + ", yahooLeaguePlayerId=" + yahooLeaguePlayerId + ", yahooLeagueId=" + yahooLeagueId
				+ ", scadLeagueId=" + scadLeagueId + ", salary=" + salary + ", isFranchiseTag=" + isFranchiseTag + "]";
	}

}
