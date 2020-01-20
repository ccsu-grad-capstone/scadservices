package edu.ccsu.cs595.capstone.scadservices;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {

	@Produces
	@PersistenceContext(name = "scadSvcPu", unitName = "scadSvcPu")
	@ScadDatabase

	private EntityManager scadEm;

}
