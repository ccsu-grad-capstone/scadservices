package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerListDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.util.YahooClientBuilder;

@Stateless
public class DashboardService {

	private static final Logger LOG = LoggerFactory.getLogger(DashboardService.class);

	@Inject
	LeagueService lSvc;
	
	@Inject
	SCADLeagueService slSvc;
	
	@Inject
	SCADLeagueTeamService sltSvc;
	
	@Inject
	SCADLeaguePlayerService slpSvc;
	
	@Inject
	YahooClientBuilder yahoo;
	
	public String getDashboardDetails() throws AuthorizationFailedException, RuntimeException {
		
		String dbDetails = null;
		String dbDetailsKey = "\"None\"";
		String dbDetailsBegin = "{\"key\":" ;
		String dbDetailsBody = null;
		String dbDetailsEnd = "}";
		String dbSeason = ", \"season\":";
		String dbCS = "\"current\"";
		String dbPS = "\"prior\"";
		String dbSeasonKey = null;
		SCADLeagueDto defaultSCADLeague = null;
		
		defaultSCADLeague = slSvc.getDefaultUserSCADLeagueBySeason();
		if (Objects.nonNull(defaultSCADLeague)) {
			LOG.info("SCAD League default found for userGuid={}, season={} and SCADLeagueId={}", yahoo.getYahooUserGuid(), yahoo.getCurrentSeason(), defaultSCADLeague.getId());
			dbDetailsKey = "\"League\"";
			dbSeasonKey = dbSeason + dbCS;
			dbDetailsBody = this.gatherCurrentSeasonDetails(defaultSCADLeague);		
		} else {
			LOG.info("SCAD League default not found for userGuid={}, season={}", yahoo.getYahooUserGuid(), yahoo.getCurrentSeason());
			defaultSCADLeague = slSvc.getDefaultUserSCADLeagueByPriorSeason();
			if (Objects.nonNull(defaultSCADLeague)) {
				LOG.info("SCAD League default found for userGuid={}, priorSeason={} and SCADLeagueId={}", yahoo.getYahooUserGuid(), yahoo.getPriorSeason(), defaultSCADLeague.getId());
				String yahooLeagueStrg = lSvc.getYahooPriorLeague(defaultSCADLeague.getYahooLeagueId());
				String renewed = new JsonParser().parse(yahooLeagueStrg).getAsJsonObject().get("renewed").getAsString();
				if (Objects.nonNull(renewed)) {
					String[] arrOfStr = renewed.split("_", 2); 
					Long gameKey = Long.valueOf(arrOfStr[0]);
					Long leagueId = Long.valueOf(arrOfStr[1]);
					SCADLeagueDto scadLeague = slSvc.getSCADLeagueByYahooLeague(leagueId);
					if (Objects.nonNull(scadLeague)) {
						dbDetailsKey = "\"League\"";
						dbSeasonKey = dbSeason + dbPS;
						dbDetailsBody = this.gatherCurrentSeasonDetails(scadLeague);	
					} else {
						dbDetailsKey = "\"League\"";
						dbSeasonKey = dbSeason + dbCS;
						scadLeague = this.renewSCADLeagueForCurrentSeason(defaultSCADLeague, gameKey, leagueId);
						dbDetailsBody = this.gatherCurrentSeasonDetails(scadLeague);	
					}
				} else {
					dbDetailsKey = "\"League\"";
					dbSeasonKey = dbSeason + dbPS;
					dbDetailsBody = this.gatherPriorSeasonDetails(defaultSCADLeague);		
				}
			} else {
				LOG.info("SCAD League default not found for userGuid={}, priorSeason={}", yahoo.getYahooUserGuid(), yahoo.getPriorSeason());
				dbDetailsKey = "\"Register\"";
				dbSeasonKey = dbSeason + dbPS;
				String userLeaguesStrg = lSvc.getUserLeaguesAsCommissionerByPriorSeason();
				JsonObject userLeaguesObj = new JsonParser().parse(userLeaguesStrg).getAsJsonObject();
				JsonArray userLeaguesArray = userLeaguesObj.get("commissionerLeagues").getAsJsonArray();
				String userLeaguesStrg1 = userLeaguesArray.toString();
				dbDetailsBody = ", \"yahooLeagues\":" + userLeaguesStrg1;
			}
		}
		dbDetails = dbDetailsBegin + dbDetailsKey + dbSeasonKey + dbDetailsBody + dbDetailsEnd;
		return dbDetails;
		
	}
	
	private String gatherCurrentSeasonDetails(SCADLeagueDto scadLeague) throws AuthorizationFailedException, RuntimeException {
		
		String dbDetailsBody = null;
		String yahooLeagueStrg = lSvc.getYahooLeague(scadLeague.getYahooLeagueId());
		String yahooMyTeamStrg = lSvc.getYahooLeagueMyTeam(scadLeague.getYahooLeagueId());
		Long yahooLeagueMyTeamId = new JsonParser().parse(yahooMyTeamStrg).getAsJsonObject().get("team_id").getAsLong();
		String yahooMyPlayersStrg = lSvc.getYahooLeagueMyPlayers(scadLeague.getYahooLeagueId(), yahooLeagueMyTeamId);
		List<Long> yahooMyPlayerIds = slpSvc.getYahooPlayerIds(yahooMyPlayersStrg);
		ObjectMapper mapper = new ObjectMapper();
		String scadLeagueStrg = null;
		try {
			scadLeagueStrg = mapper.writeValueAsString(scadLeague);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage());
		}
		SCADLeagueTeamDto scadMyTeam = sltSvc.getSCADLeagueTeamBySCADLeagueAndYahooTeamId(scadLeague.getId(), yahooLeagueMyTeamId);
		String scadMyTeamStrg = null;
		try {
			scadMyTeamStrg = mapper.writeValueAsString(scadMyTeam);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage());
		}
		SCADLeaguePlayerListDto scadMyPlayers = slpSvc.getSCADLeagueMyPlayersBySCADLeague(scadLeague.getId(), yahooMyPlayerIds);
		String scadMyPlayersStrg = null;
		try {
			scadMyPlayersStrg = mapper.writeValueAsString(scadMyPlayers);
		} catch (JsonProcessingException e) {
			LOG.info(e.getMessage());
		}			
		dbDetailsBody = ", \"yahooLeague\":" + yahooLeagueStrg + ", \"yahooMyTeam\":" + yahooMyTeamStrg + ", \"yahooMyPlayers\":" + yahooMyPlayersStrg + ", \"scadLeague\":" + scadLeagueStrg + ", \"scadMyTeam\":" + scadMyTeamStrg + ", \"scadMyPlayers\":" + scadMyPlayersStrg;			
		return dbDetailsBody;
		
	}
	
	private String gatherPriorSeasonDetails(SCADLeagueDto scadLeague) throws AuthorizationFailedException, RuntimeException {
		
		String dbDetailsBody = null;
		String yahooLeagueStrg = lSvc.getYahooPriorLeague(scadLeague.getYahooLeagueId());
		String yahooMyTeamStrg = lSvc.getYahooPriorLeagueMyTeam(scadLeague.getYahooLeagueId());
		Long yahooLeagueMyTeamId = new JsonParser().parse(yahooMyTeamStrg).getAsJsonObject().get("team_id").getAsLong();
		String yahooMyPlayersStrg = lSvc.getYahooPriorLeagueMyPlayers(scadLeague.getYahooLeagueId(), yahooLeagueMyTeamId);
		List<Long> yahooMyPlayerIds = slpSvc.getYahooPlayerIds(yahooMyPlayersStrg);
		ObjectMapper mapper = new ObjectMapper();
		String scadLeagueStrg = null;
		try {
			scadLeagueStrg = mapper.writeValueAsString(scadLeague);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage());
		}
		SCADLeagueTeamDto scadMyTeam = sltSvc.getSCADLeagueTeamBySCADLeagueAndYahooTeamId(scadLeague.getId(), yahooLeagueMyTeamId);
		String scadMyTeamStrg = null;
		try {
			scadMyTeamStrg = mapper.writeValueAsString(scadMyTeam);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage());
		}
		SCADLeaguePlayerListDto scadMyPlayers = slpSvc.getSCADLeagueMyPlayersBySCADLeague(scadLeague.getId(), yahooMyPlayerIds);
		String scadMyPlayersStrg = null;
		try {
			scadMyPlayersStrg = mapper.writeValueAsString(scadMyPlayers);
		} catch (JsonProcessingException e) {
			LOG.info(e.getMessage());
		}			
		dbDetailsBody = ", \"yahooLeague\":" + yahooLeagueStrg + ", \"yahooMyTeam\":" + yahooMyTeamStrg + ", \"yahooMyPlayers\":" + yahooMyPlayersStrg + ", \"scadLeague\":" + scadLeagueStrg + ", \"scadMyTeam\":" + scadMyTeamStrg + ", \"scadMyPlayers\":" + scadMyPlayersStrg;			
		return dbDetailsBody;
		
	}
	
	private SCADLeagueDto renewSCADLeagueForCurrentSeason(SCADLeagueDto scadLeague, Long gameKey, Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		SCADLeagueDto result = null;
		result = slSvc.renewSCADLeagueForCurrentSeason(scadLeague, gameKey, leagueId);
		return result;
		
	}
	

	
}
