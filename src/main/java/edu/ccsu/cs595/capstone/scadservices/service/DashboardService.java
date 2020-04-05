package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.api.LeagueApi;
import edu.ccsu.cs595.capstone.scadservices.api.SCADLeagueApi;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.util.YahooClientBuilder;

@Stateless
public class DashboardService {

	private static final Logger LOG = LoggerFactory.getLogger(DashboardService.class);

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	LeagueApi lApi;
	
	@Inject
	SCADLeagueApi slApi;
	
	@Inject
	YahooClientBuilder yahoo;
	
	public String getDashboardDetails() throws AuthorizationFailedException, RuntimeException {
		
		String dbDetails = null;
		String dbDetailsKey = "\"None\"";
		String dbDetailsBegin = "{\"key\":" ;
		String dbDetailsBody = null;
		String dbDetailsEnd = "}";
		
		Response defaultSCADLeague = slApi.getDefaultSCADLeague();
		SCADLeagueDto defaultSCADLeagueDto = null;
		if (defaultSCADLeague.hasEntity()) {
			defaultSCADLeagueDto = defaultSCADLeague.readEntity(SCADLeagueDto.class);
		}
		if (Objects.nonNull(defaultSCADLeagueDto)) {
			dbDetailsKey = "\"League\"";
			Response yahooLeague = lApi.getUserLeague(defaultSCADLeagueDto.getYahooLeagueId());
			String yahooLeagueStrg = yahooLeague.readEntity(String.class);
			Response yahooLeagueMyTeam = lApi.getUserLeagueMyTeam(defaultSCADLeagueDto.getYahooLeagueId());
			String yahooLeagueMyTeamStrg = yahooLeagueMyTeam.readEntity(String.class);
			Long yahooLeagueMyTeamId = new JsonParser().parse(yahooLeagueMyTeamStrg).getAsJsonObject().get("team_id").getAsLong();
			ObjectMapper mapper = new ObjectMapper();
			String defaultSCADLeagueStrg = null;
			try {
				defaultSCADLeagueStrg = mapper.writeValueAsString(defaultSCADLeagueDto);
			} catch (JsonProcessingException e) {
				LOG.info(e.getMessage());
			}
			Response scadLeagueMyTeam = slApi.getSCADLeagueMyTeamBySCADLeague(defaultSCADLeagueDto.getId(), yahooLeagueMyTeamId);
			SCADLeagueTeamDto myTeamSCADLeagueTeamDto = null;
			if (scadLeagueMyTeam.hasEntity()) {
				myTeamSCADLeagueTeamDto = scadLeagueMyTeam.readEntity(SCADLeagueTeamDto.class);
			}
			String myTeamSCADLeagueTeamDtoStrg = null;
			try {
				myTeamSCADLeagueTeamDtoStrg = mapper.writeValueAsString(myTeamSCADLeagueTeamDto);
			} catch (JsonProcessingException e) {
				LOG.info(e.getMessage());
			}
			dbDetailsBody = ", \"yahooLeague\":" + yahooLeagueStrg + ", \"yeahooMyTeam\":" + yahooLeagueMyTeamStrg + ", \"scadLeague\":" + defaultSCADLeagueStrg + ", \"scadMyTeam\":" + myTeamSCADLeagueTeamDtoStrg;			
		} else {
			dbDetailsKey = "\"Register\"";
			Response allUserLeagues = lApi.getUserAllLeaguesAsCommissioner();
			String allUserLeaguesStrg = allUserLeagues.readEntity(String.class);
			JsonObject allUserLeaguesObj = new JsonParser().parse(allUserLeaguesStrg).getAsJsonObject();
			JsonArray allUserLeaguesArray = allUserLeaguesObj.get("commissionerLeagues").getAsJsonArray();
			String allUserLeaguesStrg1 = allUserLeaguesArray.toString();
			dbDetailsBody = ", \"yahooLeagues\":" + allUserLeaguesStrg1;
		}
		dbDetails = dbDetailsBegin + dbDetailsKey + dbDetailsBody + dbDetailsEnd;
		return dbDetails;
		
	}
	
}
