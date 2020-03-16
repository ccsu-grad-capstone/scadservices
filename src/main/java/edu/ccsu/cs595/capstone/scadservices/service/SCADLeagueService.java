package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ccsu.cs595.capstone.scadservices.dao.SCADLeagueDao;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueListDto;
import edu.ccsu.cs595.capstone.scadservices.entity.SCADLeague;
import edu.ccsu.cs595.capstone.scadservices.exception.AuthorizationFailedException;
import edu.ccsu.cs595.capstone.scadservices.util.YahooClientBuilder;

@Stateless
public class SCADLeagueService {

	private static final Logger LOG = LoggerFactory.getLogger(SCADLeagueService.class);

	@Inject
	SCADLeagueDao slDao;

	@Inject
	YahooClientBuilder yahoo;

	public SCADLeagueDto getSCADLeague(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueDto result = null;
		SCADLeague slEntity = slDao.find(id);
		result = this.entityToDto(slEntity);
		return result;

	}

	public SCADLeagueDto getDefaultSCADLeagueByYahooGame() throws AuthorizationFailedException, RuntimeException {

		SCADLeagueDto result = null;
		Long yahooGameId = yahoo.getYahooGame();
		SCADLeague slEntity = slDao.getDefaultSCADLeagueByYahooGame(yahooGameId);
		result = this.entityToDto(slEntity);
		return result;

	}

	public SCADLeagueDto getSCADLeagueByYahooGameAndLeague(Long yahooLeagueId) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueDto result = null;
		Long yahooGameId = yahoo.getYahooGame();
		SCADLeague slEntity = slDao.getSCADLeagueByYahooGameAndLeague(yahooGameId, yahooLeagueId);
		result = this.entityToDto(slEntity);
		return result;

	}

	public SCADLeagueListDto getAllSCADLeaguesByYahooGame() throws AuthorizationFailedException, RuntimeException {

		SCADLeagueListDto list = new SCADLeagueListDto();
		Long yahooGameId = yahoo.getYahooGame();
		List<SCADLeague> slEntityList = slDao.getAllSCADLeaguesByYahooGame(yahooGameId);
		for (SCADLeague slEntity : slEntityList) {
			SCADLeagueDto result = this.entityToDto(slEntity);
			list.getScadLeagues().add(result);
		}
		return list;

	}

	public SCADLeagueDto createSCADLeague(SCADLeagueDto slDto) throws AuthorizationFailedException, RuntimeException {

		SCADLeagueDto result = null;
		SCADLeague newEntity = this.dtoToEntity(slDto);
		newEntity = slDao.upsert(newEntity);
		result = this.entityToDto(newEntity);
		return result;

	}

	public SCADLeagueDto updateSCADLeague(Long id, SCADLeagueDto slDto)
			throws AuthorizationFailedException, RuntimeException {

		SCADLeagueDto result = null;
		SCADLeague existingEntity = slDao.find(id);
		this.dtoToEntityForUpdate(slDto, existingEntity);
		existingEntity = slDao.upsert(existingEntity);
		result = this.entityToDto(existingEntity);
		return result;

	}

	public void deleteSCADLeague(Long id) throws AuthorizationFailedException, RuntimeException {

		SCADLeague deleteEntity = slDao.find(id);
		slDao.delete(deleteEntity);

	}

	private SCADLeagueDto entityToDto(SCADLeague slEntity) {

		SCADLeagueDto result = new SCADLeagueDto();

		if (Objects.nonNull(slEntity)) {

			result.setId(slEntity.getId());
			result.setYahooGameId(slEntity.getYahooGameId());
			result.setYahooLeagueId(slEntity.getYahooLeagueId());
			result.setSeasonYear(slEntity.getSeasonYear());
			result.setLeagueManagers(slEntity.getLeagueManagers());
			result.setRookieDraftRds(slEntity.getRookieDraftRds());
			result.setRookieDraftStrategy(slEntity.getRookieDraftStrategy());
			result.setRookieWageScale(slEntity.getRookieWageScale());
			result.setTeamSalaryCap(slEntity.getTeamSalaryCap());
			result.setLeagueSalaryCap(slEntity.getLeagueSalaryCap());
			result.setSalaryCapExemptionLimit(slEntity.getSalaryCapExemptionLimit());
			result.setIrReliefPerc(slEntity.getIrReliefPerc());
			result.setFranchiseTagReliefPerc(slEntity.getFranchiseTagReliefPerc());
			result.setFranchiseTagSpots(slEntity.getFranchiseTagSpots());
			result.setTradingDraftPickYears(slEntity.getTradingDraftPickYears());
			result.setQbMin(slEntity.getQbMin());
			result.setQbMax(slEntity.getQbMax());
			result.setRbMin(slEntity.getRbMin());
			result.setRbMax(slEntity.getRbMax());
			result.setWrMin(slEntity.getWrMin());
			result.setWrMax(slEntity.getWrMax());
			result.setTeMin(slEntity.getTeMin());
			result.setTeMax(slEntity.getTeMax());
			result.setkMin(slEntity.getkMin());
			result.setkMax(slEntity.getkMax());
			result.setDefMin(slEntity.getDefMin());
			result.setDefMax(slEntity.getDefMax());
			result.setIsDefault(slEntity.getIsDefault());
			result.setOwnerGuid(slEntity.getOwnerGuid());
			result.setCreatedBy(slEntity.getCreatedBy());
			result.setCreatedAt(slEntity.getCreatedAt());
			result.setModifiedBy(slEntity.getModifiedBy());
			result.setModifiedAt(slEntity.getModifiedAt());

		}

		return result;

	}

	private SCADLeague dtoToEntity(SCADLeagueDto slDto) {

		SCADLeague result = new SCADLeague();

		if (Objects.nonNull(slDto)) {

			result.setYahooGameId(slDto.getYahooGameId());
			result.setYahooLeagueId(slDto.getYahooLeagueId());
			result.setSeasonYear(slDto.getSeasonYear());
			result.setLeagueManagers(slDto.getLeagueManagers());
			result.setRookieDraftRds(slDto.getRookieDraftRds());
			result.setRookieDraftStrategy(slDto.getRookieDraftStrategy());
			result.setRookieWageScale(slDto.getRookieWageScale());
			result.setTeamSalaryCap(slDto.getTeamSalaryCap());
			result.setLeagueSalaryCap(slDto.getLeagueSalaryCap());
			result.setSalaryCapExemptionLimit(slDto.getSalaryCapExemptionLimit());
			result.setIrReliefPerc(slDto.getIrReliefPerc());
			result.setFranchiseTagReliefPerc(slDto.getFranchiseTagReliefPerc());
			result.setFranchiseTagSpots(slDto.getFranchiseTagSpots());
			result.setTradingDraftPickYears(slDto.getTradingDraftPickYears());
			result.setQbMin(slDto.getQbMin());
			result.setQbMax(slDto.getQbMax());
			result.setRbMin(slDto.getRbMin());
			result.setRbMax(slDto.getRbMax());
			result.setWrMin(slDto.getWrMin());
			result.setWrMax(slDto.getWrMax());
			result.setTeMin(slDto.getTeMin());
			result.setTeMax(slDto.getTeMax());
			result.setkMin(slDto.getkMin());
			result.setkMax(slDto.getkMax());
			result.setDefMin(slDto.getDefMin());
			result.setDefMax(slDto.getDefMax());
			result.setIsDefault(slDto.getIsDefault());
			result.setOwnerGuid(slDto.getOwnerGuid());

		}

		return result;

	}

	private void dtoToEntityForUpdate(SCADLeagueDto slDto, SCADLeague existingEntity) {

		if (Objects.nonNull(slDto)) {

			if (Objects.nonNull(slDto.getRookieDraftRds())) {
				existingEntity.setRookieDraftRds(slDto.getRookieDraftRds());
			}
			if (Objects.nonNull(slDto.getRookieDraftStrategy())) {
				existingEntity.setRookieDraftStrategy(slDto.getRookieDraftStrategy());
			}
			if (Objects.nonNull(slDto.getRookieWageScale())) {
				existingEntity.setRookieWageScale(slDto.getRookieWageScale());
			}
			if (Objects.nonNull(slDto.getTeamSalaryCap())) {
				existingEntity.setTeamSalaryCap(slDto.getTeamSalaryCap());
			}
			if (Objects.nonNull(slDto.getLeagueSalaryCap())) {
				existingEntity.setLeagueSalaryCap(slDto.getLeagueSalaryCap());
			}
			if (Objects.nonNull(slDto.getSalaryCapExemptionLimit())) {
				existingEntity.setSalaryCapExemptionLimit(slDto.getSalaryCapExemptionLimit());
			}
			if (Objects.nonNull(slDto.getIrReliefPerc())) {
				existingEntity.setIrReliefPerc(slDto.getIrReliefPerc());
			}
			if (Objects.nonNull(slDto.getFranchiseTagReliefPerc())) {
				existingEntity.setFranchiseTagReliefPerc(slDto.getFranchiseTagReliefPerc());
			}
			if (Objects.nonNull(slDto.getFranchiseTagSpots())) {
				existingEntity.setFranchiseTagSpots(slDto.getFranchiseTagSpots());
			}
			if (Objects.nonNull(slDto.getTradingDraftPickYears())) {
				existingEntity.setTradingDraftPickYears(slDto.getTradingDraftPickYears());
			}
			if (Objects.nonNull(slDto.getQbMin())) {
				existingEntity.setQbMin(slDto.getQbMin());
			}
			if (Objects.nonNull(slDto.getQbMax())) {
				existingEntity.setQbMax(slDto.getQbMax());
			}
			if (Objects.nonNull(slDto.getRbMin())) {
				existingEntity.setRbMin(slDto.getRbMin());
			}
			if (Objects.nonNull(slDto.getRbMax())) {
				existingEntity.setRbMax(slDto.getRbMax());
			}
			if (Objects.nonNull(slDto.getWrMin())) {
				existingEntity.setWrMin(slDto.getWrMin());
			}
			if (Objects.nonNull(slDto.getWrMax())) {
				existingEntity.setWrMax(slDto.getWrMax());
			}
			if (Objects.nonNull(slDto.getTeMin())) {
				existingEntity.setTeMin(slDto.getTeMin());
			}
			if (Objects.nonNull(slDto.getTeMax())) {
				existingEntity.setTeMax(slDto.getTeMax());
			}
			if (Objects.nonNull(slDto.getkMin())) {
				existingEntity.setkMin(slDto.getkMin());
			}
			if (Objects.nonNull(slDto.getkMax())) {
				existingEntity.setkMax(slDto.getkMax());
			}
			if (Objects.nonNull(slDto.getDefMin())) {
				existingEntity.setDefMin(slDto.getDefMin());
			}
			if (Objects.nonNull(slDto.getDefMax())) {
				existingEntity.setDefMax(slDto.getDefMax());
			}
			if (Objects.nonNull(slDto.getIsDefault())) {
				existingEntity.setIsDefault(slDto.getIsDefault());
			}

		}

	}

}
