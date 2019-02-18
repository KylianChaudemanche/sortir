package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.EtatsDao;
import fr.eni.sortir.entities.Etats;

public class JpaEtatsDao extends JpaDao implements EtatsDao {
	public JpaEtatsDao(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	public Etats addEtats(Etats etats) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(etats);
			transaction.commit();
			em.flush();

			if (etats.getNoEtat() != 0) {
				return etats;
			} else {
				return null;
			}
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Etats findEtats(Integer noEtats) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Etats etats = em.find(Etats.class, noEtats);
		try {
			if (etats != null) {
				return etats;
			} else {
				return null;
			}
		} finally {
			em.close();
		}

	}

	@Override
	public Etats updateEtats(Etats etats) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(etats);
			transaction.commit();

			return etats;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeEtats(Integer noEtats) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Etats etats = em.find(Etats.class, noEtats);
			
			if (etats != null) {
				transaction.begin();
				
				em.remove(etats);

				transaction.commit();
				
				return true;
			} else {
				return false;
			}
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Etats> getAllEtats() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT e FROM Etats AS e", Etats.class);

			Collection<Etats> listEtats = query.getResultList();

			return listEtats;
		} finally {
			em.close();
		}
	}
}
