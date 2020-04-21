package edu.ccsu.cs595.capstone.scadservices.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.api.UserApi;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.DashboardService;

public class YahooClientBuilder {
	
	private static final Logger LOG = LoggerFactory.getLogger(DashboardService.class);

	@Inject
	UserApi userApi;

	@Inject
	SCADSecurityManager sm;

	private String userGuid = null;
	private String userName = null;
	
	private static final String NFL="390";
	//private static final String NFL="nfl";
	private static final String NFLSEASON=";seasons=";
	private static final String GAMEURI = "https://fantasysports.yahooapis.com/fantasy/v2/game/";
	private static final String URIFORMAT = "?format=json";

	@SuppressWarnings("unused")
	private final String YAHOO_DUMMY_DATA_ROOT = "src/main/resources/data/";
	@SuppressWarnings({ "serial", "unused" })
	private final Map<String, String> dummyData = new HashMap<String, String>() {{
		put("leagues", "user_leagues.json");
		put("league", "user_league.json");
		put("teams", "user_league_teams.json");
		put("settings", "user_league_settings.json");
		put("standings", "user_league_standings.json");
		put("team", "user_league_team.json");
		put("players", "user_league_players.json");
		put("commissionerLeagues", "user_commissioner_leagues.json");
	}};
	
	public String getYahooUserGuid() {
		
		if (userGuid == null) {
			Response response = userApi.getUserInfo();
			UserDto userDto = response.readEntity(UserDto.class);
			userGuid = userDto.getSub();
			return userGuid;
		} else {
			return userGuid;
		}

	}
	
	public String getYahooUserName() {
		
		if (userName == null) {
			Response response = userApi.getUserInfo();
			UserDto userDto = response.readEntity(UserDto.class);
			userName = userDto.getName();
			return userName;
		} else {
			return userName;
		}

	}
	
	public Long getCurrentYahooGame() throws AuthorizationFailedException, RuntimeException {
		
		return Long.valueOf(this.getYahooGameId(NFL));
		
	}
	
	public Long getCurrentSeason() throws AuthorizationFailedException, RuntimeException {
		
		return Long.valueOf(this.getYahooGameSeason(NFL));
		
	}	

	public Long getPriorYahooGame() throws AuthorizationFailedException, RuntimeException {
		
		Long cs = this.getCurrentSeason();
		String pathParam = NFL+NFLSEASON+(cs-1);
		return Long.valueOf(this.getYahooGameId(pathParam));
		
	}
	
	public Long getPriorSeason() throws AuthorizationFailedException, RuntimeException {
		
		Long cs = this.getCurrentSeason();
		return (cs-1);
		
	}	
	
	private int getYahooGameId(String pathParam) throws AuthorizationFailedException, RuntimeException {
		
		int gameId = 0;
		String game = "game";
		String gameStrg = null;
		JsonObject gameObj = null;
		String userGuid = this.getYahooUserGuid();
		gameStrg = callYahooApis(GAMEURI+pathParam+URIFORMAT, userGuid, game);
		try {
			gameObj = new JsonParser().parse(gameStrg).getAsJsonObject();
			JsonElement element = gameObj.get("fantasy_content").getAsJsonObject().get("game").getAsJsonArray().get(0).getAsJsonObject().get("game_id");
			gameId = element.getAsInt();
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

		return gameId;
		
	}

	private int getYahooGameSeason(String pathParam) throws AuthorizationFailedException, RuntimeException {
		
		int gameSeason = 0;
		String game = "game";
		String gameStrg = null;
		JsonObject gameObj = null;
		String userGuid = this.getYahooUserGuid();
		gameStrg = callYahooApis(GAMEURI+pathParam+URIFORMAT, userGuid, game);
		try {
			gameObj = new JsonParser().parse(gameStrg).getAsJsonObject();
			JsonElement element = gameObj.get("fantasy_content").getAsJsonObject().get("game").getAsJsonArray().get(0).getAsJsonObject().get("season");
			gameSeason = element.getAsInt();
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

		return gameSeason;
		
	}
	
	public String getYahooData(String url, String user, String type) throws AuthorizationFailedException, RuntimeException {
		
		return this.callYahooApis(url, user, type);
		
	}

	@SuppressWarnings("static-access")
	private String callYahooApis(String url, String user, String type) throws AuthorizationFailedException, RuntimeException {

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
				if (response.getStatus() == 401) {
					throw new NotAuthorizedException(result);
				}
				client.close();
			} catch (NotAuthorizedException ex) {
				client.close();
				success = false;
				LOG.error("Yahoo getting {} request failed for user={}, url={} - {}", type, user, url, ex.getMessage());
				throw new AuthorizationFailedException(ex.getMessage());
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
					LOG.error("Yahoo getting {} request failed (exception) for user={}, url={} - {}. Falling back to dummy data.", type, user, url, ex.getMessage());
					throw new RuntimeException(ex.getMessage());
//					try {
//						result = new String(Files.readAllBytes(Paths.get(YAHOO_DUMMY_DATA_ROOT + dummyData.get(type))));
//					} catch (IOException exe){
//						LOG.info("SCAD had a problem getting dummy data for {}", type);
//					}
				}
			} else {
				LOG.error("Yahoo getting {} request failed for user={}, url={}", type, user, url);
				throw new RuntimeException(result);
			}
		}
		
		return result;

	}

}
