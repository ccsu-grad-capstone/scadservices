package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.ArrayList;
import java.util.List;
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
		
		String yahooLeagueMyPlayersStrg = lSvc.getYahooLeagueMyPlayers(leagueId, teamId);
		List<Long>	playerIds = this.getYahooPlayerIds(yahooLeagueMyPlayersStrg);
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

}
