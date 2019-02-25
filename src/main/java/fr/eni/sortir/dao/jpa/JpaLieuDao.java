package fr.eni.sortir.dao.jpa;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import fr.eni.sortir.dao.LieuDao;
import fr.eni.sortir.entities.Lieu;

public class JpaLieuDao extends JpaDao implements LieuDao {
    private static final Logger LOGGER = Logger.getLogger(JpaLieuDao.class.getName());
    private final String QUERY_LIEU_ALL = "SELECT l FROM Lieu AS l";

    public JpaLieuDao(EntityManagerFactory emf) {
	super(emf);
    }

    @Override
    public Lieu addLieu(Lieu lieu) {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	EntityTransaction transaction = em.getTransaction();
	try {
	    transaction.begin();
	    em.persist(lieu);
	    em.flush();
	    transaction.commit();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	    lieu = null;
	} finally {
	    if (transaction.isActive()) {
		transaction.rollback();
	    }
	    em.close();
	}
	return lieu;
    }

    @Override
    public Lieu findLieu(final Integer noLieu) {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	Lieu lieu = null;
	try {
	    lieu = em.find(Lieu.class, noLieu);
	} catch (IllegalStateException | IllegalArgumentException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	} finally {
	    em.close();
	}
	return lieu;
    }

    @Override
    public Lieu updateLieu(Lieu lieu) {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	EntityTransaction transaction = em.getTransaction();
	try {
	    transaction.begin();
	    em.merge(lieu);
	    transaction.commit();
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	    lieu = null;
	} finally {
	    if (transaction.isActive()) {
		transaction.rollback();
	    }
	    em.close();
	}
	return lieu;
    }

    @Override
    public Boolean removeLieu(final Integer noLieu) {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	EntityTransaction transaction = em.getTransaction();
	Lieu lieu = null;
	try {
	    lieu = em.find(Lieu.class, noLieu);
	    if (lieu != null) {
		transaction.begin();
		em.remove(lieu);
		transaction.commit();
	    }
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	    lieu = null;
	} finally {
	    if (transaction.isActive()) {
		transaction.rollback();
	    }
	    em.close();
	}
	if (lieu != null) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public Collection<Lieu> getAllLieu() {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	Collection<Lieu> listLieu = null;
	try {
	    listLieu = em.createQuery(QUERY_LIEU_ALL, Lieu.class).getResultList();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	} finally {
	    em.close();
	}
	return listLieu;
    }
}
