package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ccsu.cs595.capstone.scadservices.dao.SCADLeagueTeamDao;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamListDto;
import edu.ccsu.cs595.capstone.scadservices.entity.SCADLeagueTeam;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;

@Stateless
public class SCADLeagueTeamService {

	private static final Logger LOG = LoggerFactory.getLogger(SCADLeagueTeamService.class);

	@Inject
	SCADLeagueTeamDao sltDao;

	public SCADLeagueTeamDto getSCADLeague(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamDto result = null;
		SCADLeagueTeam sltEntity = sltDao.find(id);
		result = this.entityToDto(sltEntity);
		return result;

	}

	public SCADLeagueTeamDto getSCADLeagueTeamByYahooLeagueAndTeam(Long yahooLeagueID, Long yahooLeagueTeamId) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamDto result = null;
		SCADLeagueTeam sltEntity = sltDao.getSCADLeagueTeamByYahooLeagueAndTeam(yahooLeagueID, yahooLeagueTeamId);
		result = this.entityToDto(sltEntity);
		return result;

	}

	public SCADLeagueTeamDto getSCADLeagueTeamBySCADLeagueAndTeam(Long scadLeagueId, Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamDto result = null;
		SCADLeagueTeam sltEntity = sltDao.getSCADLeagueTeamBySCADLeagueAndTeam(scadLeagueId, id);
		result = this.entityToDto(sltEntity);
		return result;

	}

	public SCADLeagueTeamListDto getAllSCADLeagueTeamsByYahooLeague(Long yahooLeagueId) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamListDto list = new SCADLeagueTeamListDto();
		List<SCADLeagueTeam> sltEntityList = sltDao.getAllSCADLeagueTeamsByYahooLeague(yahooLeagueId);
		for (SCADLeagueTeam sltEntity : sltEntityList) {
			SCADLeagueTeamDto result = this.entityToDto(sltEntity);
			list.getScadLeagueTeams().add(result);
		}
		return list;

	}
	
	public SCADLeagueTeamListDto getAllSCADLeagueTeamsBySCADLeague(Long scadLeagueId) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamListDto list = new SCADLeagueTeamListDto();
		List<SCADLeagueTeam> sltEntityList = sltDao.getAllSCADLeagueTeamsBySCADLeague(scadLeagueId);
		for (SCADLeagueTeam sltEntity : sltEntityList) {
			SCADLeagueTeamDto result = this.entityToDto(sltEntity);
			list.getScadLeagueTeams().add(result);
		}
		return list;

	}

	public SCADLeagueTeamDto createSCADLeagueTeam(SCADLeagueTeamDto sltDto) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamDto result = null;
		SCADLeagueTeam newEntity = this.dtoToEntity(sltDto);
		newEntity = sltDao.upsert(newEntity);
		result = this.entityToDto(newEntity);
		return result;

	}

	public SCADLeagueTeamDto updateSCADLeagueTeam(Long id, SCADLeagueTeamDto sltDto)
			throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeamDto result = null;
		SCADLeagueTeam existingEntity = sltDao.find(id);
		this.dtoToEntityForUpdate(sltDto, existingEntity);
		existingEntity = sltDao.upsert(existingEntity);
		result = this.entityToDto(existingEntity);
		return result;

	}

	public void deleteSCADLeagueTeam(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueTeam deleteEntity = sltDao.find(id);
		sltDao.delete(deleteEntity);

	}

	private SCADLeagueTeamDto entityToDto(SCADLeagueTeam sltEntity) {

		SCADLeagueTeamDto result = new SCADLeagueTeamDto();

		if (Objects.nonNull(sltEntity)) {

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

		}

		return result;

	}

	private SCADLeagueTeam dtoToEntity(SCADLeagueTeamDto sltDto) {

		SCADLeagueTeam result = new SCADLeagueTeam();

		if (Objects.nonNull(sltDto)) {

			result.setYahooLeagueTeamId(sltDto.getYahooLeagueTeamId());
			result.setYahooLeagueId(sltDto.getYahooLeagueId());
			result.setScadLeagueId(sltDto.getScadLeagueId());
			result.setSalary(sltDto.getSalary());
			result.setIsFranchiseTag(sltDto.getIsFranchiseTag());
			result.setExceptionIn(sltDto.getExceptionIn());
			result.setExceptionOut(sltDto.getExceptionOut());

		}

		return result;

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
