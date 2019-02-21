package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import org.apache.log4j.Logger;

import fr.eni.sortir.dao.SortieDao;
import fr.eni.sortir.entities.Sortie;;

public class JpaSortieDao extends JpaDao implements SortieDao {
	private static final Logger LOGGER = Logger.getLogger(JpaSortieDao.class);
	private final String QUERY_SORTIE_ALL = "SELECT s FROM Sortie AS s";

	public JpaSortieDao(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	public Sortie addSortie(Sortie sortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(sortie);
			em.flush();
			transaction.commit();
		} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
			sortie = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return sortie;
	}

	@Override
	public Sortie findSortie(final Integer noSortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Sortie sortie = null;
		try {
			sortie = em.find(Sortie.class, noSortie);
		} catch (IllegalStateException | IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			em.close();
		}
		return sortie;
	}

	@Override
	public Sortie updateSortie(Sortie sortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.merge(sortie);
			transaction.commit();
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.error(e.getMessage(), e);
			sortie = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return sortie;
	}

	@Override
	public Boolean removeSortie(final Integer noSortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Sortie sortie = null;
		try {
			sortie = em.find(Sortie.class, noSortie);
			if (sortie != null) {
				transaction.begin();
				em.remove(sortie);
				transaction.commit();
			}
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.error(e.getMessage(), e);
			sortie = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		if (sortie != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<Sortie> getAllSortie() {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Collection<Sortie> listSortie = null;
		try {
			listSortie = em.createQuery(QUERY_SORTIE_ALL, Sortie.class).getResultList();
		} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			em.close();
		}
		return listSortie;
	}

}
