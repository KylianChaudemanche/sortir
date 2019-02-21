package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import fr.eni.sortir.dao.SiteDao;
import fr.eni.sortir.entities.Site;

public class JpaSiteDao extends JpaDao implements SiteDao {
    private final String QUERY_SITE_ALL = "SELECT s FROM Site AS s";

    public JpaSiteDao(EntityManagerFactory emf) {
	super(emf);
    }

    @Override
    public Site addSite(Site site) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.persist(site);
	    em.flush();
	    transaction.commit();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	Site site = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    site = em.find(Site.class, noSite);
	} catch (IllegalStateException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return site;
    }

    @Override
    public Site updateSite(Site site) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.merge(site);
	    transaction.commit();
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	EntityTransaction transaction = null;
	Site site = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    site = em.find(Site.class, noSite);
	    transaction = em.getTransaction();
	    if (site != null) {
		transaction.begin();
		em.remove(site);
		transaction.commit();
	    }
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	Collection<Site> listSite = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    listSite = em.createQuery(QUERY_SITE_ALL, Site.class).getResultList();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return listSite;
    }

}
