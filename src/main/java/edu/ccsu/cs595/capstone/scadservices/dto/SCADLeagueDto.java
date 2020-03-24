package edu.ccsu.cs595.capstone.scadservices.dto;

import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
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
	private List<SCADLeagueTeamDto> scadLeagueTeamsDto;
	private List<SCADLeaguePlayerDto> scadLeaguePlayersDto;

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

	public Long getFranchiseTagReliefPerc() {
		return franchiseTagReliefPerc;
	}

	public void setFranchiseTagReliefPerc(Long franchiseTagReliefPerc) {
		this.franchiseTagReliefPerc = franchiseTagReliefPerc;
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
	
	public List<SCADLeagueTeamDto> getScadLeagueTeamsDto() {
		return scadLeagueTeamsDto;
	}

	public void setScadLeagueTeamsDto(List<SCADLeagueTeamDto> scadLeagueTeamsDto) {
		this.scadLeagueTeamsDto = scadLeagueTeamsDto;
	}

	public List<SCADLeaguePlayerDto> getScadLeaguePlayersDto() {
		return scadLeaguePlayersDto;
	}

	public void setScadLeaguePlayersDto(List<SCADLeaguePlayerDto> scadLeaguePlayersDto) {
		this.scadLeaguePlayersDto = scadLeaguePlayersDto;
	}

	@Override
	public String toString() {
		return "SCADLeagueDto [yahooGameId=" + yahooGameId + ", yahooLeagueId=" + yahooLeagueId + ", leagueName=" + yahooLeagueName
				+ ", seasonYear=" + seasonYear + ", leagueManagers=" + leagueManagers + ", rookieDraftRds="
				+ rookieDraftRds + ", rookieDraftStrategy=" + rookieDraftStrategy + ", rookieWageScale="
				+ rookieWageScale + ", teamSalaryCap=" + teamSalaryCap + ", leagueSalaryCap=" + leagueSalaryCap
				+ ", salaryCapExemptionLimit=" + salaryCapExemptionLimit + ", irReliefPerc=" + irReliefPerc
				+ ", franchiseTagReliefPerc=" + franchiseTagReliefPerc + ", franchiseTagSpots=" + franchiseTagSpots
				+ ", tradingDraftPickYears=" + tradingDraftPickYears + ", qbMin=" + qbMin + ", qbMax=" + qbMax
				+ ", rbMin=" + rbMin + ", rbMax=" + rbMax + ", wrMin=" + wrMin + ", wrMax=" + wrMax + ", teMin=" + teMin
				+ ", teMax=" + teMax + ", kMin=" + kMin + ", kMax=" + kMax + ", defMin=" + defMin + ", defMax=" + defMax
				+ ", isDefault=" + isDefault + ", ownerGuid=" + ownerGuid + "]";
	}
	
}
