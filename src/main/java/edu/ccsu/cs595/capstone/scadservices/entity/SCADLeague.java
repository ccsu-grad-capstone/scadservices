package edu.ccsu.cs595.capstone.scadservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * The persistent class for the SCAD_LEAGUE database table in SCAD schema.
 */

@Entity
@Table(name = "scad_league", schema = "scad_schema")
public class SCADLeague extends AuditedEntity {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long yahooGameId;
	private Long yahooLeagueId;
	private Long seasonYear;
	private Long leagueManagers;
	private Long rookieDraftRds;
	private String rookieDraftStrategy;
	private String rookieWageScale;
	private Long teamSalaryCap;
	private Long leagueSalaryCap;
	private Long salaryCapExemptionLimit;
	private Long irReliefPerc;
	private Long franchiseTagReliefPerc;
	private Long franchiseTagSpots;
	private Long tradingDraftPickYears;
	private Long qbMin;
	private Long qbMax;
	private Long rbMin;
	private Long rbMax;
	private Long wrMin;
	private Long wrMax;
	private Long teMin;
	private Long teMax;
	private Long kMin;
	private Long kMax;
	private Long defMin;
	private Long defMax;
	private Boolean isDefault;
	private String ownerGuid;
//	private Set<SCADLeagueTeam> scadLeagueTeams;
//	private Set<SCADLeaguePlayer> scadLeaguePlayers;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "scad_league_id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "yahoo_game_id", nullable = false)
	public Long getYahooGameId() {
		return yahooGameId;
	}

	public void setYahooGameId(Long yahooGameId) {
		this.yahooGameId = yahooGameId;
	}

	@Column(name = "yahoo_league_id", nullable = false)
	public Long getYahooLeagueId() {
		return yahooLeagueId;
	}

	public void setYahooLeagueId(Long yahooLeagueId) {
		this.yahooLeagueId = yahooLeagueId;
	}

	@Column(name = "season_year", nullable = false)
	public Long getSeasonYear() {
		return seasonYear;
	}

	public void setSeasonYear(Long seasonYear) {
		this.seasonYear = seasonYear;
	}

	@Column(name = "league_managers", nullable = false)
	public Long getLeagueManagers() {
		return leagueManagers;
	}

	public void setLeagueManagers(Long leagueManagers) {
		this.leagueManagers = leagueManagers;
	}

	@Column(name = "rookie_draft_rds", nullable = false)
	public Long getRookieDraftRds() {
		return rookieDraftRds;
	}

	public void setRookieDraftRds(Long rookieDraftRds) {
		this.rookieDraftRds = rookieDraftRds;
	}

	@Column(name = "rookie_draft_strategy", nullable = false)
	public String getRookieDraftStrategy() {
		return rookieDraftStrategy;
	}

	public void setRookieDraftStrategy(String rookieDraftStrategy) {
		this.rookieDraftStrategy = rookieDraftStrategy;
	}

	@Column(name = "rookie_wage_scale", nullable = false)
	public String getRookieWageScale() {
		return rookieWageScale;
	}

	public void setRookieWageScale(String rookieWageScale) {
		this.rookieWageScale = rookieWageScale;
	}

	@Column(name = "team_salary_cap", nullable = false)
	public Long getTeamSalaryCap() {
		return teamSalaryCap;
	}

	public void setTeamSalaryCap(Long teamSalaryCap) {
		this.teamSalaryCap = teamSalaryCap;
	}

	@Column(name = "league_salary_cap", nullable = false)
	public Long getLeagueSalaryCap() {
		return leagueSalaryCap;
	}

	public void setLeagueSalaryCap(Long leagueSalaryCap) {
		this.leagueSalaryCap = leagueSalaryCap;
	}

	@Column(name = "salary_cap_exemption_limit", nullable = false)
	public Long getSalaryCapExemptionLimit() {
		return salaryCapExemptionLimit;
	}

	public void setSalaryCapExemptionLimit(Long salaryCapExemptionLimit) {
		this.salaryCapExemptionLimit = salaryCapExemptionLimit;
	}

	@Column(name = "ir_relief_perc", nullable = false)
	public Long getIrReliefPerc() {
		return irReliefPerc;
	}

	public void setIrReliefPerc(Long irReliefPerc) {
		this.irReliefPerc = irReliefPerc;
	}

	@Column(name = "franchise_tag_relief_perc", nullable = false)
	public Long getFranchiseTagReliefPerc() {
		return franchiseTagReliefPerc;
	}

	public void setFranchiseTagReliefPerc(Long franchiseTagReliefPerc) {
		this.franchiseTagReliefPerc = franchiseTagReliefPerc;
	}

	@Column(name = "franchise_tag_spots", nullable = false)
	public Long getFranchiseTagSpots() {
		return franchiseTagSpots;
	}

	public void setFranchiseTagSpots(Long franchiseTagSpots) {
		this.franchiseTagSpots = franchiseTagSpots;
	}

	@Column(name = "trading_draft_pick_years", nullable = false)
	public Long getTradingDraftPickYears() {
		return tradingDraftPickYears;
	}

	public void setTradingDraftPickYears(Long tradingDraftPickYears) {
		this.tradingDraftPickYears = tradingDraftPickYears;
	}

	@Column(name = "qb_min", nullable = false)
	public Long getQbMin() {
		return qbMin;
	}

	public void setQbMin(Long qbMin) {
		this.qbMin = qbMin;
	}

	@Column(name = "qb_max", nullable = false)
	public Long getQbMax() {
		return qbMax;
	}

	public void setQbMax(Long qbMax) {
		this.qbMax = qbMax;
	}

	@Column(name = "rb_min", nullable = false)
	public Long getRbMin() {
		return rbMin;
	}

	public void setRbMin(Long rbMin) {
		this.rbMin = rbMin;
	}

	@Column(name = "rb_max", nullable = false)
	public Long getRbMax() {
		return rbMax;
	}

	public void setRbMax(Long rbMax) {
		this.rbMax = rbMax;
	}

	@Column(name = "wr_min", nullable = false)
	public Long getWrMin() {
		return wrMin;
	}

	public void setWrMin(Long wrMin) {
		this.wrMin = wrMin;
	}

	@Column(name = "wr_max", nullable = false)
	public Long getWrMax() {
		return wrMax;
	}

	public void setWrMax(Long wrMax) {
		this.wrMax = wrMax;
	}

	@Column(name = "te_min", nullable = false)
	public Long getTeMin() {
		return teMin;
	}

	public void setTeMin(Long teMin) {
		this.teMin = teMin;
	}

	@Column(name = "te_max", nullable = false)
	public Long getTeMax() {
		return teMax;
	}

	public void setTeMax(Long teMax) {
		this.teMax = teMax;
	}

	@Column(name = "k_min", nullable = false)
	public Long getkMin() {
		return kMin;
	}

	public void setkMin(Long kMin) {
		this.kMin = kMin;
	}

	@Column(name = "k_max", nullable = false)
	public Long getkMax() {
		return kMax;
	}

	public void setkMax(Long kMax) {
		this.kMax = kMax;
	}

	@Column(name = "def_min", nullable = false)
	public Long getDefMin() {
		return defMin;
	}

	public void setDefMin(Long defMin) {
		this.defMin = defMin;
	}

	@Column(name = "def_max", nullable = false)
	public Long getDefMax() {
		return defMax;
	}

	public void setDefMax(Long defMax) {
		this.defMax = defMax;
	}

	@Column(name = "default_ind", nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Column(name = "owner_guid", nullable = false)
	public String getOwnerGuid() {
		return ownerGuid;
	}

	public void setOwnerGuid(String ownerGuid) {
		this.ownerGuid = ownerGuid;
	}

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scadLeague")    
//	public Set<SCADLeagueTeam> getScadLeagueTeams() {
//		return scadLeagueTeams;
//	}
//
//	public void setScadLeagueTeams(Set<SCADLeagueTeam> scadLeagueTeams) {
//		this.scadLeagueTeams = scadLeagueTeams;
//	}
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scadLeague")   
//	public Set<SCADLeaguePlayer> getScadLeaguePlayers() {
//		return scadLeaguePlayers;
//	}
//
//	public void setScadLeaguePlayers(Set<SCADLeaguePlayer> scadLeaguePlayers) {
//		this.scadLeaguePlayers = scadLeaguePlayers;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((defMax == null) ? 0 : defMax.hashCode());
		result = prime * result + ((defMin == null) ? 0 : defMin.hashCode());
		result = prime * result + ((franchiseTagReliefPerc == null) ? 0 : franchiseTagReliefPerc.hashCode());
		result = prime * result + ((franchiseTagSpots == null) ? 0 : franchiseTagSpots.hashCode());
		result = prime * result + ((yahooGameId == null) ? 0 : yahooGameId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((irReliefPerc == null) ? 0 : irReliefPerc.hashCode());
		result = prime * result + ((isDefault == null) ? 0 : isDefault.hashCode());
		result = prime * result + ((kMax == null) ? 0 : kMax.hashCode());
		result = prime * result + ((kMin == null) ? 0 : kMin.hashCode());
		result = prime * result + ((yahooLeagueId == null) ? 0 : yahooLeagueId.hashCode());
		result = prime * result + ((leagueManagers == null) ? 0 : leagueManagers.hashCode());
		result = prime * result + ((leagueSalaryCap == null) ? 0 : leagueSalaryCap.hashCode());
		result = prime * result + ((ownerGuid == null) ? 0 : ownerGuid.hashCode());
		result = prime * result + ((qbMax == null) ? 0 : qbMax.hashCode());
		result = prime * result + ((qbMin == null) ? 0 : qbMin.hashCode());
		result = prime * result + ((rbMax == null) ? 0 : rbMax.hashCode());
		result = prime * result + ((rbMin == null) ? 0 : rbMin.hashCode());
		result = prime * result + ((rookieDraftRds == null) ? 0 : rookieDraftRds.hashCode());
		result = prime * result + ((rookieDraftStrategy == null) ? 0 : rookieDraftStrategy.hashCode());
		result = prime * result + ((rookieWageScale == null) ? 0 : rookieWageScale.hashCode());
		result = prime * result + ((salaryCapExemptionLimit == null) ? 0 : salaryCapExemptionLimit.hashCode());
		result = prime * result + ((seasonYear == null) ? 0 : seasonYear.hashCode());
		result = prime * result + ((teMax == null) ? 0 : teMax.hashCode());
		result = prime * result + ((teMin == null) ? 0 : teMin.hashCode());
		result = prime * result + ((teamSalaryCap == null) ? 0 : teamSalaryCap.hashCode());
		result = prime * result + ((tradingDraftPickYears == null) ? 0 : tradingDraftPickYears.hashCode());
		result = prime * result + ((wrMax == null) ? 0 : wrMax.hashCode());
		result = prime * result + ((wrMin == null) ? 0 : wrMin.hashCode());
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
		SCADLeague other = (SCADLeague) obj;
		if (defMax == null) {
			if (other.defMax != null)
				return false;
		} else if (!defMax.equals(other.defMax))
			return false;
		if (defMin == null) {
			if (other.defMin != null)
				return false;
		} else if (!defMin.equals(other.defMin))
			return false;
		if (franchiseTagReliefPerc == null) {
			if (other.franchiseTagReliefPerc != null)
				return false;
		} else if (!franchiseTagReliefPerc.equals(other.franchiseTagReliefPerc))
			return false;
		if (franchiseTagSpots == null) {
			if (other.franchiseTagSpots != null)
				return false;
		} else if (!franchiseTagSpots.equals(other.franchiseTagSpots))
			return false;
		if (yahooGameId == null) {
			if (other.yahooGameId != null)
				return false;
		} else if (!yahooGameId.equals(other.yahooGameId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (irReliefPerc == null) {
			if (other.irReliefPerc != null)
				return false;
		} else if (!irReliefPerc.equals(other.irReliefPerc))
			return false;
		if (isDefault == null) {
			if (other.isDefault != null)
				return false;
		} else if (!isDefault.equals(other.isDefault))
			return false;
		if (kMax == null) {
			if (other.kMax != null)
				return false;
		} else if (!kMax.equals(other.kMax))
			return false;
		if (kMin == null) {
			if (other.kMin != null)
				return false;
		} else if (!kMin.equals(other.kMin))
			return false;
		if (yahooLeagueId == null) {
			if (other.yahooLeagueId != null)
				return false;
		} else if (!yahooLeagueId.equals(other.yahooLeagueId))
			return false;
		if (leagueManagers == null) {
			if (other.leagueManagers != null)
				return false;
		} else if (!leagueManagers.equals(other.leagueManagers))
			return false;
		if (leagueSalaryCap == null) {
			if (other.leagueSalaryCap != null)
				return false;
		} else if (!leagueSalaryCap.equals(other.leagueSalaryCap))
			return false;
		if (ownerGuid == null) {
			if (other.ownerGuid != null)
				return false;
		} else if (!ownerGuid.equals(other.ownerGuid))
			return false;
		if (qbMax == null) {
			if (other.qbMax != null)
				return false;
		} else if (!qbMax.equals(other.qbMax))
			return false;
		if (qbMin == null) {
			if (other.qbMin != null)
				return false;
		} else if (!qbMin.equals(other.qbMin))
			return false;
		if (rbMax == null) {
			if (other.rbMax != null)
				return false;
		} else if (!rbMax.equals(other.rbMax))
			return false;
		if (rbMin == null) {
			if (other.rbMin != null)
				return false;
		} else if (!rbMin.equals(other.rbMin))
			return false;
		if (rookieDraftRds == null) {
			if (other.rookieDraftRds != null)
				return false;
		} else if (!rookieDraftRds.equals(other.rookieDraftRds))
			return false;
		if (rookieDraftStrategy == null) {
			if (other.rookieDraftStrategy != null)
				return false;
		} else if (!rookieDraftStrategy.equals(other.rookieDraftStrategy))
			return false;
		if (rookieWageScale == null) {
			if (other.rookieWageScale != null)
				return false;
		} else if (!rookieWageScale.equals(other.rookieWageScale))
			return false;
		if (salaryCapExemptionLimit == null) {
			if (other.salaryCapExemptionLimit != null)
				return false;
		} else if (!salaryCapExemptionLimit.equals(other.salaryCapExemptionLimit))
			return false;
		if (seasonYear == null) {
			if (other.seasonYear != null)
				return false;
		} else if (!seasonYear.equals(other.seasonYear))
			return false;
		if (teMax == null) {
			if (other.teMax != null)
				return false;
		} else if (!teMax.equals(other.teMax))
			return false;
		if (teMin == null) {
			if (other.teMin != null)
				return false;
		} else if (!teMin.equals(other.teMin))
			return false;
		if (teamSalaryCap == null) {
			if (other.teamSalaryCap != null)
				return false;
		} else if (!teamSalaryCap.equals(other.teamSalaryCap))
			return false;
		if (tradingDraftPickYears == null) {
			if (other.tradingDraftPickYears != null)
				return false;
		} else if (!tradingDraftPickYears.equals(other.tradingDraftPickYears))
			return false;
		if (wrMax == null) {
			if (other.wrMax != null)
				return false;
		} else if (!wrMax.equals(other.wrMax))
			return false;
		if (wrMin == null) {
			if (other.wrMin != null)
				return false;
		} else if (!wrMin.equals(other.wrMin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SCADLeague [id=" + id + ", yahooGameId=" + yahooGameId + ", yahooLeagueId=" + yahooLeagueId + ", seasonYear=" + seasonYear
				+ ", leagueManagers=" + leagueManagers + ", rookieDraftRds=" + rookieDraftRds + ", rookieDraftStrategy="
				+ rookieDraftStrategy + ", rookieWageScale=" + rookieWageScale + ", teamSalaryCap=" + teamSalaryCap
				+ ", leagueSalaryCap=" + leagueSalaryCap + ", salaryCapExemptionLimit=" + salaryCapExemptionLimit
				+ ", irReliefPerc=" + irReliefPerc + ", franchiseTagReliefPerc=" + franchiseTagReliefPerc
				+ ", franchiseTagSpots=" + franchiseTagSpots + ", tradingDraftPickYears=" + tradingDraftPickYears
				+ ", qbMin=" + qbMin + ", qbMax=" + qbMax + ", rbMin=" + rbMin + ", rbMax=" + rbMax + ", wrMin=" + wrMin
				+ ", wrMax=" + wrMax + ", teMin=" + teMin + ", teMax=" + teMax + ", kMin=" + kMin + ", kMax=" + kMax
				+ ", defMin=" + defMin + ", defMax=" + defMax + ", isDefault=" + isDefault + ", ownerGuid=" + ownerGuid
				+ "]";
	}

}
