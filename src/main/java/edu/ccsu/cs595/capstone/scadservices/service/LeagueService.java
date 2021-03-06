package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.Map;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.ccsu.cs595.capstone.scadservices.exception.EmptyPlayersArrayException;
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
	
	private static final Long HARDCODE_KEY = 22351L;
	private static final String BASE_URI = "https://fantasysports.yahooapis.com/fantasy/v2";
	private static final String BASE_URI_FORMAT = "?format=json";

	public String getUserLeaguesBySeason() throws AuthorizationFailedException, RuntimeException {


		Long yahooGameKey = yahoo.getCurrentYahooGame();
		String result = this.getUserLeaguesBySeason(yahooGameKey);
		return result;

	}
	
	public String getUserLeaguesByPriorSeason() throws AuthorizationFailedException, RuntimeException {


		Long yahooGameKey = yahoo.getPriorYahooGame();
		String result = this.getUserLeaguesBySeason(yahooGameKey);
		return result;

	}
	
	private String getUserLeaguesBySeason(Long yahooGameKey) throws AuthorizationFailedException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		String result = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/users;use_login=1/games;game_keys=" + yahooGameKey + "/leagues" + BASE_URI_FORMAT;
		String rawYahooData = yahoo.getYahooData(url, userGuid, "leagues");
		try {
			if (Objects.nonNull(rawYahooData)) {
				yahooLeagueObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = this.formatLeaguesData(yahooLeagueObj);
				if (result.contains("ERROR:")) {
					this.getUserLeaguesByPriorSeason();
				}
			}
		} catch (Exception ex) {
			LOG.error("Leagues Json parsing error for userGuid={} - {}", userGuid, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting all leagues for userGuid={} and process took {}ms.", userGuid, (e - s));
		return result;

	}

	public String getUserLeaguesAsCommissionerBySeason() throws AuthorizationFailedException, RuntimeException {

		Long yahooGameKey = yahoo.getCurrentYahooGame();
		String result = this.getUserLeaguesAsCommissionerBySeason(yahooGameKey);
		return result;

	}
	
	public String getUserLeaguesAsCommissionerByPriorSeason() throws AuthorizationFailedException, RuntimeException {

		Long yahooGameKey = yahoo.getPriorYahooGame();
		String result = this.getUserLeaguesAsCommissionerBySeason(yahooGameKey);
		return result;

	}
	
	private String getUserLeaguesAsCommissionerBySeason(Long yahooGameKey) throws AuthorizationFailedException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		String result = null;
		String rawYahooData = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/users;use_login=1/games;game_keys=" + yahooGameKey + "/leagues/teams" + BASE_URI_FORMAT;
		rawYahooData = yahoo.getYahooData(url, userGuid, "commissionerLeagues");
		try {
			if (Objects.nonNull(rawYahooData)) {
				yahooLeagueObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = this.formatLeaguesDataAsCommissioner(yahooLeagueObj);
				if (result.contains("ERROR:")) {
					this.getUserLeaguesAsCommissionerByPriorSeason();
				}
			}
		} catch (Exception ex) {
			LOG.error("Commissioner Leagues Json parsing error for userGuid={} - {}", userGuid, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting all Commissioner leagues for userGuid={} and process took {}ms.", userGuid, (e - s));
		return result;

	}
	
	public String getYahooLeague(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		Long yahooGameKey = yahoo.getCurrentYahooGame();
		String result = this.getYahooLeague(leagueId,yahooGameKey);
		return result;

	}

	public String getYahooPriorLeague(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		Long yahooGameKey = yahoo.getPriorYahooGame();
		String result = this.getYahooLeague(leagueId,yahooGameKey);
		return result;

	}
	
	private String getYahooLeague(Long leagueId, Long yahooGameKey) throws AuthorizationFailedException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		String result = null;
		String rawYahooData = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/league/" + yahooGameKey + ".l." + leagueId + BASE_URI_FORMAT;
		rawYahooData = yahoo.getYahooData(url, userGuid, "league");
		try {
			if (Objects.nonNull(rawYahooData)) {
				yahooLeagueObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = this.formatLeagueData(yahooLeagueObj);
			}
		} catch (Exception ex) {
			LOG.error("League Json parsing error for userGuid={} - {}", userGuid, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting league for userGuid={} and leagueId={} and process took {}ms.", userGuid, leagueId, (e - s));
		return result;

	}
	
	public String getYahooLeagueTeams(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		Long yahooGameKey = yahoo.getCurrentYahooGame();
		String result = this.getYahooLeagueTeams(leagueId, yahooGameKey);
		return result;
	}

	public String getYahooPriorLeagueTeams(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		Long yahooGameKey = yahoo.getPriorYahooGame();
		String result = this.getYahooLeagueTeams(leagueId, yahooGameKey);
		return result;
	}

	private String getYahooLeagueTeams(Long leagueId, Long yahooGameKey) throws AuthorizationFailedException, RuntimeException {
		String userId = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/league/" + yahooGameKey + ".l." + leagueId + "/teams" + BASE_URI_FORMAT;
		String rawYahooResult = yahoo.getYahooData(url, userId, "teams");
		String result = null;
		try {
			if (Objects.nonNull(rawYahooResult)) {
				JsonObject jsonObj = new JsonParser().parse(rawYahooResult).getAsJsonObject();
				result = formatTeamsData(jsonObj);
			}
		} catch (Exception e) {
			LOG.error("Error getting teams for userGuid={}, leagueId={},  - {}", userId, leagueId, e.getMessage());
		}
		return result;
	}

	public String getYahooLeagueSettings(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		Long yahooGameKey = yahoo.getCurrentYahooGame();
		String result = this.getYahooLeagueSettings(leagueId, yahooGameKey);
		return result;
	}

	public String getYahooPriorLeagueSettings(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		Long yahooGameKey = yahoo.getPriorYahooGame();
		String result = this.getYahooLeagueSettings(leagueId, yahooGameKey);
		return result;
	}
	
	private String getYahooLeagueSettings(Long leagueId, Long yahooGameKey) throws AuthorizationFailedException, RuntimeException {
		String userId = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/league/" + yahooGameKey + ".l." + leagueId + "/settings" + BASE_URI_FORMAT;
		String result = null;

		try {
			String rawYahooData = yahoo.getYahooData(url, userId, "settings");
			if (Objects.nonNull(rawYahooData)) {
				JsonObject jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = formatSettingsData(jsonObj);
			}
		} catch (Exception e) {
			LOG.error("Error getting league settings for userGuid={}, leagueId={}, - {}", userId, leagueId, e.getMessage());
		}

		return result;
	}

	public String getYahooLeagueStandings(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		Long yahooGameKey = yahoo.getCurrentYahooGame();
		String result = this.getYahooLeagueStandings(leagueId, yahooGameKey);
		return result;
	}

	public String getYahooPriorLeagueStandings(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		Long yahooGameKey = yahoo.getPriorYahooGame();
		String result = this.getYahooLeagueStandings(leagueId, yahooGameKey);
		return result;
	}

	private String getYahooLeagueStandings(Long leagueId, Long yahooGameKey) throws AuthorizationFailedException, RuntimeException {
		String userId = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/league/" + yahooGameKey + ".l." + leagueId + "/standings" + BASE_URI_FORMAT;
		String result = null;
		try {
			String rawYahooData = yahoo.getYahooData(url, userId, "standings");
			if (Objects.nonNull(rawYahooData)) {
				JsonObject jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = formatStandingsData(jsonObj);
			}
		} catch (Exception e) {
			LOG.error("Error getting standing for userGuid={}, leagueId={}, - {}", userId, leagueId, e.getMessage());
		}

		return result;
	}
	
	public String getYahooLeagueTeamAndRoster(Long leagueId, Long teamId, Long week) throws AuthorizationFailedException, RuntimeException {
		Long yahooGameKey = yahoo.getCurrentYahooGame();
		String result = this.getYahooLeagueTeamAndRoster(leagueId, teamId, week, yahooGameKey);
		return result;
	}
	
	public String getYahooPriorLeagueTeamAndRoster(Long leagueId, Long teamId, Long week) throws AuthorizationFailedException, RuntimeException {
		Long yahooGameKey = yahoo.getPriorYahooGame();
		String result = this.getYahooLeagueTeamAndRoster(leagueId, teamId, week, yahooGameKey);
		return result;
	}	

	private String getYahooLeagueTeamAndRoster(Long leagueId, Long teamId, Long week, Long yahooGameKey) throws AuthorizationFailedException, RuntimeException {
		String userId = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/team/" + yahooGameKey + ".l." + leagueId + ".t." + teamId + "/roster";
		if (week != null) {
			url += ";week=" + week;
		}
		url += BASE_URI_FORMAT;

		String result = null;
		JsonObject jsonObj;
		String rawYahooData = yahoo.getYahooData(url, userId, "roster");
		try {
			if (Objects.nonNull(rawYahooData)) {
				jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = formatTeamAndRosterData(jsonObj);
			}
		} catch (EmptyPlayersArrayException exe) {
			LOG.error(exe.getMessage() + " Retrieving data from previous year...");
			url = BASE_URI + "/team/" + exe.getPreviousYearGameKey() + ".l." + exe.getPreviousYearLeagueId() + ".t." + teamId + "/roster";
			if (week != null) {
				url += ";week=" + week;
			}
			url += BASE_URI_FORMAT;

			rawYahooData = yahoo.getYahooData(url, userId, "roster");
			try {
				if (Objects.nonNull(rawYahooData)) {
					jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
					result = formatTeamAndRosterData(jsonObj);
				}
			} catch (Exception exe2) {
				// No players from previous year either.
				LOG.error("Tried to find players from previous year, but could not find them!");
			}
		} catch (Exception e) {
			LOG.error("Error getting rosters for userGuid={}, leagueId={}, teamId={}, week={}, - {}", userId, leagueId, teamId, week, e.getMessage());
		}

		return result;
	}

	public String getYahooLeaguePlayers(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		Long yahooGameKey = yahoo.getCurrentYahooGame();
		String result = this.getYahooLeaguePlayers(leagueId, yahooGameKey);
		return result;
	}
	
	public String getYahooPriorLeaguePlayers(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		Long yahooGameKey = yahoo.getPriorYahooGame();
		String result = this.getYahooLeaguePlayers(leagueId, yahooGameKey);
		return result;
	}

	private String getYahooLeaguePlayers(Long leagueId, Long yahooGameKey) throws AuthorizationFailedException, RuntimeException {
		String userId = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/league/" + yahooGameKey + ".l." + leagueId + "/teams/roster" + BASE_URI_FORMAT;

		String result = null;

		try {
			String rawYahooResult = yahoo.getYahooData(url, userId, "players");
			if (Objects.nonNull(rawYahooResult)) {
				JsonObject jsonObj = new JsonParser().parse(rawYahooResult).getAsJsonObject();
				result = formatPlayersData(jsonObj);
			}
		} catch (Exception e) {
			LOG.error("Error getting players for userGuid={}, leagueId={}, - {}", userId, leagueId, e.getMessage());
		}

		return result;
	}

	public String getYahooLeagueMyTeam(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		Long yahooGameKey = yahoo.getCurrentYahooGame();
		String result = this.getYahooLeagueMyTeam(leagueId, yahooGameKey);
		return result;

	}
	
	public String getYahooPriorLeagueMyTeam(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		Long yahooGameKey = yahoo.getPriorYahooGame();
		String result = this.getYahooLeagueMyTeam(leagueId, yahooGameKey);
		return result;

	}
	
	private String getYahooLeagueMyTeam(Long leagueId, Long yahooGameKey) throws AuthorizationFailedException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		String result = null;
		String rawYahooData = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/users;use_login=1/games;game_keys=" + yahooGameKey + "/leagues;league_keys=" + yahooGameKey + ".l." + leagueId + "/teams" + BASE_URI_FORMAT;
		rawYahooData = yahoo.getYahooData(url, userGuid, "myTeam");
		try {
			if (Objects.nonNull(rawYahooData)) {
				yahooLeagueObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = this.formatLeagueMyTeam(yahooLeagueObj);
			}
		} catch (Exception ex) {
			LOG.error("My Team Json parsing error for userGuid={}, leagueId={}, - {}", userGuid, leagueId, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting my teams for for userGuid={}, leagueId={} and process took {}ms.", userGuid, leagueId, (e - s));
		return result;

	}

	public String getYahooLeagueMyPlayers(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		String result = null;
		String yahooLeagueMyTeamStrg = this.getYahooLeagueMyTeam(leagueId);
		Long teamId = new JsonParser().parse(yahooLeagueMyTeamStrg).getAsJsonObject().get("team_id").getAsLong();
		result = this.getYahooLeagueMyPlayers(leagueId, teamId);
		return result;

	}
	
	public String getYahooLeagueMyPlayers(Long leagueId, Long teamId) throws AuthorizationFailedException, RuntimeException {

		Long yahooGameKey = yahoo.getCurrentYahooGame();
		String result = this.getYahooLeagueMyPlayers(leagueId, teamId, yahooGameKey);
		return result;

	}
	
	public String getYahooPriorLeagueMyPlayers(Long leagueId, Long teamId) throws AuthorizationFailedException, RuntimeException {

		Long yahooGameKey = yahoo.getPriorYahooGame();
		String result = this.getYahooLeagueMyPlayers(leagueId, teamId, yahooGameKey);
		return result;

	}
	
	private String getYahooLeagueMyPlayers(Long leagueId, Long teamId, Long yahooGameKey) throws AuthorizationFailedException, RuntimeException {

		Long s, e;
		s = System.currentTimeMillis();
		String result = null;
		String rawYahooData = null;
		JsonObject yahooLeagueObj = null;
		String userGuid = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/users;use_login=1/games;game_keys=" + yahooGameKey + "/leagues;league_keys=" + yahooGameKey + ".l." + leagueId + "/teams;team_key=" + + yahooGameKey + ".l." + leagueId + ".t." + teamId + "/players" + BASE_URI_FORMAT;
		rawYahooData = yahoo.getYahooData(url, userGuid, "myPlayers");
		try {
			if (Objects.nonNull(rawYahooData)) {
				yahooLeagueObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = this.formatLeagueMyTeamMyPlayers(yahooLeagueObj);
			}
		} catch (EmptyPlayersArrayException exe) {
			LOG.error(exe.getMessage() + " Retrieving data from previous year...");
			url = BASE_URI + "/users;use_login=1/games;game_keys=" + exe.getPreviousYearGameKey() + "/leagues;league_keys=" + exe.getPreviousYearGameKey() + ".l." + exe.getPreviousYearLeagueId() + "/teams;team_key=" + exe.getPreviousYearGameKey() + ".l." + exe.getPreviousYearLeagueId() + ".t." + teamId + "/players" + BASE_URI_FORMAT;
			rawYahooData = yahoo.getYahooData(url, userGuid, "myPlayers");
			try {
				if (Objects.nonNull(rawYahooData)) {
					yahooLeagueObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
					result = this.formatLeagueMyTeamMyPlayers(yahooLeagueObj);
				}
			} catch (Exception exe2) {
				// No players from previous year either.
				LOG.error("Tried to find players from previous year, but could not find them!");
			}

		} catch (Exception ex) {
			LOG.error("My Players Json parsing error for userGuid={}, leagueId={}, teamId={}, - {}", userGuid, leagueId, teamId, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting my players for userGuid={}, leagueId={}, teamId={} and process took {}ms.", userGuid, leagueId, teamId, (e - s));
		return result;

	}
	
	public String getYahooLeagueTeamPlayers(Long leagueId, Long teamId) throws AuthorizationFailedException, RuntimeException {
		
		Long yahooGameKey = yahoo.getCurrentYahooGame();
		String result = this.getYahooLeagueTeamPlayers(leagueId, teamId, yahooGameKey);
		return result;
	}
	
	public String getYahooPriorLeagueTeamPlayers(Long leagueId, Long teamId) throws AuthorizationFailedException, RuntimeException {
		
		Long yahooGameKey = yahoo.getPriorYahooGame();
		String result = this.getYahooLeagueTeamPlayers(leagueId, teamId, yahooGameKey);
		return result;
	}

	private String getYahooLeagueTeamPlayers(Long leagueId, Long teamId, Long yahooGameKey) throws AuthorizationFailedException, RuntimeException {
		
		String userId = yahoo.getYahooUserGuid();
		String url = BASE_URI + "/team/" + yahooGameKey + ".l." + leagueId + ".t." + teamId + "/players" + BASE_URI_FORMAT;

		String result = null;
		JsonObject jsonObj;
		String rawYahooData = yahoo.getYahooData(url, userId, "teamPlayers");
		try {
			if (Objects.nonNull(rawYahooData)) {
				jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
				result = formatTeamAndPlayersData(jsonObj);
			}
		} catch (EmptyPlayersArrayException exe) {
			LOG.error(exe.getMessage() + " Retrieving data from previous year...");
			url = BASE_URI + "/team/" + exe.getPreviousYearGameKey() + ".l." + exe.getPreviousYearLeagueId() + ".t." + teamId + "/players" + BASE_URI_FORMAT;
			rawYahooData = yahoo.getYahooData(url, userId, "teamPlayers");
			try {
				if (Objects.nonNull(rawYahooData)) {
					jsonObj = new JsonParser().parse(rawYahooData).getAsJsonObject();
					result = formatTeamAndPlayersData(jsonObj);
				}
			} catch (Exception exe2) {
				// No players from previous year either.
				LOG.error("Tried to find players from previous year, but could not find them!");
			}
		} catch (Exception e) {
			LOG.error("Error getting teamPlayers for userGuid={}, leagueId={}, teamId={}, - {}", userId, leagueId, teamId, e.getMessage());
		}

		return result;
	}

	// Helper methods

	private String formatLeaguesData(JsonObject leaguesObj) throws RuntimeException {

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

	private String formatLeagueData(JsonObject leagueObj) throws RuntimeException {

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
				JsonObject teams = rawYahooObj.get("fantasy_content").getAsJsonObject().get("league").getAsJsonArray().get(1).getAsJsonObject().get("standings").getAsJsonArray().get(0).getAsJsonObject().get("teams").getAsJsonObject();
				JsonArray newTeams = new JsonArray();
				for (Integer i = 0; i < teams.get("count").getAsInt(); i++) {
					JsonObject newTeam = new JsonObject();

					//There's a double array here, which is why we use outerTeam and team
					JsonArray outerTeam = teams.get(i.toString()).getAsJsonObject().get("team").getAsJsonArray();
					JsonArray team = outerTeam.get(0).getAsJsonArray();
					for (JsonElement x : team) {
						if (x.isJsonObject()) {
							for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
								newTeam.add(entry.getKey(), entry.getValue());
							}
						}
					}
					newTeam.add("team_points", outerTeam.get(1).getAsJsonObject());
					newTeam.add("team_standings", outerTeam.get(2).getAsJsonObject());
					newTeams.add(newTeam);
				}
				result = "{\"standings\":" + newTeams.toString() + "}";
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
				JsonArray league = rawYahooObj.get("fantasy_content").getAsJsonObject().get("league").getAsJsonArray();
				String renew = league.get(0).getAsJsonObject().get("renew").getAsString();
				String previousYearGameKey = "";
				String previousYearLeagueId = "";
				if (!renew.isEmpty()) {
					String[] temp = renew.split("_");
					previousYearGameKey = temp[0];
					previousYearLeagueId = temp[1];
				}
				JsonObject teams = league.get(1).getAsJsonObject().get("teams").getAsJsonObject();
				for (Integer j = 0; j < 12; j++) {
					JsonArray team = teams.get(j.toString()).getAsJsonObject().get("team").getAsJsonArray();
					JsonObject roster = team.get(1).getAsJsonObject().get("roster").getAsJsonObject();
					JsonElement playersLocation = roster.get("0").getAsJsonObject().get("players");
					JsonObject players;
					if (playersLocation.isJsonArray() && ((JsonArray) playersLocation).size() == 0) { // Players array is blank
						String teamId = team.get(0).getAsJsonArray().get(1).getAsJsonObject().get("team_id").getAsString();
						String url = BASE_URI + "/team/" + previousYearGameKey+ ".l." + previousYearLeagueId + ".t." + teamId + "/roster" + BASE_URI_FORMAT;
						JsonObject payload = new JsonParser().parse(yahoo.getYahooData(url, "formatter", "roster")).getAsJsonObject();
						players = payload.get("fantasy_content").getAsJsonObject().get("team").getAsJsonArray().get(1).getAsJsonObject().get("roster").getAsJsonObject().get("0").getAsJsonObject().get("players").getAsJsonObject();
					} else {
						players = playersLocation.getAsJsonObject();
					}

					for (Integer i = 0; i < players.get("count").getAsInt(); i++) {
						JsonObject newPlayer = new JsonObject();
						JsonArray playerArray = players.get(i.toString()).getAsJsonObject().get("player").getAsJsonArray();
						JsonArray player = playerArray.get(0).getAsJsonArray();
						for (JsonElement x : player) {
							// Filter out blank JSON arrays
							if (x.isJsonObject()) {
								for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
									newPlayer.add(entry.getKey(), entry.getValue());
								}
							}
						}

						if (playerArray.size() > 1) {
							JsonObject newSelectedPosition = new JsonObject();

							JsonArray selectedPosition = playerArray.get(1).getAsJsonObject().get("selected_position").getAsJsonArray();
							for (JsonElement x : selectedPosition) {
								if (x.isJsonObject()) {
									for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
										newSelectedPosition.add(entry.getKey(), entry.getValue());
									}
								}
							}
							newPlayer.add("selected_position", newSelectedPosition);
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

	private String formatLeaguesDataAsCommissioner(JsonObject leaguesObj) throws RuntimeException {

		String result = null;

		if (Objects.nonNull(leaguesObj)) {
			try {
				JsonElement error = leaguesObj.get("error");
				if (Objects.isNull(error)) {
					JsonObject leagues = leaguesObj.get("fantasy_content").getAsJsonObject().get("users").getAsJsonObject().get("0").getAsJsonObject().get("user").getAsJsonArray().get(1).getAsJsonObject().get("games").getAsJsonObject().get("0").getAsJsonObject().get("game").getAsJsonArray().get(1).getAsJsonObject().get("leagues").getAsJsonObject();
					JsonArray commissLeagues = new JsonArray();
					for (Integer i = 0; i < leagues.get("count").getAsInt(); i++) {
						JsonArray league = leagues.get(i.toString()).getAsJsonObject().get("league").getAsJsonArray();
						Long leagueId = league.get(0).getAsJsonObject().get("league_id").getAsLong();
						if (leagueId.equals(HARDCODE_KEY)) {
							commissLeagues.add(league.get(0).getAsJsonObject());
						} else {
							JsonArray managers = league.get(1).getAsJsonObject().get("teams").getAsJsonObject().get("0").getAsJsonObject().get("team").getAsJsonArray().get(0).getAsJsonArray().get(19).getAsJsonObject().get("managers").getAsJsonArray();
							for (JsonElement manager : managers) {
								JsonObject manObj = ((JsonObject) manager).get("manager").getAsJsonObject();
								if (manObj.keySet().contains("is_commissioner")
										&& manObj.get("is_commissioner").getAsString().equals("1")
										&& manObj.keySet().contains("is_current_login")
										&& manObj.get("is_current_login").getAsString().equals("1")) {
									commissLeagues.add(league.get(0).getAsJsonObject());
								}
							}
						}
					}
					result = "{\"commissionerLeagues\":" + commissLeagues.toString() + "}";
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

	private String formatTeamAndRosterData(JsonObject rawYahooObj) throws EmptyPlayersArrayException {
		String result = null;
		if (Objects.nonNull(rawYahooObj)) {
			try {
				JsonArray team = rawYahooObj.get("fantasy_content").getAsJsonObject().get("team").getAsJsonArray();
				JsonObject roster = team.get(1).getAsJsonObject().get("roster").getAsJsonObject();

				JsonElement playersLocation = roster.get("0").getAsJsonObject().get("players");
				if (playersLocation.isJsonArray() && ((JsonArray) playersLocation).size() == 0) { // Players array is blank
					String[] temp = team.get(0).getAsJsonArray().get(0).getAsJsonObject().get("team_key").getAsString().split("[.]");
					String gameKey = temp[0];
					String leagueId = temp[2];
					String url = BASE_URI + "/league/" + gameKey + ".l." + leagueId + BASE_URI_FORMAT;
					JsonObject leagueInfo = new JsonParser().parse(yahoo.getYahooData(url, "formatter", "league")).getAsJsonObject();
					String renew = leagueInfo.get("fantasy_content").getAsJsonObject().get("league").getAsJsonArray().get(0).getAsJsonObject().get("renew").getAsString();
					if (!renew.isEmpty()) {
						temp = renew.split("_");
						Long previousYearGameKey = Long.parseLong(temp[0]);
						Long previousYearLeagueId = Long.parseLong(temp[1]);
						EmptyPlayersArrayException exe = new EmptyPlayersArrayException();
						exe.setPreviousYearGameKey(previousYearGameKey);
						exe.setPreviousYearLeagueId(previousYearLeagueId);
						throw exe;
					}
				}

				//Format the roster
				JsonObject newRoster = new JsonObject();
				newRoster.add("coverage_type", roster.get("coverage_type"));
				newRoster.add("week", roster.get("week"));
				newRoster.add("is_editable", roster.get("is_editable"));
				JsonObject players = roster.get("0").getAsJsonObject().get("players").getAsJsonObject();
				JsonArray newPlayers = new JsonArray();
				for (Integer i = 0; i < players.get("count").getAsInt(); i++) {
					JsonObject newPlayer = new JsonObject();
					JsonArray playerArray = players.get(i.toString()).getAsJsonObject().get("player").getAsJsonArray();
					JsonArray player = playerArray.get(0).getAsJsonArray();
					for (JsonElement x : player) {
						// Filter out blank JSON arrays
						if (x.isJsonObject()) {
							for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
								newPlayer.add(entry.getKey(), entry.getValue());
							}
						}
					}

					if (playerArray.size() > 1) {
						JsonObject newSelectedPosition = new JsonObject();

						JsonArray selectedPosition = playerArray.get(1).getAsJsonObject().get("selected_position").getAsJsonArray();
						for (JsonElement x : selectedPosition) {
							if (x.isJsonObject()) {
								for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
									newSelectedPosition.add(entry.getKey(), entry.getValue());
								}
							}
						}
						newPlayer.add("selected_position", newSelectedPosition);
					}

					newPlayers.add(newPlayer);
				}
				newRoster.add("players", newPlayers);

				//Format the team and include the roster
				JsonObject newTeam = new JsonObject();
				for (JsonElement x : team.get(0).getAsJsonArray()) {
					if (x.isJsonObject()) {
						for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
							newTeam.add(entry.getKey(), entry.getValue());
						}
					}
				}
				newTeam.add("roster", newRoster);
				result = "{\"team\":" + newTeam.toString() + "}";
			} catch (EmptyPlayersArrayException exe) {
				throw exe;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return result;
	}
	
	private String formatLeagueMyTeam(JsonObject leaguesObj) throws RuntimeException {

		String result = null;

		if (Objects.nonNull(leaguesObj)) {
			try {
				JsonObject teams = leaguesObj.get("fantasy_content").getAsJsonObject().get("users").getAsJsonObject().get("0").getAsJsonObject().get("user").getAsJsonArray().get(1).getAsJsonObject().get("games").getAsJsonObject().get("0").getAsJsonObject().get("game").getAsJsonArray().get(1).getAsJsonObject().get("leagues").getAsJsonObject().get("0").getAsJsonObject().get("league").getAsJsonArray().get(1).getAsJsonObject().get("teams").getAsJsonObject();
				JsonObject newTeam = new JsonObject();
				for (Integer i = 0; i < teams.get("count").getAsInt(); i++) {
					JsonArray team = teams.get(i.toString()).getAsJsonObject().get("team").getAsJsonArray().get(0).getAsJsonArray();
					for (JsonElement x : team) {
						if (x.isJsonObject()) {
							for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
								newTeam.add(entry.getKey(), entry.getValue());
							}
						}
					}
				}
				result = newTeam.toString();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}

		}

		return result;

	}

	private String formatLeagueMyTeamMyPlayers(JsonObject leaguesObj) throws EmptyPlayersArrayException, RuntimeException {

		String result = null;

		if (Objects.nonNull(leaguesObj)) {
			try {
				JsonArray league = leaguesObj.get("fantasy_content").getAsJsonObject().get("users").getAsJsonObject().get("0").getAsJsonObject().get("user").getAsJsonArray().get(1).getAsJsonObject().get("games").getAsJsonObject().get("0").getAsJsonObject().get("game").getAsJsonArray().get(1).getAsJsonObject().get("leagues").getAsJsonObject().get("0").getAsJsonObject().get("league").getAsJsonArray();
				JsonObject teams = league.get(1).getAsJsonObject().get("teams").getAsJsonObject();
				JsonElement playersLocation = teams.get("0").getAsJsonObject().get("team").getAsJsonArray().get(1).getAsJsonObject().get("players");
				if (playersLocation.isJsonArray() && ((JsonArray) playersLocation).size() == 0) {
					String renew = league.get(0).getAsJsonObject().get("renew").getAsString();
					if (!renew.isEmpty()) {
						String[] temp = renew.split("_");
						Long previousYearGameKey = Long.parseLong(temp[0]);
						Long previousYearLeagueId = Long.parseLong(temp[1]);
						EmptyPlayersArrayException exe = new EmptyPlayersArrayException();
						exe.setPreviousYearGameKey(previousYearGameKey);
						exe.setPreviousYearLeagueId(previousYearLeagueId);
						throw exe;
					}
				}
				JsonObject players = playersLocation.getAsJsonObject();
				JsonArray newPlayers = new JsonArray();
				for (Integer i = 0; i < players.get("count").getAsInt(); i++) {
					JsonObject newPlayer = new JsonObject();
					JsonArray playerArray = players.get(i.toString()).getAsJsonObject().get("player").getAsJsonArray();
					JsonArray player = playerArray.get(0).getAsJsonArray();
					for (JsonElement x : player) {
						// Filter out blank JSON arrays
						if (x.isJsonObject()) {
							for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
								newPlayer.add(entry.getKey(), entry.getValue());
							}
						}
					}

					if (playerArray.size() > 1) {
						JsonObject newSelectedPosition = new JsonObject();

						JsonArray selectedPosition = playerArray.get(1).getAsJsonObject().get("selected_position").getAsJsonArray();
						for (JsonElement x : selectedPosition) {
							if (x.isJsonObject()) {
								for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
									newSelectedPosition.add(entry.getKey(), entry.getValue());
								}
							}
						}
						newPlayer.add("selected_position", newSelectedPosition);
					}

					newPlayers.add(newPlayer);
				}
				result = newPlayers.toString();
			} catch (EmptyPlayersArrayException exe) {
				throw exe;
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}

		return result;

	}
	
	private String formatTeamAndPlayersData(JsonObject rawYahooObj) throws EmptyPlayersArrayException {
		String result = null;
		if (Objects.nonNull(rawYahooObj)) {
			try {
				JsonArray team = rawYahooObj.get("fantasy_content").getAsJsonObject().get("team").getAsJsonArray();
				JsonElement playersLocation = team.get(1).getAsJsonObject().get("players");
				if (playersLocation.isJsonArray() && ((JsonArray) playersLocation).size() == 0) {
					String[] temp = team.get(0).getAsJsonArray().get(0).getAsJsonObject().get("team_key").getAsString().split("[.]");
					String gameKey = temp[0];
					String leagueId = temp[2];
					String url = BASE_URI + "/league/" + gameKey + ".l." + leagueId + BASE_URI_FORMAT;
					JsonObject leagueInfo = new JsonParser().parse(yahoo.getYahooData(url, "formatter", "league")).getAsJsonObject();
					String renew = leagueInfo.get("fantasy_content").getAsJsonObject().get("league").getAsJsonArray().get(0).getAsJsonObject().get("renew").getAsString();
					if (!renew.isEmpty()) {
						temp = renew.split("_");
						Long previousYearGameKey = Long.parseLong(temp[0]);
						Long previousYearLeagueId = Long.parseLong(temp[1]);
						EmptyPlayersArrayException exe = new EmptyPlayersArrayException();
						exe.setPreviousYearGameKey(previousYearGameKey);
						exe.setPreviousYearLeagueId(previousYearLeagueId);
						throw exe;
					}
				}
				JsonObject players = playersLocation.getAsJsonObject();
				JsonArray newPlayers = new JsonArray();
				for (Integer i = 0; i < players.get("count").getAsInt(); i++) {
					JsonObject newPlayer = new JsonObject();
					JsonArray playerArray = players.get(i.toString()).getAsJsonObject().get("player").getAsJsonArray();
					JsonArray player = playerArray.get(0).getAsJsonArray();
					for (JsonElement x : player) {
						// Filter out blank JSON arrays
						if (x.isJsonObject()) {
							for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
								newPlayer.add(entry.getKey(), entry.getValue());
							}
						}
					}

					if (playerArray.size() > 1) {
						JsonObject newSelectedPosition = new JsonObject();
						JsonArray selectedPosition = playerArray.get(1).getAsJsonObject().get("selected_position").getAsJsonArray();
						for (JsonElement x : selectedPosition) {
							if (x.isJsonObject()) {
								for (Map.Entry<String, JsonElement> entry : ((JsonObject) x).entrySet()) {
									newSelectedPosition.add(entry.getKey(), entry.getValue());
								}
							}
						}
						newPlayer.add("selected_position", newSelectedPosition);
					}

					newPlayers.add(newPlayer);
				}
				result = "{\"players\":" + newPlayers.toString() + "}";
			} catch (EmptyPlayersArrayException ex) {
				throw ex;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return result;
	}
	
}
