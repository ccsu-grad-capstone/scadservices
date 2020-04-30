package edu.ccsu.cs595.capstone.scadservices.dto;

import java.util.LinkedList;
import java.util.List;

public class SCADLeagueTeamListDto {
	
	private List<SCADLeagueTeamDto> scadLeagueTeams = new LinkedList<SCADLeagueTeamDto>();
	
	public List<SCADLeagueTeamDto> getScadLeagueTeams() {
		return scadLeagueTeams;
	}

	public void setScadLeagueTeams(List<SCADLeagueTeamDto> scadLeagueTeams) {
		this.scadLeagueTeams = scadLeagueTeams;
	}

	@Override
	public String toString() {
		return "SCADLeagueTeamListDto [scadLeagueTeams=" + scadLeagueTeams + "]";
	}
	

}
