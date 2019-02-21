package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import fr.eni.sortir.dao.EtatDao;
import fr.eni.sortir.entities.Etat;

public class JpaEtatDao extends JpaDao implements EtatDao {
    private final String QUERY_ETAT_ALL = "SELECT e FROM Etats AS e"; 
    private final String QUERY_ETAT_BY_NAME = "SELECT e FROM Etat AS e WHERE libelle = :libelle";
    private final String LIBELLE = "libelle";
    
    public JpaEtatDao(EntityManagerFactory emf) {
	super(emf);
    }

    @Override
    public Etat addEtat(Etat etat) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.persist(etat);
	    em.flush();
	    transaction.commit();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	Etat etat = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    etat = em.find(Etat.class, noEtat);
	} catch (IllegalStateException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return etat;
    }

    @Override
    public Etat updateEtat(Etat etat) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.merge(etat);
	    transaction.commit();
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	EntityTransaction transaction = null;
	Etat etat = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    etat = em.find(Etat.class, noEtat);
	    transaction = em.getTransaction();
	    if (etat != null) {
		transaction.begin();
		em.remove(etat);
		transaction.commit();
	    }
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	Collection<Etat> listEtat = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    listEtat = em.createQuery(QUERY_ETAT_ALL, Etat.class)
		    .getResultList();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return listEtat;
    }

    @Override
    public Etat findEtatByName(final String name) {
	EntityManager em = null;
	Etat etat = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    etat = em.createQuery(QUERY_ETAT_BY_NAME, Etat.class)
		    .setParameter(LIBELLE, name)
		    .getSingleResult();
	} catch(IllegalStateException | PersistenceException | IllegalArgumentException e) {
	   e.printStackTrace(); 
	} finally {
	    em.close();
	}
	return etat;
    }
}
