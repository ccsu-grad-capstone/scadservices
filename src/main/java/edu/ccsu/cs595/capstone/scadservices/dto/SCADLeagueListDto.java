package edu.ccsu.cs595.capstone.scadservices.dto;

import java.util.LinkedList;
import java.util.List;

public class SCADLeagueListDto {
	
	private List<SCADLeagueDto> scadLeagues = new LinkedList<SCADLeagueDto>();
	
	public List<SCADLeagueDto> getScadLeagues() {
		return scadLeagues;
	}

	public void setScadLeagues(List<SCADLeagueDto> scadLeagues) {
		this.scadLeagues = scadLeagues;
	}

	@Override
	public String toString() {
		return "SCADLeagueListDto [scadLeagues=" + scadLeagues + "]";
	}
	

}
