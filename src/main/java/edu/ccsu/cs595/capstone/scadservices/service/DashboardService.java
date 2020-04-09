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
		
		SCADLeagueDto defaultSCADLeague = slSvc.getDefaultUserSCADLeagueBySeason();
		if (Objects.nonNull(defaultSCADLeague)) {
			LOG.info("SCAD League default found for userGuid={}, season={} and SCADLeagueId={}", yahoo.getYahooUserGuid(), yahoo.getSeasonYear(), defaultSCADLeague.getId());
			dbDetailsKey = "\"League\"";
			String yahooLeagueStrg = lSvc.getYahooLeague(defaultSCADLeague.getYahooLeagueId());
			String yahooMyTeamStrg = lSvc.getYahooLeagueMyTeam(defaultSCADLeague.getYahooLeagueId());
			Long yahooLeagueMyTeamId = new JsonParser().parse(yahooMyTeamStrg).getAsJsonObject().get("team_id").getAsLong();
			String yahooMyPlayersStrg = lSvc.getYahooLeagueMyPlayers(defaultSCADLeague.getYahooLeagueId(), yahooLeagueMyTeamId);
			List<Long> yahooMyPlayerIds = slpSvc.getYahooPlayerIds(yahooMyPlayersStrg);
			ObjectMapper mapper = new ObjectMapper();
			String scadLeagueStrg = null;
			try {
				scadLeagueStrg = mapper.writeValueAsString(defaultSCADLeague);
			} catch (JsonProcessingException e) {
				LOG.error(e.getMessage());
			}
			SCADLeagueTeamDto scadMyTeam = sltSvc.getSCADLeagueTeamBySCADLeagueAndYahooTeamId(defaultSCADLeague.getId(), yahooLeagueMyTeamId);
			String scadMyTeamStrg = null;
			try {
				scadMyTeamStrg = mapper.writeValueAsString(scadMyTeam);
			} catch (JsonProcessingException e) {
				LOG.error(e.getMessage());
			}
			SCADLeaguePlayerListDto scadMyPlayers = slpSvc.getSCADLeagueMyPlayersBySCADLeague(defaultSCADLeague.getId(), yahooMyPlayerIds);
			String scadMyPlayersStrg = null;
			try {
				scadMyPlayersStrg = mapper.writeValueAsString(scadMyPlayers);
			} catch (JsonProcessingException e) {
				LOG.info(e.getMessage());
			}			
			dbDetailsBody = ", \"yahooLeague\":" + yahooLeagueStrg + ", \"yahooMyTeam\":" + yahooMyTeamStrg + ", \"yahooMyPlayers\":" + yahooMyPlayersStrg + ", \"scadLeague\":" + scadLeagueStrg + ", \"scadMyTeam\":" + scadMyTeamStrg + ", \"scadMyPlayers\":" + scadMyPlayersStrg;			
		} else {
			LOG.info("SCAD League default not found for userGuid={}, season={}", yahoo.getYahooUserGuid(), yahoo.getSeasonYear());
			dbDetailsKey = "\"Register\"";
			String userLeaguesStrg = lSvc.getUserLeaguesAsCommissionerBySeason();
			JsonObject userLeaguesObj = new JsonParser().parse(userLeaguesStrg).getAsJsonObject();
			JsonArray userLeaguesArray = userLeaguesObj.get("commissionerLeagues").getAsJsonArray();
			String userLeaguesStrg1 = userLeaguesArray.toString();
			dbDetailsBody = ", \"yahooLeagues\":" + userLeaguesStrg1;
		}
		dbDetails = dbDetailsBegin + dbDetailsKey + dbDetailsBody + dbDetailsEnd;
		return dbDetails;
		
	}
	
}
