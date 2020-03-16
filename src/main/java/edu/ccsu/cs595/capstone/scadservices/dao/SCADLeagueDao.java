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

	public SCADLeague getDefaultSCADLeagueByYahooGame(Long yahooGameId) {

		SCADLeague result = null;

		if (Objects.nonNull(yahooGameId)) {
			try {
				result = (SCADLeague) scadEm()
						.createQuery("from SCADLeague sl where sl.yahooGameId = :vYGId and sl.isDefault = true")
						.setParameter("vYGId", yahooGameId).getSingleResult();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}

	public SCADLeague getSCADLeagueByYahooGameAndLeague(Long yahooGameId, Long yahooLeagueId) {

		SCADLeague result = null;

		if ((Objects.nonNull(yahooGameId)) && (Objects.nonNull(yahooLeagueId))) {
			try {
				result = (SCADLeague) scadEm()
						.createQuery("from SCADLeague sl where sl.yahooGameId = :vYGId and sl.yahooLeagueId = :vYLId")
						.setParameter("vYGId", yahooGameId).setParameter("vYLId", yahooLeagueId).getSingleResult();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	public List<SCADLeague> getAllSCADLeaguesByYahooGame(Long yahooGameId) {

		List<SCADLeague> result = null;

		if (Objects.nonNull(yahooGameId)) {
			try {
				result = (List<SCADLeague>) scadEm().createQuery("from SCADLeague sl where sl.yahooGameId = :vYGId")
						.setParameter("vYGId", yahooGameId).getResultList();
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
