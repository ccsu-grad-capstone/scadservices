package edu.ccsu.cs595.capstone.scadservices.dto;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SCADLeagueDto extends AuditedDto {
	
	private Long id;
	private Long yahooGameId;
	private Long yahooLeagueId;
	private String yahooLeagueName;
	private Long seasonYear;
	private Long leagueManagers;
	private Long rookieDraftRds;
	private String rookieDraftStrategy;
	private String rookieWageScale;
	private Long teamSalaryCap;
	private Long leagueSalaryCap;
	private Long salaryCapExemptionLimit;
	private Long irReliefPerc;
	private Long franchiseTagDiscount;
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
	private Long renewSCADLeagueId;
	private List<SCADLeagueTeamDto> scadLeagueTeams;
	private List<SCADLeaguePlayerDto> scadLeaguePlayers;
	private Boolean isCurrentlyLoggedInUserACommissioner = false;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getYahooGameId() {
		return yahooGameId;
	}

	public void setYahooGameId(Long yahooGameId) {
		this.yahooGameId = yahooGameId;
	}

	public Long getYahooLeagueId() {
		return yahooLeagueId;
	}

	public void setYahooLeagueId(Long yahooLeagueId) {
		this.yahooLeagueId = yahooLeagueId;
	}
	
	public Long getSeasonYear() {
		return seasonYear;
	}

	public void setSeasonYear(Long seasonYear) {
		this.seasonYear = seasonYear;
	}

	public String getYahooLeagueName() {
		return yahooLeagueName;
	}

	public void setYahooLeagueName(String yahooLeagueName) {
		this.yahooLeagueName = yahooLeagueName;
	}

	public Long getLeagueManagers() {
		return leagueManagers;
	}

	public void setLeagueManagers(Long leagueManagers) {
		this.leagueManagers = leagueManagers;
	}

	public Long getRookieDraftRds() {
		return rookieDraftRds;
	}

	public void setRookieDraftRds(Long rookieDraftRds) {
		this.rookieDraftRds = rookieDraftRds;
	}

	public String getRookieDraftStrategy() {
		return rookieDraftStrategy;
	}

	public void setRookieDraftStrategy(String rookieDraftStrategy) {
		this.rookieDraftStrategy = rookieDraftStrategy;
	}

	public String getRookieWageScale() {
		return rookieWageScale;
	}

	public void setRookieWageScale(String rookieWageScale) {
		this.rookieWageScale = rookieWageScale;
	}

	public Long getTeamSalaryCap() {
		return teamSalaryCap;
	}

	public void setTeamSalaryCap(Long teamSalaryCap) {
		this.teamSalaryCap = teamSalaryCap;
	}

	public Long getLeagueSalaryCap() {
		return leagueSalaryCap;
	}

	public void setLeagueSalaryCap(Long leagueSalaryCap) {
		this.leagueSalaryCap = leagueSalaryCap;
	}

	public Long getSalaryCapExemptionLimit() {
		return salaryCapExemptionLimit;
	}

	public void setSalaryCapExemptionLimit(Long salaryCapExemptionLimit) {
		this.salaryCapExemptionLimit = salaryCapExemptionLimit;
	}

	public Long getIrReliefPerc() {
		return irReliefPerc;
	}

	public void setIrReliefPerc(Long irReliefPerc) {
		this.irReliefPerc = irReliefPerc;
	}

	public Long getFranchiseTagDiscount() {
		return franchiseTagDiscount;
	}

	public void setFranchiseTagDiscount(Long franchiseTagDiscount) {
		this.franchiseTagDiscount = franchiseTagDiscount;
	}

	public Long getFranchiseTagSpots() {
		return franchiseTagSpots;
	}

	public void setFranchiseTagSpots(Long franchiseTagSpots) {
		this.franchiseTagSpots = franchiseTagSpots;
	}

	public Long getTradingDraftPickYears() {
		return tradingDraftPickYears;
	}

	public void setTradingDraftPickYears(Long tradingDraftPickYears) {
		this.tradingDraftPickYears = tradingDraftPickYears;
	}

	public Long getQbMin() {
		return qbMin;
	}

	public void setQbMin(Long qbMin) {
		this.qbMin = qbMin;
	}

	public Long getQbMax() {
		return qbMax;
	}

	public void setQbMax(Long qbMax) {
		this.qbMax = qbMax;
	}

	public Long getRbMin() {
		return rbMin;
	}

	public void setRbMin(Long rbMin) {
		this.rbMin = rbMin;
	}

	public Long getRbMax() {
		return rbMax;
	}

	public void setRbMax(Long rbMax) {
		this.rbMax = rbMax;
	}

	public Long getWrMin() {
		return wrMin;
	}

	public void setWrMin(Long wrMin) {
		this.wrMin = wrMin;
	}

	public Long getWrMax() {
		return wrMax;
	}

	public void setWrMax(Long wrMax) {
		this.wrMax = wrMax;
	}

	public Long getTeMin() {
		return teMin;
	}

	public void setTeMin(Long teMin) {
		this.teMin = teMin;
	}

	public Long getTeMax() {
		return teMax;
	}

	public void setTeMax(Long teMax) {
		this.teMax = teMax;
	}

	public Long getkMin() {
		return kMin;
	}

	public void setkMin(Long kMin) {
		this.kMin = kMin;
	}

	public Long getkMax() {
		return kMax;
	}

	public void setkMax(Long kMax) {
		this.kMax = kMax;
	}

	public Long getDefMin() {
		return defMin;
	}

	public void setDefMin(Long defMin) {
		this.defMin = defMin;
	}

	public Long getDefMax() {
		return defMax;
	}

	public void setDefMax(Long defMax) {
		this.defMax = defMax;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getOwnerGuid() {
		return ownerGuid;
	}

	public void setOwnerGuid(String ownerGuid) {
		this.ownerGuid = ownerGuid;
	}

	public Long getRenewSCADLeagueId() {
		return renewSCADLeagueId;
	}

	public void setRenewSCADLeagueId(Long renewSCADLeagueId) {
		this.renewSCADLeagueId = renewSCADLeagueId;
	}

	public List<SCADLeagueTeamDto> getScadLeagueTeams() {
		return scadLeagueTeams;
	}

	public void setScadLeagueTeams(List<SCADLeagueTeamDto> scadLeagueTeamsDto) {
		this.scadLeagueTeams = scadLeagueTeamsDto;
	}

	public List<SCADLeaguePlayerDto> getScadLeaguePlayers() {
		return scadLeaguePlayers;
	}

	public void setScadLeaguePlayers(List<SCADLeaguePlayerDto> scadLeaguePlayersDto) {
		this.scadLeaguePlayers = scadLeaguePlayersDto;
	}

	public Boolean getIsCurrentlyLoggedInUserACommissioner() {
		return isCurrentlyLoggedInUserACommissioner;
	}

	public void setIsCurrentlyLoggedInUserACommissioner(Boolean userACommissioner) {
		isCurrentlyLoggedInUserACommissioner = userACommissioner;
	}

	@Override
	public String toString() {
		return "SCADLeagueDto [id=" + id + ", yahooGameId=" + yahooGameId + ", yahooLeagueId=" + yahooLeagueId
				+ ", yahooLeagueName=" + yahooLeagueName + ", seasonYear=" + seasonYear + ", leagueManagers="
				+ leagueManagers + ", rookieDraftRds=" + rookieDraftRds + ", rookieDraftStrategy=" + rookieDraftStrategy
				+ ", rookieWageScale=" + rookieWageScale + ", teamSalaryCap=" + teamSalaryCap + ", leagueSalaryCap="
				+ leagueSalaryCap + ", salaryCapExemptionLimit=" + salaryCapExemptionLimit + ", irReliefPerc="
				+ irReliefPerc + ", franchiseTagDiscount=" + franchiseTagDiscount + ", franchiseTagSpots="
				+ franchiseTagSpots + ", tradingDraftPickYears=" + tradingDraftPickYears + ", qbMin=" + qbMin
				+ ", qbMax=" + qbMax + ", rbMin=" + rbMin + ", rbMax=" + rbMax + ", wrMin=" + wrMin + ", wrMax=" + wrMax
				+ ", teMin=" + teMin + ", teMax=" + teMax + ", kMin=" + kMin + ", kMax=" + kMax + ", defMin=" + defMin
				+ ", defMax=" + defMax + ", isDefault=" + isDefault + ", ownerGuid=" + ownerGuid
				+ ", renewSCADLeagueId=" + renewSCADLeagueId + ", scadLeagueTeams=" + scadLeagueTeams
				+ ", scadLeaguePlayers=" + scadLeaguePlayers + ", isCurrentlyLoggedInUserACommissioner="
				+ isCurrentlyLoggedInUserACommissioner + "]";
	}
	
}
