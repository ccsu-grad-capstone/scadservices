package edu.ccsu.cs595.capstone.scadservices.dao;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import edu.ccsu.cs595.capstone.scadservices.config.ScadDatabase;
import edu.ccsu.cs595.capstone.scadservices.entity.SCADLeague;

public class SCADLeagueDao {

	@Inject
	@ScadDatabase
	private EntityManager scadEm;

	public EntityManager scadEm() {
		return scadEm;
	}
	
	public SCADLeague find(Long id) {
		return scadEm().find(SCADLeague.class, id);
	}

	public SCADLeague getSCADLeagueByYahooLeagueId(Long yahooLeagueID) {

		SCADLeague result = null;

		if (Objects.nonNull(yahooLeagueID)) {
			try {
				result = (SCADLeague) scadEm().createQuery("from SCADLeague sl where sl.yahooLeagueID = :vYLId")
						.setParameter("vYLId", yahooLeagueID).getSingleResult();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	@SuppressWarnings("unchecked")
	public List<SCADLeague> getSCADLeaguesByYahooUserId(String userId) {

		List<SCADLeague> result = null;
		
		if (Objects.nonNull(userId)) {
			try {
				result = (List<SCADLeague>) scadEm().createQuery("from SCADLeague sl where sl.ownerId = :vUId")
						.setParameter("vUId", userId).getResultList();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}
	
	public SCADLeague upsert(SCADLeague entity) {
		return scadEm().merge(entity);
	}
	
	public void delete(SCADLeague entity) {
		scadEm().remove(entity);
	}
	
}
