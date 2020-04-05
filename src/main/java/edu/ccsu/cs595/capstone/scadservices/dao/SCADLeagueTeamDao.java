package edu.ccsu.cs595.capstone.scadservices.dao;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import edu.ccsu.cs595.capstone.scadservices.config.ScadDatabase;
import edu.ccsu.cs595.capstone.scadservices.entity.SCADLeagueTeam;

public class SCADLeagueTeamDao {

	@Inject
	@ScadDatabase
	private EntityManager scadEm;

	public EntityManager scadEm() {
		return scadEm;
	}
	
	public SCADLeagueTeam find(Long id) {
		return scadEm().find(SCADLeagueTeam.class, id);
	}
	
	public SCADLeagueTeam getSCADLeagueTeamByYahooLeagueAndTeam(Long yahooLeagueID, Long yahooLeagueTeamId) {

		SCADLeagueTeam result = null;

		if ((Objects.nonNull(yahooLeagueID)) && (Objects.nonNull(yahooLeagueTeamId))) {
			try {
				result = (SCADLeagueTeam) scadEm().createQuery("from SCADLeagueTeam slt where slt.yahooLeagueId = :vYLId and slt.yahooLeagueTeamId = :vYLTId")
						.setParameter("vYLId", yahooLeagueID).setParameter("vYLTId", yahooLeagueTeamId).getSingleResult();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	public SCADLeagueTeam getSCADLeagueTeamBySCADLeagueAndTeam(Long scadLeagueId, Long id) {

		SCADLeagueTeam result = null;

		if ((Objects.nonNull(scadLeagueId)) && (Objects.nonNull(id))) {
			try {
				result = (SCADLeagueTeam) scadEm().createQuery("from SCADLeagueTeam slt where slt.scadLeagueId = :vSCADLId and slt.id = :vId")
						.setParameter("vSCADLId", scadLeagueId).setParameter("vId", id).getSingleResult();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	public SCADLeagueTeam getSCADLeagueTeamBySCADLeagueAndYahooTeamId(Long scadLeagueId, Long yahooTeamId) {

		SCADLeagueTeam result = null;

		if ((Objects.nonNull(scadLeagueId)) && (Objects.nonNull(yahooTeamId))) {
			try {
				result = (SCADLeagueTeam) scadEm().createQuery("from SCADLeagueTeam slt where slt.scadLeagueId = :vSCADLId and slt.yahooLeagueTeamId = :vYahooTeamId")
						.setParameter("vSCADLId", scadLeagueId).setParameter("vYahooTeamId", yahooTeamId).getSingleResult();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	@SuppressWarnings("unchecked")
	public List<SCADLeagueTeam> getAllSCADLeagueTeamsByYahooLeague(Long yahooLeagueId) {

		List<SCADLeagueTeam> result = null;
		
		if (Objects.nonNull(yahooLeagueId)) {
			try {
				result = (List<SCADLeagueTeam>) scadEm().createQuery("from SCADLeagueTeam slt where slt.yahooLeagueId = :vYLId")
						.setParameter("vYLId", yahooLeagueId).getResultList();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	@SuppressWarnings("unchecked")
	public List<SCADLeagueTeam> getAllSCADLeagueTeamsBySCADLeague(Long scadLeagueId) {

		List<SCADLeagueTeam> result = null;
		
		if (Objects.nonNull(scadLeagueId)) {
			try {
				result = (List<SCADLeagueTeam>) scadEm().createQuery("from SCADLeagueTeam slt where slt.scadLeagueId = :vSCADLId")
						.setParameter("vSCADLId", scadLeagueId).getResultList();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	public SCADLeagueTeam upsert(SCADLeagueTeam entity) {
		return scadEm().merge(entity);
	}
	
	public void delete(SCADLeagueTeam entity) {
		scadEm().remove(entity);
	}
	
}
