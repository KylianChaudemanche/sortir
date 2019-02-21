package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.VilleDao;
import fr.eni.sortir.entities.Ville;

public class JpaVilleDao extends JpaDao implements VilleDao {

	public JpaVilleDao(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	public Ville addVille(Ville ville) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(ville);
			em.flush();
			transaction.commit();

			if (ville.getNoVille() != 0) {
				return ville;
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
	public Ville findVille(Integer noVille) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Ville ville = em.find(Ville.class, noVille);
		try {
			if (ville != null) {
				return ville;
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	@Override
	public Ville updateVille(Ville ville) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(ville);
			transaction.commit();

			return ville;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeVille(Integer noVille) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Ville ville = em.find(Ville.class, noVille);
			
			if (ville != null) {
				transaction.begin();
				
				em.remove(ville);

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
	public Collection<Ville> getAllVille() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT v FROM Ville AS v", Ville.class);

			Collection<Ville> listVille = query.getResultList();

			return listVille;
		} finally {
			em.close();
		}
	}
}
