package edu.ccsu.cs595.capstone.scadservices.dao;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import edu.ccsu.cs595.capstone.scadservices.config.ScadDatabase;
import edu.ccsu.cs595.capstone.scadservices.entity.SCADLeaguePlayer;

public class SCADLeaguePlayerDao {

	@Inject
	@ScadDatabase
	private EntityManager scadEm;

	public EntityManager scadEm() {
		return scadEm;
	}
	
	public SCADLeaguePlayer find(Long id) {
		return scadEm().find(SCADLeaguePlayer.class, id);
	}
	
	public SCADLeaguePlayer getSCADLeaguePlayerByYahooLeagueAndPlayer(Long yahooLeagueID, Long yahooLeaguePlayerId) {

		SCADLeaguePlayer result = null;

		if ((Objects.nonNull(yahooLeagueID)) && (Objects.nonNull(yahooLeaguePlayerId))) {
			try {
				result = (SCADLeaguePlayer) scadEm().createQuery("from SCADLeaguePlayer slp where slp.yahooLeagueID = :vYLId and slp.yahooLeaguePlayerId = :vYLPId")
						.setParameter("vYLId", yahooLeagueID).setParameter("vYLPId", yahooLeaguePlayerId).getSingleResult();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	public SCADLeaguePlayer getSCADLeaguePlayerBySCADLeagueAndPlayer(Long scadLeagueId, Long id) {

		SCADLeaguePlayer result = null;

		if ((Objects.nonNull(scadLeagueId)) && (Objects.nonNull(id))) {
			try {
				result = (SCADLeaguePlayer) scadEm().createQuery("from SCADLeaguePlayer slp where slp.scadLeagueId = :vSCADLId and slp.id = :vId")
						.setParameter("vSCADLId", scadLeagueId).setParameter("vId", id).getSingleResult();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	@SuppressWarnings("unchecked")
	public List<SCADLeaguePlayer> getAllSCADLeaguePlayersByYahooLeague(Long yahooLeagueId) {

		List<SCADLeaguePlayer> result = null;
		
		if (Objects.nonNull(yahooLeagueId)) {
			try {
				result = (List<SCADLeaguePlayer>) scadEm().createQuery("from SCADLeaguePlayer slp where slp.yahooLeagueId = :vYLId")
						.setParameter("vYLId", yahooLeagueId).getResultList();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	@SuppressWarnings("unchecked")
	public List<SCADLeaguePlayer> getAllSCADLeaguePlayersBySCADLeague(Long scadLeagueId) {

		List<SCADLeaguePlayer> result = null;
		
		if (Objects.nonNull(scadLeagueId)) {
			try {
				result = (List<SCADLeaguePlayer>) scadEm().createQuery("from SCADLeaguePlayer slp where slp.scadLeagueId = :vSCADLId")
						.setParameter("vSCADLId", scadLeagueId).getResultList();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	@SuppressWarnings("unchecked")
	public List<SCADLeaguePlayer> getSCADLeaguePlayersBySCADLeagueAndYahooPlayersIds(Long scadLeagueId, List<Long> yahooPlayerIds) {

		List<SCADLeaguePlayer> result = null;
		
		if ((Objects.nonNull(scadLeagueId)) && (Objects.nonNull(yahooPlayerIds))){
			try {
				result = (List<SCADLeaguePlayer>) scadEm().createQuery("from SCADLeaguePlayer slp where slp.scadLeagueId = :vSCADLId and slp.yahooLeaguePlayerId in (:vYPIds)")
						.setParameter("vSCADLId", scadLeagueId).setParameter("vYPIds", yahooPlayerIds).getResultList();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	public SCADLeaguePlayer upsert(SCADLeaguePlayer entity) {
		return scadEm().merge(entity);
	}
	
	public void delete(SCADLeaguePlayer entity) {
		scadEm().remove(entity);
	}
	
}
