package edu.ccsu.cs595.capstone.scadservices;

public class EndpointConstants {

	public static final String APPLICATION_PATH = "/api/";
	public static final String USER = "user";
	public static final String LEAGUE = "yahoo/league";
	public static final String DASHBOARD = "scad/dashboard";
	public static final String SCADLEAGUE = "scad/league";
	public static final String SCADLEAGUETEAM = "/team";
	public static final String SCADLEAGUEPLAYER = "/player";

	public static final String ID = "id";
	public static final String LEAGUEID = "leagueId";
	public static final String TEAMID = "teamId";
	public static final String PLAYERID = "playerId";
	public static final String RESOURCE_ID = "The ID of the resource.";
	public static final String LEAGUE_RESOURCE_ID = "The League ID of the resource.";
	public static final String TEAM_RESOURCE_ID = "The Team ID of the resource.";
	public static final String PLAYER_RESOURCE_ID = "The Player ID of the resource.";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	
	public static final String REGEMAIL = "Registered email address.";
	public static final String IDTOKEN = "Yahoo id_token.";
	public static final String ID_TOKEN = "id_token";
	public static final String PWD = "Login password.";
	
	public static final String POSTPROPOSED = "The proposed state of the resource "
		        + "(you must supply all required fields).";
	
	public static final String PUTPROPOSED = "The proposed state of the resource "
	        + "(you may supply only the ID and the changes).";

}
