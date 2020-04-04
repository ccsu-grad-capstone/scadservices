package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.api.LeagueApi;
import edu.ccsu.cs595.capstone.scadservices.dao.SCADLeagueDao;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueListDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.entity.AuditContext;
import edu.ccsu.cs595.capstone.scadservices.entity.SCADLeague;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.util.YahooClientBuilder;

@Stateless
public class SCADLeagueService {

	private static final Logger LOG = LoggerFactory.getLogger(SCADLeagueService.class);

	@Inject
	SCADLeagueDao slDao;

	@Inject
	YahooClientBuilder yahoo;
	
	@Inject
	SCADLeagueTeamService sltSvc;
	
	@Inject
	SCADLeaguePlayerService slpSvc;
	
	@Inject
	LeagueApi lApi;

	public SCADLeagueDto getSCADLeague(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueDto result = null;
		SCADLeague slEntity = slDao.find(id);
		result = this.entityToDto(slEntity);
		return result;

	}

	public SCADLeagueDto getDefaultSCADLeagueByYahooGame() throws AuthorizationFailedException, RuntimeException {

		SCADLeagueDto result = null;
		Long yahooGameId = yahoo.getYahooGame();
		String userGuid = yahoo.getYahooUserGuid();
		SCADLeague slEntity = slDao.getDefaultSCADLeagueByYahooGame(yahooGameId, userGuid);
		result = this.entityToDto(slEntity);
		return result;

	}

	public SCADLeagueDto getSCADLeagueByYahooGameAndLeague(Long yahooLeagueId) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueDto result = null;
		Long yahooGameId = yahoo.getYahooGame();
		SCADLeague slEntity = slDao.getSCADLeagueByYahooGameAndLeague(yahooGameId, yahooLeagueId);
		result = this.entityToDto(slEntity);
		return result;

	}

	public SCADLeagueListDto getAllSCADLeaguesByYahooGame() throws AuthorizationFailedException, RuntimeException {

		SCADLeagueListDto list = new SCADLeagueListDto();
		Long yahooGameId = yahoo.getYahooGame();
		String userGuid = yahoo.getYahooUserGuid();
		List<SCADLeague> slEntityList = slDao.getAllSCADLeaguesByYahooGame(yahooGameId, userGuid);
		for (SCADLeague slEntity : slEntityList) {
			SCADLeagueDto result = this.entityToDto(slEntity);
			list.getScadLeagues().add(result);
		}
		return list;

	}

	public SCADLeagueDto createSCADLeague(SCADLeagueDto slDto) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueDto result = null;
		addYahooLeagueData(slDto);
		SCADLeague newEntity = this.dtoToEntity(slDto);
		newEntity = slDao.upsert(newEntity);
		result = this.entityToDto(newEntity);
		result.setYahooLeagueName(slDto.getYahooLeagueName());
		List<SCADLeagueTeamDto> ltList = createLeagueTeams(result);
		List<SCADLeaguePlayerDto> lpList = createLeaguePlayers(result);
		result.setScadLeagueTeamsDto(ltList);
		result.setScadLeaguePlayersDto(lpList);
		return result;

	}
	
	private void addYahooLeagueData(SCADLeagueDto slDto) throws AuthorizationFailedException, RuntimeException {
		
		slDto.setYahooGameId(yahoo.getYahooGame());
		slDto.setSeasonYear(yahoo.getSeasonYear());
		slDto.setOwnerGuid(yahoo.getYahooUserGuid());
		SCADLeagueDto dl = this.getDefaultSCADLeagueByYahooGame();
		if (Objects.isNull(dl)) {
			slDto.setIsDefault(true);
		} else {
			slDto.setIsDefault(false);
		}
		AuditContext ac = new AuditContext(yahoo.getYahooUserGuid(),yahoo.getYahooUserName());
		AuditContext.setAuditContext(ac);
		
	}
	
	private List<SCADLeagueTeamDto> createLeagueTeams (SCADLeagueDto leagueDto) throws AuthorizationFailedException, RuntimeException {
		
		List<SCADLeagueTeamDto> result = new ArrayList<SCADLeagueTeamDto>();
		Response lts = lApi.getUserLeagueTeams(leagueDto.getYahooLeagueId());
		String ltsString = lts.readEntity(String.class);
		JsonObject ltsObject = new JsonParser().parse(ltsString).getAsJsonObject();
		JsonArray ltsArray = ltsObject.get("teams").getAsJsonArray();
		for (int i = 0; i < ltsArray.size(); i++) {
			SCADLeagueTeamDto newLTDto = new SCADLeagueTeamDto();
			JsonObject teamObject = ltsArray.get(i).getAsJsonObject();
			Long teamId = teamObject.get("team_id").getAsLong();
			newLTDto.setYahooLeagueId(leagueDto.getYahooLeagueId());
			newLTDto.setScadLeagueId(leagueDto.getId());
			newLTDto.setYahooLeagueTeamId(teamId);
			newLTDto.setSalary(0L);
			newLTDto.setIsFranchiseTag(false);
			newLTDto.setExceptionIn(0L);
			newLTDto.setExceptionOut(0L);
			SCADLeagueTeamDto scadLTDto = sltSvc.createSCADLeagueTeam(newLTDto);
			result.add(scadLTDto);
		}
		return result;
		
	}
	
	private List<SCADLeaguePlayerDto> createLeaguePlayers (SCADLeagueDto leagueDto) throws AuthorizationFailedException, RuntimeException {

		List<SCADLeaguePlayerDto> result = new ArrayList<SCADLeaguePlayerDto>();
		Response lps = lApi.getUserLeaguePlayers(leagueDto.getYahooLeagueId());
		String lpsString = lps.readEntity(String.class);
		JsonObject lpsObject = new JsonParser().parse(lpsString).getAsJsonObject();
		JsonArray lpsArray = lpsObject.get("players").getAsJsonArray();
		for (int i = 0; i < lpsArray.size(); i++) {
			SCADLeaguePlayerDto newLPDto = new SCADLeaguePlayerDto();
			JsonObject playerObject = lpsArray.get(i).getAsJsonObject();
			Long playerId = playerObject.get("player_id").getAsLong();
			newLPDto.setYahooLeagueId(leagueDto.getYahooLeagueId());
			newLPDto.setScadLeagueId(leagueDto.getId());
			newLPDto.setYahooLeaguePlayerId(playerId);
			newLPDto.setSalary(0L);
			newLPDto.setIsFranchiseTag(false);
			SCADLeaguePlayerDto scadLTDto = slpSvc.createSCADLeaguePlayer(newLPDto);
			result.add(scadLTDto);
		}
			
		return result;
		
	}

	public SCADLeagueDto updateSCADLeague(Long id, SCADLeagueDto slDto)
			throws AuthorizationFailedException, RuntimeException {

		SCADLeagueDto result = null;
		SCADLeague existingEntity = slDao.find(id);
		this.dtoToEntityForUpdate(slDto, existingEntity);
		AuditContext ac = new AuditContext(yahoo.getYahooUserGuid(),yahoo.getYahooUserName());
		AuditContext.setAuditContext(ac);
		existingEntity = slDao.upsert(existingEntity);
		result = this.entityToDto(existingEntity);
		return result;

	}

	public void deleteSCADLeague(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeague deleteEntity = slDao.find(id);
		AuditContext ac = new AuditContext(yahoo.getYahooUserGuid(),yahoo.getYahooUserName());
		AuditContext.setAuditContext(ac);
		slDao.delete(deleteEntity);

	}

	private SCADLeagueDto entityToDto(SCADLeague slEntity) {

		if (Objects.nonNull(slEntity)) {
			
			SCADLeagueDto result = new SCADLeagueDto();

			result.setId(slEntity.getId());
			result.setYahooGameId(slEntity.getYahooGameId());
			result.setYahooLeagueId(slEntity.getYahooLeagueId());
			result.setSeasonYear(slEntity.getSeasonYear());
			result.setLeagueManagers(slEntity.getLeagueManagers());
			result.setRookieDraftRds(slEntity.getRookieDraftRds());
			result.setRookieDraftStrategy(slEntity.getRookieDraftStrategy());
			result.setRookieWageScale(slEntity.getRookieWageScale());
			result.setTeamSalaryCap(slEntity.getTeamSalaryCap());
			result.setLeagueSalaryCap(slEntity.getLeagueSalaryCap());
			result.setSalaryCapExemptionLimit(slEntity.getSalaryCapExemptionLimit());
			result.setIrReliefPerc(slEntity.getIrReliefPerc());
			result.setFranchiseTagReliefPerc(slEntity.getFranchiseTagReliefPerc());
			result.setFranchiseTagSpots(slEntity.getFranchiseTagSpots());
			result.setTradingDraftPickYears(slEntity.getTradingDraftPickYears());
			result.setQbMin(slEntity.getQbMin());
			result.setQbMax(slEntity.getQbMax());
			result.setRbMin(slEntity.getRbMin());
			result.setRbMax(slEntity.getRbMax());
			result.setWrMin(slEntity.getWrMin());
			result.setWrMax(slEntity.getWrMax());
			result.setTeMin(slEntity.getTeMin());
			result.setTeMax(slEntity.getTeMax());
			result.setkMin(slEntity.getkMin());
			result.setkMax(slEntity.getkMax());
			result.setDefMin(slEntity.getDefMin());
			result.setDefMax(slEntity.getDefMax());
			result.setIsDefault(slEntity.getIsDefault());
			result.setOwnerGuid(slEntity.getOwnerGuid());
			result.setCreatedBy(slEntity.getCreatedBy());
			result.setCreatedAt(slEntity.getCreatedAt());
			result.setModifiedBy(slEntity.getModifiedBy());
			result.setModifiedAt(slEntity.getModifiedAt());
			
			return result;

		}

		return null;

	}

	private SCADLeague dtoToEntity(SCADLeagueDto slDto) {

		if (Objects.nonNull(slDto)) {
			
			SCADLeague result = new SCADLeague();

			result.setYahooGameId(slDto.getYahooGameId());
			result.setYahooLeagueId(slDto.getYahooLeagueId());
			result.setSeasonYear(slDto.getSeasonYear());
			result.setLeagueManagers(slDto.getLeagueManagers());
			result.setRookieDraftRds(slDto.getRookieDraftRds());
			result.setRookieDraftStrategy(slDto.getRookieDraftStrategy());
			result.setRookieWageScale(slDto.getRookieWageScale());
			result.setTeamSalaryCap(slDto.getTeamSalaryCap());
			result.setLeagueSalaryCap(slDto.getLeagueSalaryCap());
			result.setSalaryCapExemptionLimit(slDto.getSalaryCapExemptionLimit());
			result.setIrReliefPerc(slDto.getIrReliefPerc());
			result.setFranchiseTagReliefPerc(slDto.getFranchiseTagReliefPerc());
			result.setFranchiseTagSpots(slDto.getFranchiseTagSpots());
			result.setTradingDraftPickYears(slDto.getTradingDraftPickYears());
			result.setQbMin(slDto.getQbMin());
			result.setQbMax(slDto.getQbMax());
			result.setRbMin(slDto.getRbMin());
			result.setRbMax(slDto.getRbMax());
			result.setWrMin(slDto.getWrMin());
			result.setWrMax(slDto.getWrMax());
			result.setTeMin(slDto.getTeMin());
			result.setTeMax(slDto.getTeMax());
			result.setkMin(slDto.getkMin());
			result.setkMax(slDto.getkMax());
			result.setDefMin(slDto.getDefMin());
			result.setDefMax(slDto.getDefMax());
			result.setIsDefault(slDto.getIsDefault());
			result.setOwnerGuid(slDto.getOwnerGuid());
			
			return result;

		}

		return null;

	}

	private void dtoToEntityForUpdate(SCADLeagueDto slDto, SCADLeague existingEntity) {

		if (Objects.nonNull(slDto)) {

			if (Objects.nonNull(slDto.getRookieDraftRds())) {
				existingEntity.setRookieDraftRds(slDto.getRookieDraftRds());
			}
			if (Objects.nonNull(slDto.getRookieDraftStrategy())) {
				existingEntity.setRookieDraftStrategy(slDto.getRookieDraftStrategy());
			}
			if (Objects.nonNull(slDto.getRookieWageScale())) {
				existingEntity.setRookieWageScale(slDto.getRookieWageScale());
			}
			if (Objects.nonNull(slDto.getTeamSalaryCap())) {
				existingEntity.setTeamSalaryCap(slDto.getTeamSalaryCap());
			}
			if (Objects.nonNull(slDto.getLeagueSalaryCap())) {
				existingEntity.setLeagueSalaryCap(slDto.getLeagueSalaryCap());
			}
			if (Objects.nonNull(slDto.getSalaryCapExemptionLimit())) {
				existingEntity.setSalaryCapExemptionLimit(slDto.getSalaryCapExemptionLimit());
			}
			if (Objects.nonNull(slDto.getIrReliefPerc())) {
				existingEntity.setIrReliefPerc(slDto.getIrReliefPerc());
			}
			if (Objects.nonNull(slDto.getFranchiseTagReliefPerc())) {
				existingEntity.setFranchiseTagReliefPerc(slDto.getFranchiseTagReliefPerc());
			}
			if (Objects.nonNull(slDto.getFranchiseTagSpots())) {
				existingEntity.setFranchiseTagSpots(slDto.getFranchiseTagSpots());
			}
			if (Objects.nonNull(slDto.getTradingDraftPickYears())) {
				existingEntity.setTradingDraftPickYears(slDto.getTradingDraftPickYears());
			}
			if (Objects.nonNull(slDto.getQbMin())) {
				existingEntity.setQbMin(slDto.getQbMin());
			}
			if (Objects.nonNull(slDto.getQbMax())) {
				existingEntity.setQbMax(slDto.getQbMax());
			}
			if (Objects.nonNull(slDto.getRbMin())) {
				existingEntity.setRbMin(slDto.getRbMin());
			}
			if (Objects.nonNull(slDto.getRbMax())) {
				existingEntity.setRbMax(slDto.getRbMax());
			}
			if (Objects.nonNull(slDto.getWrMin())) {
				existingEntity.setWrMin(slDto.getWrMin());
			}
			if (Objects.nonNull(slDto.getWrMax())) {
				existingEntity.setWrMax(slDto.getWrMax());
			}
			if (Objects.nonNull(slDto.getTeMin())) {
				existingEntity.setTeMin(slDto.getTeMin());
			}
			if (Objects.nonNull(slDto.getTeMax())) {
				existingEntity.setTeMax(slDto.getTeMax());
			}
			if (Objects.nonNull(slDto.getkMin())) {
				existingEntity.setkMin(slDto.getkMin());
			}
			if (Objects.nonNull(slDto.getkMax())) {
				existingEntity.setkMax(slDto.getkMax());
			}
			if (Objects.nonNull(slDto.getDefMin())) {
				existingEntity.setDefMin(slDto.getDefMin());
			}
			if (Objects.nonNull(slDto.getDefMax())) {
				existingEntity.setDefMax(slDto.getDefMax());
			}
			if (Objects.nonNull(slDto.getIsDefault())) {
				existingEntity.setIsDefault(slDto.getIsDefault());
			}

		}

	}

}
