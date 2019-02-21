package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import fr.eni.sortir.dao.VilleDao;
import fr.eni.sortir.entities.Ville;

public class JpaVilleDao extends JpaDao implements VilleDao {
    private final String QUERY_ALL_VILLE = "SELECT v FROM Ville AS v";

    public JpaVilleDao(EntityManagerFactory emf) {
	super(emf);
    }

    @Override
    public Ville addVille(Ville ville) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.persist(ville);
	    em.flush();
	    transaction.commit();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	Ville ville = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    ville = em.find(Ville.class, noVille);
	} catch (IllegalStateException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return ville;
    }

    @Override
    public Ville updateVille(Ville ville) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.merge(ville);
	    transaction.commit();
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	EntityTransaction transaction = null;
	Ville ville = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    ville = em.find(Ville.class, noVille);
	    transaction = em.getTransaction();
	    if (ville != null) {
		transaction.begin();
		em.remove(ville);
		transaction.commit();
	    }
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	Collection<Ville> listVille = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    listVille = em.createQuery(QUERY_ALL_VILLE, Ville.class).getResultList();
	} finally {
	    em.close();
	}
	return listVille;
    }
}
