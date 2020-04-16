package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.dao.SCADLeagueTeamDao;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamListDto;
import edu.ccsu.cs595.capstone.scadservices.entity.AuditContext;
import edu.ccsu.cs595.capstone.scadservices.entity.SCADLeagueTeam;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.util.YahooClientBuilder;

@Stateless
public class SCADLeagueTeamService {

	private static final Logger LOG = LoggerFactory.getLogger(SCADLeagueTeamService.class);

	@Inject
	SCADLeagueTeamDao sltDao;
	
	@Inject
	YahooClientBuilder yahoo;
	
	@Inject
	LeagueService lSvc;
	
	@Inject
	SCADLeagueService slSvc;

	public SCADLeagueTeamDto getSCADLeagueTeam(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamDto result = null;
		SCADLeagueTeam sltEntity = sltDao.find(id);
		result = this.entityToDto(sltEntity);
		return result;

	}

	public SCADLeagueTeamDto getSCADLeagueTeamByYahooLeagueAndTeam(Long leagueId, Long teamId) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamDto result = null;
		SCADLeagueTeam sltEntity = sltDao.getSCADLeagueTeamByYahooLeagueAndTeam(leagueId, teamId);
		result = this.entityToDto(sltEntity);
		return result;

	}

	public SCADLeagueTeamDto getSCADLeagueTeamBySCADLeagueAndTeam(Long scadLeagueId, Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamDto result = null;
		SCADLeagueTeam sltEntity = sltDao.getSCADLeagueTeamBySCADLeagueAndTeam(scadLeagueId, id);
		result = this.entityToDto(sltEntity);
		return result;

	}

	public SCADLeagueTeamListDto getSCADLeagueTeamsByYahooLeague(Long leagueId) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamListDto list = new SCADLeagueTeamListDto();
		List<SCADLeagueTeam> sltEntityList = sltDao.getSCADLeagueTeamsByYahooLeague(leagueId);
		for (SCADLeagueTeam sltEntity : sltEntityList) {
			SCADLeagueTeamDto result = this.entityToDto(sltEntity);
			list.getScadLeagueTeams().add(result);
		}
		return list;

	}
	
	public SCADLeagueTeamListDto getSCADLeagueTeamsBySCADLeague(Long scadLeagueId) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamListDto list = new SCADLeagueTeamListDto();
		List<SCADLeagueTeam> sltEntityList = sltDao.getSCADLeagueTeamsBySCADLeague(scadLeagueId);
		for (SCADLeagueTeam sltEntity : sltEntityList) {
			SCADLeagueTeamDto result = this.entityToDto(sltEntity);
			list.getScadLeagueTeams().add(result);
		}
		return list;

	}
	
	public SCADLeagueTeamDto getSCADLeagueMyTeam(Long scadLeagueId) throws AuthorizationFailedException, RuntimeException {
		
		SCADLeagueTeamDto result = null;
		SCADLeagueDto scadLeagueDto = slSvc.getSCADLeague(scadLeagueId);
		Long yahooTeamId = this.getMyYahooTeamId(scadLeagueDto.getYahooLeagueId());
		result = this.getSCADLeagueTeamBySCADLeagueAndYahooTeamId(scadLeagueId, yahooTeamId);
		return result;
		
	}
	
	public SCADLeagueTeamDto getSCADLeagueMyTeamByYahooLeague(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		SCADLeagueTeamDto result = null;
		Long yahooTeamId = this.getMyYahooTeamId(leagueId);
		result = this.getSCADLeagueTeamByYahooLeagueAndTeam(leagueId, yahooTeamId);
		return result;
		
	}
	
	public Long getMyYahooTeamId(Long leagueId) throws AuthorizationFailedException, RuntimeException {
		
		String yahooLeagueMyTeamStrg = lSvc.getYahooLeagueMyTeam(leagueId);
		Long yahooLeagueMyTeamId = new JsonParser().parse(yahooLeagueMyTeamStrg).getAsJsonObject().get("team_id").getAsLong();
		return yahooLeagueMyTeamId;
		
	}
	
	public SCADLeagueTeamDto getSCADLeagueTeamBySCADLeagueAndYahooTeamId(Long scadLeagueId, Long yahooTeamId) {
		
		SCADLeagueTeamDto result = null;
		SCADLeagueTeam sltEntity = sltDao.getSCADLeagueTeamBySCADLeagueAndYahooTeamId(scadLeagueId, yahooTeamId);
		result = this.entityToDto(sltEntity);
		return result;
		
	}
	
	public SCADLeagueTeamDto createSCADLeagueTeam(SCADLeagueTeamDto sltDto) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamDto result = null;
		SCADLeagueTeam newEntity = this.dtoToEntity(sltDto);
		AuditContext ac = new AuditContext(yahoo.getYahooUserGuid(),yahoo.getYahooUserName());
		AuditContext.setAuditContext(ac);
		newEntity = sltDao.upsert(newEntity);
		result = this.entityToDto(newEntity);
		return result;

	}

	public SCADLeagueTeamDto updateSCADLeagueTeam(Long id, SCADLeagueTeamDto sltDto)
			throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamDto result = null;
		SCADLeagueTeam existingEntity = sltDao.find(id);
		this.dtoToEntityForUpdate(sltDto, existingEntity);
		AuditContext ac = new AuditContext(yahoo.getYahooUserGuid(),yahoo.getYahooUserName());
		AuditContext.setAuditContext(ac);
		existingEntity = sltDao.upsert(existingEntity);
		result = this.entityToDto(existingEntity);
		return result;

	}

	public void deleteSCADLeagueTeam(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeam deleteEntity = sltDao.find(id);
		AuditContext ac = new AuditContext(yahoo.getYahooUserGuid(),yahoo.getYahooUserName());
		AuditContext.setAuditContext(ac);
		sltDao.delete(deleteEntity);

	}

	private SCADLeagueTeamDto entityToDto(SCADLeagueTeam sltEntity) {

		if (Objects.nonNull(sltEntity)) {
			
			SCADLeagueTeamDto result = new SCADLeagueTeamDto();

			result.setId(sltEntity.getId());
			result.setYahooLeagueTeamId(sltEntity.getYahooLeagueTeamId());
			result.setYahooLeagueId(sltEntity.getYahooLeagueId());
			result.setScadLeagueId(sltEntity.getScadLeagueId());
			result.setSalary(sltEntity.getSalary());
			result.setIsFranchiseTag(sltEntity.getIsFranchiseTag());
			result.setExceptionIn(sltEntity.getExceptionIn());
			result.setExceptionOut(sltEntity.getExceptionOut());
			result.setCreatedBy(sltEntity.getCreatedBy());
			result.setCreatedAt(sltEntity.getCreatedAt());
			result.setModifiedBy(sltEntity.getModifiedBy());
			result.setModifiedAt(sltEntity.getModifiedAt());
			result.setRenewSCADLeagueTeamId(sltEntity.getRenewSCADLeagueTeamId());
			
			return result;

		}

		return null;

	}

	private SCADLeagueTeam dtoToEntity(SCADLeagueTeamDto sltDto) {

		if (Objects.nonNull(sltDto)) {
			
			SCADLeagueTeam result = new SCADLeagueTeam();

			result.setYahooLeagueTeamId(sltDto.getYahooLeagueTeamId());
			result.setYahooLeagueId(sltDto.getYahooLeagueId());
			result.setScadLeagueId(sltDto.getScadLeagueId());
			result.setSalary(sltDto.getSalary());
			result.setIsFranchiseTag(sltDto.getIsFranchiseTag());
			result.setExceptionIn(sltDto.getExceptionIn());
			result.setExceptionOut(sltDto.getExceptionOut());
			result.setRenewSCADLeagueTeamId(sltDto.getRenewSCADLeagueTeamId());
			
			return result;

		}

		return null;

	}

	private void dtoToEntityForUpdate(SCADLeagueTeamDto sltDto, SCADLeagueTeam existingEntity) {

		if (Objects.nonNull(sltDto)) {

			if (Objects.nonNull(sltDto.getSalary())) {
				existingEntity.setSalary(sltDto.getSalary());
			}
			if (Objects.nonNull(sltDto.getIsFranchiseTag())) {
				existingEntity.setIsFranchiseTag(sltDto.getIsFranchiseTag());
			}
			if (Objects.nonNull(sltDto.getExceptionIn())) {
				existingEntity.setExceptionIn(sltDto.getExceptionIn());
			}
			if (Objects.nonNull(sltDto.getExceptionOut())) {
				existingEntity.setExceptionOut(sltDto.getExceptionOut());
			}

		}

	}

}
