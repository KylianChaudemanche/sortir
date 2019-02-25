package fr.eni.sortir.dao.jpa;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import fr.eni.sortir.dao.EtatDao;
import fr.eni.sortir.entities.Etat;

public class JpaEtatDao extends JpaDao implements EtatDao {
    private static final Logger LOGGER = Logger.getLogger(JpaEtatDao.class.getName());
    private static final String QUERY_ETAT_ALL = "SELECT e FROM Etat AS e";
    private static final String QUERY_ETAT_BY_NAME = "SELECT e FROM Etat AS e WHERE libelle = :libelle";
    private static final String LIBELLE = "libelle";

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
	    em.flush();
	    transaction.commit();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	    etat = null;
	} finally {
	    if (transaction.isActive()) {
		transaction.rollback();
	    }
	    em.close();
	}
	return etat;
    }

    @Override
    public Etat findEtat(final Integer noEtat) {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	Etat etat = null;
	try {
	    etat = em.find(Etat.class, noEtat);
	} catch (IllegalStateException | IllegalArgumentException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	} finally {
	    em.close();
	}
	return etat;
    }

    @Override
    public Etat updateEtat(Etat etat) {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	EntityTransaction transaction = em.getTransaction();
	try {
	    transaction.begin();
	    em.merge(etat);
	    transaction.commit();
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	    etat = null;
	} finally {
	    if (transaction.isActive()) {
		transaction.rollback();
	    }
	    em.close();
	}
	return etat;
    }

    @Override
    public Boolean removeEtat(final Integer noEtat) {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	EntityTransaction transaction = em.getTransaction();
	Etat etat = null;
	try {
	    etat = em.find(Etat.class, noEtat);
	    if (etat != null) {
		transaction.begin();
		em.remove(etat);
		transaction.commit();
	    }
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	    etat = null;
	} finally {
	    if (transaction.isActive()) {
		transaction.rollback();
	    }
	    em.close();
	}
	if (etat != null) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public Collection<Etat> getAllEtat() {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	;
	Collection<Etat> listEtat = null;
	try {
	    listEtat = em.createQuery(QUERY_ETAT_ALL, Etat.class).getResultList();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	} finally {
	    em.close();
	}
	return listEtat;
    }

    @Override
    public Etat findEtatByName(final String name) {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	Etat etat = null;
	try {
	    etat = em.createQuery(QUERY_ETAT_BY_NAME, Etat.class).setParameter(LIBELLE, name).getSingleResult();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	} finally {
	    em.close();
	}
	return etat;
    }
}
