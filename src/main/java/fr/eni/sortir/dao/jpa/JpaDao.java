package fr.eni.sortir.dao.jpa;

import javax.persistence.EntityManagerFactory;

public class JpaDao {
	private EntityManagerFactory emf;

	protected JpaDao(EntityManagerFactory emf) {
		this.emf = emf;
	}

	protected EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
}
