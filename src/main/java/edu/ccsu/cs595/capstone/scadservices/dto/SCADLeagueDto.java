package edu.ccsu.cs595.capstone.scadservices.dto;

import java.time.ZonedDateTime;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SCADLeagueDto extends AuditedDto {
	
	private String gameKey;
	private String gameId;
	private String leagueKey;
	private String leagueId;
	private String name;
	private String url;
	private Boolean logoUrl;
	private String draftStatus;
	private Long numTeams;
	private String weeklyDeadLine;
	private ZonedDateTime leagueUpdateTimestamp;
	private String scoringType;
	private String leagueType;
	private String renew;
	
	

	@Override
	public Long getId() {
		return null;
	}

	@Override
	public void setId(Long id) {

	}

	
}
