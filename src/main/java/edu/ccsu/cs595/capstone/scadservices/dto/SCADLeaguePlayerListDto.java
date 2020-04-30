package edu.ccsu.cs595.capstone.scadservices.dto;

import java.util.LinkedList;
import java.util.List;

public class SCADLeaguePlayerListDto {
	
	private List<SCADLeaguePlayerDto> scadLeaguePlayers = new LinkedList<SCADLeaguePlayerDto>();
	
	public List<SCADLeaguePlayerDto> getScadLeaguePlayers() {
		return scadLeaguePlayers;
	}

	public void setScadLeaguePlayers(List<SCADLeaguePlayerDto> scadLeaguePlayers) {
		this.scadLeaguePlayers = scadLeaguePlayers;
	}

	@Override
	public String toString() {
		return "SCADLeaguePlayerListDto [scadLeaguePlayers=" + scadLeaguePlayers + "]";
	}
	

}
