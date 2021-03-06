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

	public SCADLeague getDefaultUserSCADLeagueBySeason(Long yahooGameId, String userGuid) {

		SCADLeague result = null;

		if ((Objects.nonNull(yahooGameId)) && (Objects.nonNull(userGuid))) {
			try {
				result = (SCADLeague) scadEm()
						.createQuery("from SCADLeague sl where sl.yahooGameId = :vYGId and sl.ownerGuid = :vUserGuid and sl.isDefault = true")
						.setParameter("vYGId", yahooGameId).setParameter("vUserGuid", userGuid).getSingleResult();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}

	public SCADLeague getSCADLeagueByYahooLeague(Long leagueId) {

		SCADLeague result = null;

		if (Objects.nonNull(leagueId)) {
			try {
				result = (SCADLeague) scadEm()
						.createQuery("from SCADLeague sl where sl.yahooLeagueId = :vLId")
						.setParameter("vLId", leagueId).getSingleResult();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	public List<SCADLeague> getUserSCADLeaguesBySeason(Long yahooGameId, String userGuid) {

		List<SCADLeague> result = null;

		if ((Objects.nonNull(yahooGameId)) && (Objects.nonNull(userGuid))) {
			try {
				result = (List<SCADLeague>) scadEm().createQuery("from SCADLeague sl where sl.yahooGameId = :vYGId and sl.ownerGuid = :vUserGuid")
						.setParameter("vYGId", yahooGameId).setParameter("vUserGuid", userGuid).getResultList();
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
