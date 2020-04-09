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
	
	public SCADLeaguePlayer getSCADLeaguePlayerByYahooLeagueAndPlayer(Long leagueId, Long playerId) {

		SCADLeaguePlayer result = null;

		if ((Objects.nonNull(leagueId)) && (Objects.nonNull(playerId))) {
			try {
				result = (SCADLeaguePlayer) scadEm().createQuery("from SCADLeaguePlayer slp where slp.yahooLeagueId = :vLId and slp.yahooLeaguePlayerId = :vPId")
						.setParameter("vLId", leagueId).setParameter("vPId", playerId).getSingleResult();
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
	public List<SCADLeaguePlayer> getSCADLeaguePlayersByYahooLeague(Long leagueId) {

		List<SCADLeaguePlayer> result = null;
		
		if (Objects.nonNull(leagueId)) {
			try {
				result = (List<SCADLeaguePlayer>) scadEm().createQuery("from SCADLeaguePlayer slp where slp.yahooLeagueId = :vLId")
						.setParameter("vLId", leagueId).getResultList();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	@SuppressWarnings("unchecked")
	public List<SCADLeaguePlayer> getSCADLeaguePlayersBySCADLeague(Long scadLeagueId) {

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
	public List<SCADLeaguePlayer> getSCADLeaguePlayersBySCADLeagueAndYahooPlayersIds(Long scadLeagueId, List<Long> playerIds) {

		List<SCADLeaguePlayer> result = null;
		
		if ((Objects.nonNull(scadLeagueId)) && (Objects.nonNull(playerIds))){
			try {
				result = (List<SCADLeaguePlayer>) scadEm().createQuery("from SCADLeaguePlayer slp where slp.scadLeagueId = :vSCADLId and slp.yahooLeaguePlayerId in (:vPIds)")
						.setParameter("vSCADLId", scadLeagueId).setParameter("vPIds", playerIds).getResultList();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	@SuppressWarnings("unchecked")
	public List<SCADLeaguePlayer> getSCADLeaguePlayersByYahooLeagueAndPlayersIds(Long leagueId, List<Long> playerIds) {

		List<SCADLeaguePlayer> result = null;
		
		if ((Objects.nonNull(leagueId)) && (Objects.nonNull(playerIds))){
			try {
				result = (List<SCADLeaguePlayer>) scadEm().createQuery("from SCADLeaguePlayer slp where slp.yahooLeagueId = :vLId and slp.yahooLeaguePlayerId in (:vPIds)")
						.setParameter("vLId", leagueId).setParameter("vPIds", playerIds).getResultList();
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
