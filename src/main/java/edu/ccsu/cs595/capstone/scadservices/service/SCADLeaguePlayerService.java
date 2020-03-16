package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ccsu.cs595.capstone.scadservices.dao.SCADLeaguePlayerDao;
import edu.ccsu.cs595.capstone.scadservices.dao.SCADLeagueTeamDao;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeaguePlayerListDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueTeamListDto;
import edu.ccsu.cs595.capstone.scadservices.entity.SCADLeaguePlayer;
import edu.ccsu.cs595.capstone.scadservices.entity.SCADLeagueTeam;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;

@Stateless
public class SCADLeaguePlayerService {

	private static final Logger LOG = LoggerFactory.getLogger(SCADLeaguePlayerService.class);

	@Inject
	SCADLeaguePlayerDao slpDao;

	public SCADLeaguePlayerDto getSCADLeague(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerDto result = null;
		SCADLeaguePlayer slpEntity = slpDao.find(id);
		result = this.entityToDto(slpEntity);
		return result;

	}

	public SCADLeaguePlayerDto getSCADLeaguePlayerByYahooLeagueAndPlayer(Long yahooLeagueID, Long yahooLeaguePlayerId) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerDto result = null;
		SCADLeaguePlayer slpEntity = slpDao.getSCADLeaguePlayerByYahooLeagueAndPlayer(yahooLeagueID, yahooLeaguePlayerId);
		result = this.entityToDto(slpEntity);
		return result;

	}

	public SCADLeaguePlayerDto getSCADLeaguePlayerBySCADLeagueAndPlayer(Long scadLeagueId, Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerDto result = null;
		SCADLeaguePlayer slpEntity = slpDao.getSCADLeaguePlayerBySCADLeagueAndPlayer(scadLeagueId, id);
		result = this.entityToDto(slpEntity);
		return result;

	}

	public SCADLeaguePlayerListDto getAllSCADLeaguePlayersByYahooLeague(Long yahooLeagueId) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerListDto list = new SCADLeaguePlayerListDto();
		List<SCADLeaguePlayer> slpEntityList = slpDao.getAllSCADLeaguePlayersByYahooLeague(yahooLeagueId);
		for (SCADLeaguePlayer slpEntity : slpEntityList) {
			SCADLeaguePlayerDto result = this.entityToDto(slpEntity);
			list.getScadLeaguePlayers().add(result);
		}
		return list;

	}
	
	public SCADLeaguePlayerListDto getAllSCADLeaguePlayersBySCADLeague(Long scadLeagueId) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerListDto list = new SCADLeaguePlayerListDto();
		List<SCADLeaguePlayer> slpEntityList = slpDao.getAllSCADLeaguePlayersBySCADLeague(scadLeagueId);
		for (SCADLeaguePlayer slpEntity : slpEntityList) {
			SCADLeaguePlayerDto result = this.entityToDto(slpEntity);
			list.getScadLeaguePlayers().add(result);
		}
		return list;

	}

	public SCADLeaguePlayerDto createSCADLeaguePlayer(SCADLeaguePlayerDto slpDto) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerDto result = null;
		SCADLeaguePlayer newEntity = this.dtoToEntity(slpDto);
		newEntity = slpDao.upsert(newEntity);
		result = this.entityToDto(newEntity);
		return result;

	}

	public SCADLeaguePlayerDto updateSCADLeaguePlayer(Long id, SCADLeaguePlayerDto slpDto)
			throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayerDto result = null;
		SCADLeaguePlayer existingEntity = slpDao.find(id);
		this.dtoToEntityForUpdate(slpDto, existingEntity);
		existingEntity = slpDao.upsert(existingEntity);
		result = this.entityToDto(existingEntity);
		return result;

	}

	public void deleteSCADLeaguePlayer(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeaguePlayer deleteEntity = slpDao.find(id);
		slpDao.delete(deleteEntity);

	}

	private SCADLeaguePlayerDto entityToDto(SCADLeaguePlayer slpEntity) {

		SCADLeaguePlayerDto result = new SCADLeaguePlayerDto();

		if (Objects.nonNull(slpEntity)) {

			result.setId(slpEntity.getId());
			result.setYahooLeaguePlayerId(slpEntity.getYahooLeaguePlayerId());
			result.setYahooLeagueId(slpEntity.getYahooLeagueId());
			result.setScadLeagueId(slpEntity.getScadLeagueId());
			result.setSalary(slpEntity.getSalary());
			result.setIsFranchiseTag(slpEntity.getIsFranchiseTag());			
			result.setCreatedBy(slpEntity.getCreatedBy());
			result.setCreatedAt(slpEntity.getCreatedAt());
			result.setModifiedBy(slpEntity.getModifiedBy());
			result.setModifiedAt(slpEntity.getModifiedAt());

		}

		return result;

	}

	private SCADLeaguePlayer dtoToEntity(SCADLeaguePlayerDto slpDto) {

		SCADLeaguePlayer result = new SCADLeaguePlayer();

		if (Objects.nonNull(slpDto)) {

			result.setYahooLeaguePlayerId(slpDto.getYahooLeaguePlayerId());
			result.setYahooLeagueId(slpDto.getYahooLeagueId());
			result.setScadLeagueId(slpDto.getScadLeagueId());
			result.setSalary(slpDto.getSalary());
			result.setIsFranchiseTag(slpDto.getIsFranchiseTag());

		}

		return result;

	}

	private void dtoToEntityForUpdate(SCADLeaguePlayerDto slpDto, SCADLeaguePlayer existingEntity) {

		if (Objects.nonNull(slpDto)) {

			if (Objects.nonNull(slpDto.getSalary())) {
				existingEntity.setSalary(slpDto.getSalary());
			}
			if (Objects.nonNull(slpDto.getIsFranchiseTag())) {
				existingEntity.setIsFranchiseTag(slpDto.getIsFranchiseTag());
			}

		}

	}

}
