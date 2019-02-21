package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import org.apache.log4j.Logger;

import fr.eni.sortir.dao.VilleDao;
import fr.eni.sortir.entities.Ville;

public class JpaVilleDao extends JpaDao implements VilleDao {
	private static final Logger LOGGER = Logger.getLogger(JpaVilleDao.class);
	private final String QUERY_ALL_VILLE = "SELECT v FROM Ville AS v";

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
		} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
			ville = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return ville;
	}

	@Override
	public Ville findVille(final Integer noVille) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Ville ville = null;
		try {
			ville = em.find(Ville.class, noVille);
		} catch (IllegalStateException | IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			em.close();
		}
		return ville;
	}

	@Override
	public Ville updateVille(Ville ville) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.merge(ville);
			transaction.commit();
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.error(e.getMessage(), e);
			ville = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return ville;
	}

	@Override
	public Boolean removeVille(final Integer noVille) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Ville ville = null;
		try {
			ville = em.find(Ville.class, noVille);
			if (ville != null) {
				transaction.begin();
				em.remove(ville);
				transaction.commit();
			}
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.error(e.getMessage(), e);
			ville = null;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
		if (ville != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<Ville> getAllVille() {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Collection<Ville> listVille = null;
		try {
			listVille = em.createQuery(QUERY_ALL_VILLE, Ville.class).getResultList();
		} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			em.close();
		}
		return listVille;
	}
}
