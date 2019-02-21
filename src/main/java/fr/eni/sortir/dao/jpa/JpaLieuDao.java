package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import fr.eni.sortir.dao.LieuDao;
import fr.eni.sortir.entities.Lieu;

public class JpaLieuDao extends JpaDao implements LieuDao {
    private final String QUERY_LIEU_ALL = "SELECT l FROM Lieu AS l";

    public JpaLieuDao(EntityManagerFactory emf) {
	super(emf);
    }

    @Override
    public Lieu addLieu(Lieu lieu) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.persist(lieu);
	    em.flush();
	    transaction.commit();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	Lieu lieu = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    lieu = em.find(Lieu.class, noLieu);
	} catch (IllegalStateException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return lieu;
    }

    @Override
    public Lieu updateLieu(Lieu lieu) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.merge(lieu);
	    transaction.commit();
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	EntityTransaction transaction = null;
	Lieu lieu = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    lieu = em.find(Lieu.class, noLieu);
	    transaction = em.getTransaction();
	    if (lieu != null) {
		transaction.begin();
		em.remove(lieu);
		transaction.commit();
	    }
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	Collection<Lieu> listLieu = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    listLieu = em.createQuery(QUERY_LIEU_ALL, Lieu.class).getResultList();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return listLieu;
    }
}
