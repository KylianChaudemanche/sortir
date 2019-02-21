package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.EtatDao;
import fr.eni.sortir.entities.Etat;

public class JpaEtatDao extends JpaDao implements EtatDao {
	public JpaEtatDao(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	public Etat addEtat(Etat etat) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(etat);
			transaction.commit();
			em.flush();

			if (etat.getNoEtat() != 0) {
				return etat;
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
	public Etat findEtat(Integer noEtat) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Etat etat = em.find(Etat.class, noEtat);
		try {
			if (etat != null) {
				return etat;
			} else {
				return null;
			}
		} finally {
			em.close();
		}

	}

	@Override
	public Etat updateEtat(Etat etat) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(etat);
			transaction.commit();

			return etat;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeEtat(Integer noEtat) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Etat etat = em.find(Etat.class, noEtat);
			
			if (etat != null) {
				transaction.begin();
				
				em.remove(etat);

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
	public Collection<Etat> getAllEtat() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT e FROM Etats AS e", Etat.class);

			Collection<Etat> listEtat = query.getResultList();

			return listEtat;
		} finally {
			em.close();
		}
	}

	@Override
	public Etat findEtatByName(String name) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		
		try {
			Query query = em.createQuery("SELECT e FROM Etat AS e WHERE libelle = :libelle", Etat.class).setParameter("libelle", name);

			Etat etat = (Etat) query.getSingleResult();

			return etat;
		} finally {
			em.close();
		}
	}
}
