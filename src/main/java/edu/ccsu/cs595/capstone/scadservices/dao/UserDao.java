package edu.ccsu.cs595.capstone.scadservices.dao;

import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import edu.ccsu.cs595.capstone.scadservices.config.ScadDatabase;
import edu.ccsu.cs595.capstone.scadservices.entity.User;

public class UserDao {

	@Inject
	@ScadDatabase
	private EntityManager scadEm;

	public EntityManager scadEm() {
		return scadEm;
	}

	public User getUserByEmail(String email) {

		User result = null;

		if (Objects.nonNull(email)) {
			try {
				result = (User) scadEm().createQuery("from User u where u.email = :vEmail")
						.setParameter("vEmail", email).getSingleResult();
			} catch (NoResultException e) {
				result = null;
			}
		}

		return result;

	}

}
