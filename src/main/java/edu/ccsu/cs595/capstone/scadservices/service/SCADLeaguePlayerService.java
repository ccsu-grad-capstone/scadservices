package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.dao.SCADLeaguePlayerDao;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerListDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.entity.AuditContext;
import edu.ccsu.cs595.capstone.scadservices.entity.SCADLeaguePlayer;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.util.YahooClientBuilder;

@Stateless
public class SCADLeaguePlayerService {

	private static final Logger LOG = LoggerFactory.getLogger(SCADLeaguePlayerService.class);
	private static String CACHE_YAHOO_TEAMS = null;
	private Map<Long, String> CACHE_YAHOO_PLAYERS = new LinkedHashMap<>();

	@Inject
	SCADLeaguePlayerDao slpDao;
	
	@Inject
	YahooClientBuilder yahoo;
	
	@Inject
	LeagueService lSvc;
	
	@Inject
	SCADLeagueService slSvc;
	
	@Inject
	SCADLeagueTeamService sltSvc;

	public SCADLeaguePlayerDto getSCADLeaguePlayer(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerDto result = null;
		SCADLeaguePlayer slpEntity = slpDao.find(id);
		result = this.entityToDto(slpEntity);
		return result;

	}

	public SCADLeaguePlayerDto getSCADLeaguePlayerByYahooLeagueAndPlayer(Long leagueId, Long playerId) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerDto result = null;
		SCADLeaguePlayer slpEntity = slpDao.getSCADLeaguePlayerByYahooLeagueAndPlayer(leagueId, playerId);
		result = this.entityToDto(slpEntity);
		return result;

	}
	
	public SCADLeaguePlayerListDto getSCADLeaguePlayersBySCADLeagueAndTeam(Long scadLeagueId, Long scadTeamId) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerListDto list = null;
		SCADLeagueDto scadLeagueDto = slSvc.getSCADLeague(scadLeagueId);
		SCADLeagueTeamDto scadLeagueTeamDto = sltSvc.getSCADLeagueTeam(scadTeamId);
		list = this.getSCADLeaguePlayersByYahooLeagueAndTeam(scadLeagueDto.getYahooLeagueId(), scadLeagueTeamDto.getYahooLeagueTeamId());
		return list;

	}
	
	public SCADLeaguePlayerListDto getSCADLeaguePlayersByYahooLeagueAndTeam(Long leagueId, Long teamId) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerListDto list = null;
		List<Long> playerIds = this.getPlayersByYahooLeagueAndTeam(leagueId, teamId);
		list = this.getSCADLeagueMyPlayersByYahooLeague(leagueId, playerIds);
		return list;

	}
	
	public SCADLeaguePlayerDto getSCADLeaguePlayerBySCADLeagueAndPlayer(Long scadLeagueId, Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerDto result = null;
		SCADLeaguePlayer slpEntity = slpDao.getSCADLeaguePlayerBySCADLeagueAndPlayer(scadLeagueId, id);
		result = this.entityToDto(slpEntity);
		return result;

	}

	public SCADLeaguePlayerListDto getSCADLeaguePlayersByYahooLeague(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerListDto list = new SCADLeaguePlayerListDto();
		List<SCADLeaguePlayer> slpEntityList = slpDao.getSCADLeaguePlayersByYahooLeague(leagueId);
		for (SCADLeaguePlayer slpEntity : slpEntityList) {
			SCADLeaguePlayerDto result = this.entityToDto(slpEntity);
			list.getScadLeaguePlayers().add(result);
		}
		return list;

	}
	
	public SCADLeaguePlayerListDto getSCADLeaguePlayersBySCADLeague(Long scadLeagueId) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerListDto list = new SCADLeaguePlayerListDto();
		List<SCADLeaguePlayer> slpEntityList = slpDao.getSCADLeaguePlayersBySCADLeague(scadLeagueId);
		for (SCADLeaguePlayer slpEntity : slpEntityList) {
			SCADLeaguePlayerDto result = this.entityToDto(slpEntity);
			list.getScadLeaguePlayers().add(result);
		}
		return list;

	}
	
	public SCADLeaguePlayerListDto getSCADLeagueMyPlayers(Long scadLeagueId) throws AuthorizationFailedException, RuntimeException {
		
		SCADLeaguePlayerListDto list = null;
		SCADLeagueDto scadLeagueDto = slSvc.getSCADLeague(scadLeagueId);
		List<Long> playerIds =this.getPlayersIds(scadLeagueDto.getYahooLeagueId());
		list = this.getSCADLeagueMyPlayersBySCADLeague(scadLeagueId, playerIds);
		return list;
		
	}
	
	public SCADLeaguePlayerListDto getSCADLeagueMyPlayersByYahooLeague(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		SCADLeaguePlayerListDto list = null;
		List<Long> playerIds =this.getPlayersIds(leagueId);
		list = this.getSCADLeagueMyPlayersByYahooLeague(leagueId, playerIds);
		return list;
		
	}
	
	public List<Long> getPlayersIds(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		Long teamId = sltSvc.getMyYahooTeamId(leagueId);
		String yahooLeagueMyPlayersStrg = lSvc.getYahooLeagueMyPlayers(leagueId, teamId);
		List<Long>	playerIds = this.getYahooPlayerIds(yahooLeagueMyPlayersStrg);
		return playerIds;
		
	}
	
	public List<Long> getPlayersByYahooLeagueAndTeam(Long leagueId, Long teamId) throws AuthorizationFailedException, RuntimeException {
		
		String yahooLeagueMyPlayersStrg = lSvc.getYahooLeagueTeamPlayers(leagueId, teamId);
		JsonObject yahooLeagueMyPlayersObj = new JsonParser().parse(yahooLeagueMyPlayersStrg).getAsJsonObject();
		JsonArray yahooLeagueMyPlayersArray = yahooLeagueMyPlayersObj.get("players").getAsJsonArray();
		String yahooLeagueMyPlayersStrg1 = yahooLeagueMyPlayersArray.toString();
		List<Long>	playerIds = this.getYahooPlayerIds(yahooLeagueMyPlayersStrg1);
		return playerIds;
		
	}
	
	public List<Long> getYahooPlayerIds(String yahooLeagueMyPlayersStrg) {
		
		JsonArray players = new JsonParser().parse(yahooLeagueMyPlayersStrg).getAsJsonArray();
		List<Long> playerIds = new ArrayList<Long>();
		for (JsonElement player : players) {
			JsonObject playerObj = ((JsonObject) player).getAsJsonObject();
			Long playerId = playerObj.get("player_id").getAsLong();
			playerIds.add(playerId);
		}
		
		return playerIds;
		
	}


	public SCADLeaguePlayerListDto getSCADLeagueMyPlayersBySCADLeague(Long scadLeagueId, List<Long> playerIds) {
		
		SCADLeaguePlayerListDto list = new SCADLeaguePlayerListDto();
		List<SCADLeaguePlayer> slpEntityList = slpDao.getSCADLeaguePlayersBySCADLeagueAndYahooPlayersIds(scadLeagueId, playerIds);
		for (SCADLeaguePlayer slpEntity : slpEntityList) {
			SCADLeaguePlayerDto result = this.entityToDto(slpEntity);
			list.getScadLeaguePlayers().add(result);
		}
		return list;
		
	}
	
	public SCADLeaguePlayerListDto getSCADLeagueMyPlayersByYahooLeague(Long leagueId, List<Long> playerIds) {
		
		SCADLeaguePlayerListDto list = new SCADLeaguePlayerListDto();
		List<SCADLeaguePlayer> slpEntityList = slpDao.getSCADLeaguePlayersByYahooLeagueAndPlayersIds(leagueId, playerIds);
		for (SCADLeaguePlayer slpEntity : slpEntityList) {
			SCADLeaguePlayerDto result = this.entityToDto(slpEntity);
			list.getScadLeaguePlayers().add(result);
		}
		return list;
		
	}

	public SCADLeaguePlayerDto createSCADLeaguePlayer(SCADLeaguePlayerDto slpDto) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerDto result = null;
		SCADLeaguePlayer newEntity = this.dtoToEntity(slpDto);
		AuditContext ac = new AuditContext(yahoo.getYahooUserGuid(),yahoo.getYahooUserName());
		AuditContext.setAuditContext(ac);
		newEntity = slpDao.upsert(newEntity);
		LOG.info("SCADLeaguePlayer created successfully for leagueId={}, SCADLeagueId={}, the new SCADLeaguePlayerId={}", slpDto.getYahooLeagueId(), slpDto.getScadLeagueId(), newEntity.getId());
		result = this.entityToDto(newEntity);
		return result;

	}

	public SCADLeaguePlayerDto updateSCADLeaguePlayer(Long id, SCADLeaguePlayerDto slpDto)
			throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerDto result = null;
		SCADLeaguePlayer existingEntity = slpDao.find(id);
		this.dtoToEntityForUpdate(slpDto, existingEntity);
		AuditContext ac = new AuditContext(yahoo.getYahooUserGuid(),yahoo.getYahooUserName());
		AuditContext.setAuditContext(ac);
		existingEntity = slpDao.upsert(existingEntity);
		result = this.entityToDto(existingEntity);
		return result;

	}

	public void deleteSCADLeaguePlayer(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayer deleteEntity = slpDao.find(id);
		AuditContext ac = new AuditContext(yahoo.getYahooUserGuid(),yahoo.getYahooUserName());
		AuditContext.setAuditContext(ac);
		slpDao.delete(deleteEntity);

	}

	private SCADLeaguePlayerDto entityToDto(SCADLeaguePlayer slpEntity) {

		if (Objects.nonNull(slpEntity)) {
			
			SCADLeaguePlayerDto result = new SCADLeaguePlayerDto();

			result.setId(slpEntity.getId());
			result.setYahooLeaguePlayerId(slpEntity.getYahooLeaguePlayerId());
			result.setYahooLeagueId(slpEntity.getYahooLeagueId());
			result.setScadLeagueId(slpEntity.getScadLeagueId());
			result.setSalary(slpEntity.getSalary());
			result.setIsFranchiseTag(slpEntity.getIsFranchiseTag());			
			result.setCreatedBy(slpEntity.getCreatedBy());
			result.setCreatedAt(slpEntity.getCreatedAt());
			result.setModifiedBy(slpEntity.getModifiedBy());
			result.setModifiedAt(slpEntity.getModifiedAt());
			result.setRenewSCADLeaguePlayerId(slpEntity.getRenewSCADLeaguePlayerId());
			result.setPreviousYearSalary(slpEntity.getPreviousYearSalary());
			this.setPlayerTeamDetails(result);
			
			return result;

		}

		return null;

	}

	private SCADLeaguePlayer dtoToEntity(SCADLeaguePlayerDto slpDto) {

		if (Objects.nonNull(slpDto)) {
			
			SCADLeaguePlayer result = new SCADLeaguePlayer();

			result.setYahooLeaguePlayerId(slpDto.getYahooLeaguePlayerId());
			result.setYahooLeagueId(slpDto.getYahooLeagueId());
			result.setScadLeagueId(slpDto.getScadLeagueId());
			result.setSalary(slpDto.getSalary());
			result.setIsFranchiseTag(slpDto.getIsFranchiseTag());
			if (Objects.isNull(slpDto.getRenewSCADLeaguePlayerId())) {
				result.setRenewSCADLeaguePlayerId(0L);
			} else {
				result.setRenewSCADLeaguePlayerId(slpDto.getRenewSCADLeaguePlayerId());
			}
			if (Objects.isNull(slpDto.getPreviousYearSalary())) {
				result.setPreviousYearSalary(0L);
			} else {
				result.setPreviousYearSalary(slpDto.getPreviousYearSalary());
			}
			
			return result;

		}

		return null;

	}

	private void dtoToEntityForUpdate(SCADLeaguePlayerDto slpDto, SCADLeaguePlayer existingEntity) {

		if (Objects.nonNull(slpDto)) {

			if (Objects.nonNull(slpDto.getSalary())) {
				existingEntity.setSalary(slpDto.getSalary());
			}
			if (Objects.nonNull(slpDto.getIsFranchiseTag())) {
				existingEntity.setIsFranchiseTag(slpDto.getIsFranchiseTag());
			}

		}

	}
	
	private void setPlayerTeamDetails(SCADLeaguePlayerDto result) {
		
		if (Objects.nonNull(result)) {
			
			SCADLeagueTeamDto SCADLeagueTeam = this.getSCADTeamByYahooPlayer(result.getYahooLeagueId(), result.getYahooLeaguePlayerId());
			if (Objects.nonNull(SCADLeagueTeam)) {
				result.setYahooTeamId(SCADLeagueTeam.getYahooLeagueTeamId());
				result.setScadTeamId(SCADLeagueTeam.getId());
			}
		}
		
	}
	
	private SCADLeagueTeamDto getSCADTeamByYahooPlayer(Long leagueId, Long playerId) {
		
		SCADLeagueTeamDto result = null;
		
		if (Objects.nonNull(playerId)) {
			
			try {
				String yahooTeams = null;
				if (Objects.isNull(CACHE_YAHOO_TEAMS)) {
					yahooTeams = lSvc.getYahooLeagueTeams(leagueId);
					CACHE_YAHOO_TEAMS = yahooTeams;
				} else {
					yahooTeams = CACHE_YAHOO_TEAMS;
				}
				JsonObject teamsObj = new JsonParser().parse(yahooTeams).getAsJsonObject();
				JsonArray teams =	teamsObj.get("teams").getAsJsonArray();
				Long teamId = null;
				boolean breakInd = false;
				for (JsonElement team : teams) {
					JsonObject teamObj = ((JsonObject) team).getAsJsonObject();
					teamId = teamObj.get("team_id").getAsLong();
					String yahooPlayers = null;
					if (CACHE_YAHOO_PLAYERS.size() > 0) {
						if (CACHE_YAHOO_PLAYERS.containsKey(teamId)) {
							yahooPlayers = CACHE_YAHOO_PLAYERS.get(teamId);
						} else {
							yahooPlayers = lSvc.getYahooLeagueTeamPlayers(leagueId,teamId);
							CACHE_YAHOO_PLAYERS.put(teamId, yahooPlayers);
						}
					} else {
						yahooPlayers = lSvc.getYahooLeagueTeamPlayers(leagueId,teamId);
						CACHE_YAHOO_PLAYERS.put(teamId, yahooPlayers);
					}
					JsonObject playersObj = new JsonParser().parse(yahooPlayers).getAsJsonObject();
					JsonArray players = playersObj.get("players").getAsJsonArray();
					Long fetchedPlayerId = null;
					for (JsonElement player : players) {
						JsonObject playerObj = ((JsonObject) player).getAsJsonObject();
						fetchedPlayerId = playerObj.get("player_id").getAsLong();
						if (fetchedPlayerId.equals(playerId)) {
							breakInd = true;
							break;
						}
					}
					if (breakInd) {
						break;
					}
				}
				if (Objects.nonNull(teamId)) {
					result = sltSvc.getSCADLeagueTeamByYahooLeagueAndTeam(leagueId, teamId);
				}

			} catch (AuthorizationFailedException e) {
				LOG.error(e.getMessage());
			} catch (RuntimeException e) {
				LOG.error(e.getMessage());
			}
			
		}
		
		return result;
		
	}

}
