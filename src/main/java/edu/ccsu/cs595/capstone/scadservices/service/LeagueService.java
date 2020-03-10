package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.api.UserApi;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;

@Stateless
public class LeagueService {

	private static final Logger LOG = LoggerFactory.getLogger(LeagueService.class);

	@Inject
	UserApi userApi;

	@Inject
	SCADSecurityManager sm;

	private static final String YAHOORESTURI_USERLEAGUE = "https://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games;game_keys=";
	private static final String YAHOORESTURI_USERLEAGUE_EXT = "/leagues?format=json";
	private static final String YAHOORESTURI_GAMEID = "390";

	public String getLeague(Long id) {

		String result = null;
		UserDto userDto = new UserDto();
		userDto.setId(1L);
		return result;

	}

	public String getAllLeagues() {

		Long s, e;
		s = System.currentTimeMillis();
		String yahooLeagueStrg = null;
		JsonObject yahooLeagueObj = null;
		String result = null;
		String leagueUrl = YAHOORESTURI_USERLEAGUE + YAHOORESTURI_GAMEID + YAHOORESTURI_USERLEAGUE_EXT;
		Response response = userApi.getUserInfo();
		UserDto userDto = response.readEntity(UserDto.class);
		String user = userDto.getSub();
		String leagues = "leagues";
		yahooLeagueStrg = getYahooData(leagueUrl, user, leagues);
		try {
			if (Objects.nonNull(yahooLeagueStrg)) {
				yahooLeagueObj = new JsonParser().parse(yahooLeagueStrg).getAsJsonObject();
				result = getLeaguesData(yahooLeagueObj);
			}
		} catch (Exception ex) {
			LOG.error("Leagues Json parsing error for user={} - {}", user, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting all leagues for user={}, process took {}ms.", user, (e - s));
		return result;

	}

	private String getLeaguesData(JsonObject leaguesObj) {

		String result = null;

		if (Objects.nonNull(leaguesObj)) {
			try {
				JsonElement error = leaguesObj.get("error");
				if (Objects.isNull(error)) {
					JsonObject fantasyContent = leaguesObj.get("fantasy_content").getAsJsonObject();
					JsonObject users = fantasyContent.get("users").getAsJsonObject();
					JsonArray userArray = (users.get("0").getAsJsonObject()).get("user").getAsJsonArray();
					JsonObject games = userArray.get(1).getAsJsonObject().get("games").getAsJsonObject();
					JsonArray gameArray = games.get("0").getAsJsonObject().get("game").getAsJsonArray();
					JsonObject leagues = gameArray.get(1).getAsJsonObject().get("leagues").getAsJsonObject();
					int leagueCnt = leagues.get("count").getAsInt();
					JsonArray newleagues = new JsonArray();
					for (int i = 0; i < leagueCnt; i++) {
						JsonArray league = leagues.get(Integer.toString(i)).getAsJsonObject().get("league").getAsJsonArray();
						newleagues.add(league.get(0));
					}
					result = "{\"leagues\":" + newleagues.toString() + "}";
				} else {
					LOG.error("Leagues object has error: {} ", error);
					result = "ERROR:" + error.toString();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}

		}

		return result;

	}

	@SuppressWarnings("static-access")
	private String getYahooData(String url, String user, String type) {

		Long s, e;
		s = System.currentTimeMillis();
		boolean success = true;
		String result = null;
		if (Objects.nonNull(url)) {
			Client client = ClientBuilder.newClient();
			WebTarget webTarget = client.target(url);
			String authorization = "Bearer " + sm.getACCESSTOKEN();
			Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Authorization", authorization).header("Accept", "application/json")
					.header("Content-Type", "application/json");
			try {
				Response response = builder.get();
				result = response.readEntity(String.class);
				client.close();
			} catch (Exception ex) {
				success = false;
				LOG.error("Yahoo getting {} request failed for user={}, url={} - {}", type, user, url, ex.getMessage());
			}
		}
		e = System.currentTimeMillis();
		if (success) {
			LOG.info("Yahoo getting {} request was successfull for user={}, and time took {}ms.", type, user, (e - s));
		}
		return result;

	}

}
