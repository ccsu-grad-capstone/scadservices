package edu.ccsu.cs595.capstone.scadservices.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.ccsu.cs595.capstone.scadservices.EndpointConstants;
import org.omg.SendingContext.RunTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.util.YahooClientBuilder;

@Stateless
public class LeagueService {

	private static final Logger LOG = LoggerFactory.getLogger(LeagueService.class);

	@Inject
	YahooClientBuilder yahoo;
	
	private static final String YAHOORESTURI_USERLEAGUES = "https://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games;game_keys=";
	private static final String YAHOORESTURI_USERLEAGUES_EXT = "/leagues?format=json";
	private static final String YAHOORESTURI_USERLEAGUE = "https://fantasysports.yahooapis.com/fantasy/v2/league/nfl.l.";
	private static final String YAHOORESTURI_USERLEAGUE_EXT = "?format=json";


	public String getUserAllLeagues() throws AuthorizationFailedException, RuntimeException, IOException {

		Long s, e;
		s = System.currentTimeMillis();
		String leagues = "leagues";
		String result = null;
		String yahooLeagueStrg = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = yahoo.getYahooUserGuid();
		Long yahooGameId = yahoo.getYahooGame();
		String leagueUrl = YAHOORESTURI_USERLEAGUES + yahooGameId + YAHOORESTURI_USERLEAGUES_EXT;
		yahooLeagueStrg = yahoo.getYahooLeagueData(leagueUrl, userGuid, leagues);
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

	public String getUserLeague(Long leagueId) throws  IOException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		String league = "league";
		String result;
		String rawYahooResult = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = yahoo.getYahooUserGuid();
		String leagueUrl = YAHOORESTURI_USERLEAGUE + leagueId + YAHOORESTURI_USERLEAGUE_EXT;
		try {
			rawYahooResult = yahoo.getYahooLeagueData(leagueUrl, userGuid, league);
		} catch (Exception ex) {
			LOG.error("Leagues Json parsing error for userGuid={} - {}", userGuid, ex.getMessage());
		}

		if (rawYahooResult == null) {
			byte[] dummyData = Files.readAllBytes(Paths.get(EndpointConstants.DUMMY_DATA_ROOT + "/leagueDummyData.json"));
			result = new String(dummyData, StandardCharsets.US_ASCII);
		} else {
			yahooLeagueObj = new JsonParser().parse(rawYahooResult).getAsJsonObject();
			result = this.getLeagueData(yahooLeagueObj);
		}

		e = System.currentTimeMillis();
		LOG.info("Getting all leagues for userGuid={}, process took {}ms.", userGuid, (e - s));
		return result;
	}

	public String getUserLeagueTeams(Long leagueId) throws IOException, RuntimeException {
		String userId = yahoo.getYahooUserGuid();
		String url = "https://fantasysports.yahooapis.com/fantasy/v2/league/nfl.l." + leagueId + "/teams?format=json";
		String rawYahooResult = null;
		String result;

		try {
			rawYahooResult = yahoo.getYahooLeagueData(url, userId, "teams");
		} catch (Exception e) {
			LOG.error("Error getting teams for userGuid = {} - {}", userId, e.getMessage());
		}

		JsonParser parser = new JsonParser();
		if (Objects.nonNull(rawYahooResult)) {
			result = formatTeamsData(parser.parse(rawYahooResult).getAsJsonObject());
		} else {
			byte[] dummyData = Files.readAllBytes(Paths.get(EndpointConstants.DUMMY_DATA_ROOT + "/teamsDummyData.json"));
			result = new String(dummyData, StandardCharsets.US_ASCII);
		}

		return result;
	}

	public String getUserLeagueSettings(Long leagueId) throws IOException, RuntimeException {
		String userId = yahoo.getYahooUserGuid();
		String url = "https://fantasysports.yahooapis.com/fantasy/v2/league/nfl.l." + leagueId + "/settings?format=json";
		String result = null;
		String rawYahooData = null;

		try {
			rawYahooData = yahoo.getYahooLeagueData(url, userId, "settings");
		} catch (Exception e) {
			LOG.error("Error getting teams for userGuid = {} - {}", userId, e.getMessage());
		}

		if (Objects.nonNull(rawYahooData)) {
			JsonObject jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
			result = formatSettingsData(jsonObj);
		} else {
			byte[] dummyData = Files.readAllBytes(Paths.get(EndpointConstants.DUMMY_DATA_ROOT + "/settingsDummyData.json"));
			result = new String(dummyData, StandardCharsets.US_ASCII);
		}

		return result;
	}

	public String getUserLeagueStandings(Long leagueId) throws IOException, RuntimeException {
		String userId = yahoo.getYahooUserGuid();
		String url = "https://fantasysports.yahooapis.com/fantasy/v2/league/nfl.l." + leagueId + "/standings?format=json";
		String result = null;
		String rawYahooData = null;
		try {
			rawYahooData = yahoo.getYahooLeagueData(url, userId, "standings");
		} catch (Exception e) {
			LOG.error("Error getting teams for userGuid = {} - {}", userId, e.getMessage());
		}

		if (Objects.nonNull(rawYahooData)) {
			JsonObject jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
			result = formatSettingsData(jsonObj);
		} else {
			byte[] dummyData = Files.readAllBytes(Paths.get(EndpointConstants.DUMMY_DATA_ROOT + "/standingsDummyData.json"));
			result = new String(dummyData, StandardCharsets.US_ASCII);
		}

		return result;
	}

	public String getUserLeagueTeamAndRoster(Long leagueId, Long teamId, Long week) throws IOException, RuntimeException {
		String userId = yahoo.getYahooUserGuid();
		String url = "https://fantasysports.yahooapis.com/fantasy/v2/team/nfl.l." + leagueId + ".t." + teamId + "/roster";
		if (week != null) {
			url += ";week=" + week;
		}
		url += "?format=json";

		String result;
		String rawYahooData = null;

		try {
			rawYahooData = yahoo.getYahooLeagueData(url, userId, "roster");
		} catch (Exception e) {
			LOG.error("Error getting rosters for userGuid = {} - {}", userId, e.getMessage());
		}

		if (Objects.nonNull(rawYahooData)) {
			JsonObject jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
			result = formatRosterData(jsonObj);
		} else {
			byte[] dummyData = Files.readAllBytes(Paths.get(EndpointConstants.DUMMY_DATA_ROOT + "/rosterDummyData.json"));
			result = new String(dummyData, StandardCharsets.US_ASCII);
		}

		return result;
	}

	public String getUserLeaguePlayers(Long leagueId) {
		String userId = yahoo.getYahooUserGuid();
		String url = "https://fantasysports.yahooapis.com/fantasy/v2/league/nfl.l." + leagueId;
		url += "/players?format=json";

		String result = null;

		try {
			String rawYahooResult = yahoo.getYahooLeagueData(url, userId, "players");
			if (Objects.nonNull(rawYahooResult)) {
				JsonObject jsonObj = new JsonParser().parse(rawYahooResult).getAsJsonObject();
				result = formatPlayersData(jsonObj);
			}
		} catch (Exception e) {
			LOG.error("Error getting players for userGuid = {} - {}", userId, e.getMessage());
		}

		return result;
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
	
	private String getLeagueData(JsonObject leagueObj) throws RuntimeException {

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

	private String formatTeamsData(JsonObject rawYahooObj) {
		String result = null;
		if (Objects.nonNull(rawYahooObj)) {
			try {
				JsonElement error = rawYahooObj.get("error");
				if (Objects.isNull(error)) {
					JsonObject teams = rawYahooObj.get("fantasy_content").getAsJsonObject().get("league").getAsJsonArray().get(1).getAsJsonObject().get("teams").getAsJsonObject();
					JsonArray newTeams = new JsonArray();
					for (Integer i = 0; i < teams.get("count").getAsInt(); i++) {
						JsonObject newTeam = new JsonObject();
						JsonArray team = teams.get(i.toString()).getAsJsonObject().get("team").getAsJsonArray().get(0).getAsJsonArray();
						for (JsonElement x : team) {
							if (x.isJsonObject()) {
								for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
									newTeam.add(entry.getKey(), entry.getValue());
								}
							}
						}
						newTeams.add(newTeam);
					}
					result = "{\"teams\":" + newTeams.toString() + "}";
				} else {
					LOG.error("SCAD Teams object has an error: {} ", error);
					result = "ERROR:" + error.toString();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return result;
	}

	private String formatPlayersData(JsonObject rawYahooObj) {
		String result = null;
		if (Objects.nonNull(rawYahooObj)) {
			try {
				JsonElement error = rawYahooObj.get("error");
				if (Objects.isNull(error)) {
					JsonObject players = rawYahooObj.get("fantasy_content").getAsJsonObject().get("league").getAsJsonArray().get(1).getAsJsonObject().get("players").getAsJsonObject();
					JsonArray newPlayers = new JsonArray();
					for (Integer i = 0; i < players.get("count").getAsInt(); i++) {
						JsonObject newPlayer = new JsonObject();
						JsonArray player = players.get(i.toString()).getAsJsonObject().get("player").getAsJsonArray().get(0).getAsJsonArray();
						for (JsonElement x : player) {
							// Filter out blank JSON arrays
							if (x.isJsonObject()) {
								for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
									newPlayer.add(entry.getKey(), entry.getValue());
								}
							}
						}
						newPlayers.add(newPlayer);
					}
					result = "{\"players\":" + newPlayers.toString() + "}";
				} else {
					LOG.error("SCAD Players object has an error: {} ", error);
					result = "ERROR:" + error.toString();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return result;
	}

	private String formatSettingsData(JsonObject rawYahooObj) {
		String result = null;
		if (Objects.nonNull(rawYahooObj)) {
			try {
				JsonElement error = rawYahooObj.get("error");
				if (Objects.isNull(error)) {
					JsonArray settings = rawYahooObj.get("fantasy_content").getAsJsonObject().get("league").getAsJsonArray().get(1).getAsJsonObject().get("settings").getAsJsonArray();
					result = "{\"settings\":" + settings.toString() + "}";
				} else {
					LOG.error("SCAD Teams object has an error: {} ", error);
					result = "ERROR:" + error.toString();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return result;
	}

	private String formatRosterData(JsonObject rawYahooObj) {
		String result = null;
		if (Objects.nonNull(rawYahooObj)) {
			try {
				JsonObject roster = rawYahooObj.get("fantasy_content").getAsJsonObject().get("roster").getAsJsonObject();
				JsonObject newRoster = new JsonObject();
				newRoster.add("coverage_type", roster.get("coverage_type"));
				newRoster.add("week", roster.get("week"));
				newRoster.add("is_editable", roster.get("is_editable"));
				JsonObject players = roster.get("0").getAsJsonObject().get("players").getAsJsonObject();
				JsonArray newPlayers = new JsonArray();
				for (Integer i = 0; i < players.get("count").getAsInt(); i++) {
					JsonObject newPlayer = new JsonObject();
					JsonArray player = players.get(i.toString()).getAsJsonObject().get("player").getAsJsonArray().get(0).getAsJsonArray();
					for (JsonElement x : player) {
						// Filter out blank JSON arrays
						if (x.isJsonObject()) {
							for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
								newPlayer.add(entry.getKey(), entry.getValue());
							}
						}
					}
					newPlayers.add(newPlayer);
				}
				newRoster.add("players", newPlayers);
				result = "{\"roster\":" + newRoster.toString() + "}";
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return result;
	}
}
