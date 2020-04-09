package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;

@Stateless
public class DashboardService {

	private static final Logger LOG = LoggerFactory.getLogger(DashboardService.class);

	@Inject
	LeagueService lSvc;
	
	@Inject
	SCADLeagueService slSvc;
	
	public String getDashboardDetails() throws AuthorizationFailedException, RuntimeException {
		
		String dbDetails = null;
		String dbDetailsKey = "\"None\"";
		String dbDetailsBegin = "{\"key\":" ;
		String dbDetailsBody = null;
		String dbDetailsEnd = "}";
		
//		SCADLeagueDto defaultSCADLeagueDto = slSvc.getDefaultUserSCADLeagueBySeason();
//		if (Objects.nonNull(defaultSCADLeagueDto)) {
//			dbDetailsKey = "\"League\"";
//			String yahooLeagueStrg = lSvc.getYahooLeague(defaultSCADLeagueDto.getYahooLeagueId());
//			String yahooLeagueMyTeamStrg = lSvc.getYahooLeagueMyTeam(defaultSCADLeagueDto.getYahooLeagueId());
//			Long yahooLeagueMyTeamId = new JsonParser().parse(yahooLeagueMyTeamStrg).getAsJsonObject().get("team_id").getAsLong();
//			String yahooLeagueMyPlayersStrg = lSvc.getYahooLeagueMyPlayers(defaultSCADLeagueDto.getYahooLeagueId(), yahooLeagueMyTeamId);
//			List<Long> yahooPlayerIds = this.getYahooPlayerIds(yahooLeagueMyPlayersStrg);
//			ObjectMapper mapper = new ObjectMapper();
//			String defaultSCADLeagueStrg = null;
//			try {
//				defaultSCADLeagueStrg = mapper.writeValueAsString(defaultSCADLeagueDto);
//			} catch (JsonProcessingException e) {
//				LOG.info(e.getMessage());
//			}
//			Response scadLeagueMyTeam = slApi.getSCADLeagueMyTeamBySCADLeague(defaultSCADLeagueDto.getId(), yahooLeagueMyTeamId);
//			SCADLeagueTeamDto myTeamSCADLeagueTeamDto = null;
//			if (scadLeagueMyTeam.hasEntity()) {
//				myTeamSCADLeagueTeamDto = scadLeagueMyTeam.readEntity(SCADLeagueTeamDto.class);
//			}
//			String myTeamSCADLeagueTeamDtoStrg = null;
//			try {
//				myTeamSCADLeagueTeamDtoStrg = mapper.writeValueAsString(myTeamSCADLeagueTeamDto);
//			} catch (JsonProcessingException e) {
//				LOG.info(e.getMessage());
//			}
//			Response scadLeagueMyTeamMyPlayers = slApi.getSCADLeagueMyTeamMyPlayersBySCADLeague(defaultSCADLeagueDto.getId(), yahooPlayerIds);
//			SCADLeaguePlayerListDto myTeamMyPlayersSCADLeaguePlayerListDto = null;
//			if (scadLeagueMyTeamMyPlayers.hasEntity()) {
//				myTeamMyPlayersSCADLeaguePlayerListDto = scadLeagueMyTeamMyPlayers.readEntity(SCADLeaguePlayerListDto.class);
//			}
//			String myTeamMyPlayersSCADLeaguePlayerListDtoStrg = null;
//			try {
//				myTeamMyPlayersSCADLeaguePlayerListDtoStrg = mapper.writeValueAsString(myTeamMyPlayersSCADLeaguePlayerListDto);
//			} catch (JsonProcessingException e) {
//				LOG.info(e.getMessage());
//			}			
//			dbDetailsBody = ", \"yahooLeague\":" + yahooLeagueStrg + ", \"yahooMyTeam\":" + yahooLeagueMyTeamStrg + ", \"yahooMyPlayers\":" + yahooLeagueMyTeamMyPlayersStrg + ", \"scadLeague\":" + defaultSCADLeagueStrg + ", \"scadMyTeam\":" + myTeamSCADLeagueTeamDtoStrg + ", \"scadMyPlayers\":" + myTeamMyPlayersSCADLeaguePlayerListDtoStrg;			
//		} else {
//			dbDetailsKey = "\"Register\"";
//			String userLeaguesStrg = lSvc.getUserLeaguesAsCommissionerBySeason();
//			JsonObject userLeaguesObj = new JsonParser().parse(userLeaguesStrg).getAsJsonObject();
//			JsonArray userLeaguesArray = userLeaguesObj.get("commissionerLeagues").getAsJsonArray();
//			String userLeaguesStrg1 = userLeaguesArray.toString();
//			dbDetailsBody = ", \"yahooLeagues\":" + userLeaguesStrg1;
//		}
		dbDetails = dbDetailsBegin + dbDetailsKey + dbDetailsBody + dbDetailsEnd;
		return dbDetails;
		
	}
	
	private List<Long> getYahooPlayerIds(String yahooLeagueMyTeamMyPlayersStrg) {
		
		JsonArray players = new JsonParser().parse(yahooLeagueMyTeamMyPlayersStrg).getAsJsonArray();
		List<Long> playerIds = new ArrayList<Long>();
		for (JsonElement player : players) {
			JsonObject playerObj = ((JsonObject) player).getAsJsonObject();
			Long playerId = playerObj.get("player_id").getAsLong();
			playerIds.add(playerId);
		}
		
		return playerIds;
		
	}
	
}
