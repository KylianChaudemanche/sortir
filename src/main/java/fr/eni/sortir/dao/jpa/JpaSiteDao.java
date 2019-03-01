package fr.eni.sortir.dao.jpa;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import fr.eni.sortir.dao.SiteDao;
import fr.eni.sortir.entities.Site;

public class JpaSiteDao extends JpaDao implements SiteDao {
	private static final Logger LOGGER = Logger.getLogger(JpaSiteDao.class.getName());
	private final String QUERY_SITE_ALL = "SELECT s FROM Site AS s";

	public JpaSiteDao(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	public Site addSite(Site site) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(site);
			em.flush();
			transaction.commit();
		} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			site = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return site;
	}

	@Override
	public Site findSite(final Integer noSite) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Site site = null;
		try {
			site = em.find(Site.class, noSite);
		} catch (IllegalStateException | IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			em.close();
		}
		return site;
	}

	@Override
	public Site updateSite(Site site) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.merge(site);
			transaction.commit();
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			site = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return site;
	}

	@Override
	public Boolean removeSite(final Integer noSite) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Site site = null;
		try {
			site = em.find(Site.class, noSite);
			if (site != null) {
				transaction.begin();
				em.remove(site);
				transaction.commit();
			}
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			site = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		if (site != null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Collection<Site> getAllSite() {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Collection<Site> listSite = null;
		try {
			listSite = em.createQuery(QUERY_SITE_ALL, Site.class).getResultList();
		} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			em.close();
		}
		return listSite;
	}

}
