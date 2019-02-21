package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import fr.eni.sortir.dao.SortieDao;
import fr.eni.sortir.entities.Sortie;;

public class JpaSortieDao extends JpaDao implements SortieDao {
    private final String QUERY_SORTIE_ALL = "SELECT s FROM Sortie AS s";

    public JpaSortieDao(EntityManagerFactory emf) {
	super(emf);
    }

    @Override
    public Sortie addSortie(Sortie sortie) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.persist(sortie);
	    em.flush();
	    transaction.commit();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	Sortie sortie = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    sortie = em.find(Sortie.class, noSortie);
	} catch (IllegalStateException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return sortie;
    }

    @Override
    public Sortie updateSortie(Sortie sortie) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.merge(sortie);
	    transaction.commit();
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	EntityTransaction transaction = null;
	Sortie sortie = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    sortie = em.find(Sortie.class, noSortie);
	    transaction = em.getTransaction();
	    if (sortie != null) {
		transaction.begin();
		em.remove(sortie);
		transaction.commit();
	    }
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
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
	EntityManager em = null;
	Collection<Sortie> listSortie = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    listSortie = em.createQuery(QUERY_SORTIE_ALL, Sortie.class).getResultList();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return listSortie;
    }

}
