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
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;

@Stateless
public class LeagueService {

	private static final Logger LOG = LoggerFactory.getLogger(LeagueService.class);

	@Inject
	UserApi userApi;

	@Inject
	SCADSecurityManager sm;
	
	private static final String YAHOORESTURI_GAMEINFO = "https://fantasysports.yahooapis.com/fantasy/v2/game/nfl?format=json";
	private static final String YAHOORESTURI_USERLEAGUES = "https://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games;game_keys=";
	private static final String YAHOORESTURI_USERLEAGUES_EXT = "/leagues?format=json";
	private static final String YAHOORESTURI_USERLEAGUE = "https://fantasysports.yahooapis.com/fantasy/v2/league/nfl.l.";
	private static final String YAHOORESTURI_USERLEAGUE_EXT = "?format=json";
	
	public String getUserGuid() {
		
		String userGuid = null;
		Response response = userApi.getUserInfo();
		UserDto userDto = response.readEntity(UserDto.class);
		userGuid = userDto.getSub();
		return userGuid;
		
	}
	
	public Long getGameId() throws AuthorizationFailedException, RuntimeException {
		
		return Long.valueOf(this.getYahooGameId());
		
	}
	
	public Long getSeasonYear() throws AuthorizationFailedException, RuntimeException {
		
		return Long.valueOf(this.getYahooGameSeason());
		
	}	

	public String getUserLeague(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		String league = "league";
		String result = null;
		String yahooLeagueStrg = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = this.getUserGuid();
		String leagueUrl = YAHOORESTURI_USERLEAGUE + leagueId + YAHOORESTURI_USERLEAGUE_EXT;
		yahooLeagueStrg = this.getYahooData(leagueUrl, userGuid, league);
		try {
			if (Objects.nonNull(yahooLeagueStrg)) {
				yahooLeagueObj = new JsonParser().parse(yahooLeagueStrg).getAsJsonObject();
				result = this.getLeagueData(yahooLeagueObj);
			}
		} catch (Exception ex) {
			LOG.error("Leagues Json parsing error for userGuid={} - {}", userGuid, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting all leagues for userGuid={}, process took {}ms.", userGuid, (e - s));
		return result;

	}

	public String getUserAllLeagues() throws AuthorizationFailedException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		String leagues = "leagues";
		String result = null;
		String yahooLeagueStrg = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = this.getUserGuid();
		Long gameId = this.getGameId();
		String leagueUrl = YAHOORESTURI_USERLEAGUES + gameId + YAHOORESTURI_USERLEAGUES_EXT;
		yahooLeagueStrg = this.getYahooData(leagueUrl, userGuid, leagues);
		try {
			if (Objects.nonNull(yahooLeagueStrg)) {
				yahooLeagueObj = new JsonParser().parse(yahooLeagueStrg).getAsJsonObject();
				result = this.getLeaguesData(yahooLeagueObj);
			}
		} catch (Exception ex) {
			LOG.error("Leagues Json parsing error for userGuid={} - {}", userGuid, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting all leagues for userGuid={}, process took {}ms.", userGuid, (e - s));
		return result;

	}
	
	private int getYahooGameId() throws AuthorizationFailedException, RuntimeException {
		
		int gameId = 0;
		String game = "game";
		String gameStrg = null;
		JsonObject gameObj = null;
		String userGuid = this.getUserGuid();
		gameStrg = getYahooData(YAHOORESTURI_GAMEINFO, userGuid, game);
		try {
			gameObj = new JsonParser().parse(gameStrg).getAsJsonObject();
			JsonElement element = gameObj.get("fantasy_content").getAsJsonObject().get("game").getAsJsonArray().get(0).getAsJsonObject().get("game_id");
			gameId = element.getAsInt();
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

		return gameId;
	}
	
	private int getYahooGameSeason() throws AuthorizationFailedException, RuntimeException {
		
		int gameSeason = 0;
		String game = "game";
		String gameStrg = null;
		JsonObject gameObj = null;
		String userGuid = this.getUserGuid();
		gameStrg = getYahooData(YAHOORESTURI_GAMEINFO, userGuid, game);
		try {
			gameObj = new JsonParser().parse(gameStrg).getAsJsonObject();
			JsonElement element = gameObj.get("fantasy_content").getAsJsonObject().get("game").getAsJsonArray().get(0).getAsJsonObject().get("season");
			gameSeason = element.getAsInt();
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

		return gameSeason;
	}

	private String getLeaguesData(JsonObject leaguesObj) throws AuthorizationFailedException, RuntimeException {

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
	
	private String getLeagueData(JsonObject leagueObj) throws AuthorizationFailedException, RuntimeException {

		String result = null;

		if (Objects.nonNull(leagueObj)) {
			try {
				JsonElement error = leagueObj.get("error");
				if (Objects.isNull(error)) {
					JsonObject fantasyContent = leagueObj.get("fantasy_content").getAsJsonObject();
					JsonArray league = fantasyContent.get("league").getAsJsonArray();
					JsonObject ldata = league.get(0).getAsJsonObject();
					result = ldata.toString();
				} else {
					LOG.error("League object has error: {} ", error);
					result = "ERROR:" + error.toString();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}

		}

		return result;

	}

	@SuppressWarnings("static-access")
	private String getYahooData(String url, String user, String type) throws AuthorizationFailedException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		boolean success = true;
		String result = null;
		JsonObject resultObj = null;
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
				throw new RuntimeException(ex.getMessage());
			}
		}
		e = System.currentTimeMillis();
		if (success) {
			if (Objects.nonNull(result)) {
				try {
					resultObj = new JsonParser().parse(result).getAsJsonObject();
					JsonElement error = resultObj.get("error");
					if (Objects.nonNull(error)) {
						LOG.error("Yahoo getting {} request failed for user={}, url={} - {}", type, user, url, error);
						throw new AuthorizationFailedException(error.toString());
					} else {
						LOG.info("Yahoo getting {} request was successfull for user={}, and time took {}ms.", type, user, (e - s));
					}
				} catch (Exception ex) {
					LOG.error("Yahoo getting {} request failed (exception) for user={}, url={} - {}", type, user, url, ex.getMessage());
					throw new AuthorizationFailedException(ex.getMessage());
				}
			} else {
				LOG.error("Yahoo getting {} request failed for user={}, url={}", type, user, url);
			}
		}
		
		return result;

	}
	
}
