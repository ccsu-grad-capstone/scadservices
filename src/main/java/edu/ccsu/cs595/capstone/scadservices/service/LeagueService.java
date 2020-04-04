package edu.ccsu.cs595.capstone.scadservices.service;

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

import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.util.YahooClientBuilder;

@Stateless
public class LeagueService {

	private static final Logger LOG = LoggerFactory.getLogger(LeagueService.class);

	@Inject
	YahooClientBuilder yahoo;
	
	private static final String BASE_URI = "https://fantasysports.yahooapis.com/fantasy/v2/";
	private static final String GAME_KEY = "390";

	public String getUserAllLeagues() throws AuthorizationFailedException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		String result = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/users;use_login=1/games;game_keys=" + GAME_KEY + "/leagues?format=json";
		String rawYahooData = yahoo.getYahooLeagueData(url, userGuid, "leagues");
		try {
			if (Objects.nonNull(rawYahooData)) {
				yahooLeagueObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = this.formatLeaguesData(yahooLeagueObj);
			}
		} catch (Exception ex) {
			LOG.error("Leagues Json parsing error for userGuid={} - {}", userGuid, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting all leagues for userGuid={}, process took {}ms.", userGuid, (e - s));
		return result;

	}

	public String getUserAllLeaguesAsCommissioner() throws AuthorizationFailedException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		String result = null;
		String rawYahooData = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/users;use_login=1/games;game_keys=" + GAME_KEY + "/leagues?format=json";
		rawYahooData = yahoo.getYahooLeagueData(url, userGuid, "leagues");
		try {
			if (Objects.nonNull(rawYahooData)) {
				yahooLeagueObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = this.getLeaguesDataAsCommissioner(yahooLeagueObj);
			}
		} catch (Exception ex) {
			LOG.error("Leagues Json parsing error for userGuid={} - {}", userGuid, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting all leagues for userGuid={}, process took {}ms.", userGuid, (e - s));
		return result;

	}

	public String getUserLeague(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		String result = null;
		String rawYahooData = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/league/" + GAME_KEY + ".l." + leagueId + "?format=json";
		rawYahooData = yahoo.getYahooLeagueData(url, userGuid, "league");
		try {
			if (Objects.nonNull(rawYahooData)) {
				yahooLeagueObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = this.formatLeagueData(yahooLeagueObj);
			}
		} catch (Exception ex) {
			LOG.error("Leagues Json parsing error for userGuid={} - {}", userGuid, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting all leagues for userGuid={}, process took {}ms.", userGuid, (e - s));
		return result;

	}

	public String getUserLeagueTeams(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		String userId = yahoo.getYahooUserGuid();
		String url = BASE_URI + "league/" + GAME_KEY + ".l." + leagueId + "/teams?format=json";
		String rawYahooResult = yahoo.getYahooLeagueData(url, userId, "teams");
		String result = null;
		try {
			if (Objects.nonNull(rawYahooResult)) {
				JsonObject jsonObj = new JsonParser().parse(rawYahooResult).getAsJsonObject();
				result = formatTeamsData(jsonObj);
			}
		} catch (Exception e) {
			LOG.error("Error getting teams for userGuid = {} - {}", userId, e.getMessage());
		}

		return result;
	}

	public String getUserLeagueSettings(Long leagueId) {
		String userId = yahoo.getYahooUserGuid();
		String url = BASE_URI + "league/" + GAME_KEY + ".l." + leagueId + "/settings?format=json";
		String result = null;

		try {
			String rawYahooData = yahoo.getYahooLeagueData(url, userId, "settings");
			if (Objects.nonNull(rawYahooData)) {
				JsonObject jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = formatSettingsData(jsonObj);
			}
		} catch (Exception e) {
			LOG.error("Error getting teams for userGuid = {} - {}", userId, e.getMessage());
		}

		return result;
	}

	public String getUserLeagueStandings(Long leagueId) {
		String userId = yahoo.getYahooUserGuid();
		String url = BASE_URI + "league/" + GAME_KEY + ".l." + leagueId + "/standings?format=json";
		String result = null;
		try {
			String rawYahooData = yahoo.getYahooLeagueData(url, userId, "standings");
			if (Objects.nonNull(rawYahooData)) {
				JsonObject jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = formatStandingsData(jsonObj);
			}
		} catch (Exception e) {
			LOG.error("Error getting teams for userGuid = {} - {}", userId, e.getMessage());
		}

		return result;
	}

	public String getUserLeagueTeamAndRoster(Long leagueId, Long teamId, Long week) {
		String userId = yahoo.getYahooUserGuid();
		String url = BASE_URI + "team/" + GAME_KEY + ".l." + leagueId + ".t." + teamId + "/roster";
		if (week != null) {
			url += ";week=" + week;
		}
		url += "?format=json";

		String result = null;
		try {
			String rawYahooData = yahoo.getYahooLeagueData(url, userId, "roster");
			if (Objects.nonNull(rawYahooData)) {
				JsonObject jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = formatRosterData(jsonObj);
			}
		} catch (Exception e) {
			LOG.error("Error getting rosters for userGuid = {} - {}", userId, e.getMessage());
		}

		return result;
	}

	public String getUserLeaguePlayers(Long leagueId) {
		String userId = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/league/" + GAME_KEY + ".l." + leagueId + "/teams/roster?format=json";

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



	// Helper methods

	private String formatLeaguesData(JsonObject leaguesObj) throws AuthorizationFailedException, RuntimeException {

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

	private String formatLeagueData(JsonObject leagueObj) throws AuthorizationFailedException, RuntimeException {

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

	private String formatStandingsData(JsonObject rawYahooObj) {
		String result = null;
		if (Objects.nonNull(rawYahooObj)) {
			try {
				JsonElement error = rawYahooObj.get("error");
				if (Objects.isNull(error)) {
					JsonObject teams = rawYahooObj.get("fantasy_content").getAsJsonObject().get("league").getAsJsonArray().get(1).getAsJsonObject().get("standings").getAsJsonArray().get(0).getAsJsonObject().get("teams").getAsJsonObject();
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
					result = "{\"standings\":" + newTeams.toString() + "}";
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
				JsonArray newPlayers = new JsonArray();
				JsonObject teams = rawYahooObj.get("fantasy_content").getAsJsonObject().get("league").getAsJsonArray().get(1).getAsJsonObject().get("teams").getAsJsonObject();
				for (Integer j = 0; j < 12; j++) {
					JsonObject roster = teams.get(j.toString()).getAsJsonObject().get("team").getAsJsonArray().get(1).getAsJsonObject().get("roster").getAsJsonObject();
					JsonObject players = roster.get("0").getAsJsonObject().get("players").getAsJsonObject();
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
				}
				result = "{\"players\":" + newPlayers.toString() + "}";
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

	private String getLeaguesDataAsCommissioner(JsonObject leaguesObj) throws AuthorizationFailedException, RuntimeException {

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
						if (validateAsCommissioner(league.get(0))) {
							newleagues.add(league.get(0));
						}
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

	private String formatRosterData(JsonObject rawYahooObj) {
		String result = null;
		if (Objects.nonNull(rawYahooObj)) {
			try {
				JsonObject roster = rawYahooObj.get("fantasy_content").getAsJsonObject().get("team").getAsJsonArray().get(1).getAsJsonObject().get("roster").getAsJsonObject();
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

	private boolean validateAsCommissioner (JsonElement league) {
		boolean result = false;
		return result;
	}

}
