package br.com.caelum.financas.infra;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class EntityManagerProducer {

	@PersistenceUnit
	private EntityManagerFactory factory;
	
	@RequestScoped @Produces
	public EntityManager get() {
		return factory.createEntityManager();
	}
	
}
