package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import fr.eni.sortir.dao.ParticipantDao;
import fr.eni.sortir.entities.Participant;

public class JpaParticipantDao extends JpaDao implements ParticipantDao {
    private final String QUERY_PARTICIPANT_ALL = "SELECT p FROM Participant AS p";
    private final String QUERY_PARTICIPANT_BY_MAIL = "SELECT p FROM Participant AS p WHERE mail = :mail";
    private final String MAIL = "mail";

    public JpaParticipantDao(EntityManagerFactory emf) {
	super(emf);
    }

    @Override
    public Participant addParticipant(Participant participant) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.persist(participant);
	    em.flush();
	    transaction.commit();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
	    participant = null;
	} finally {
	    if (transaction.isActive()) {
		transaction.rollback();
	    }
	    em.close();
	}
	return participant;
    }

    @Override
    public Participant findParticipant(final Integer noParticipant) {
	EntityManager em = null;
	Participant participant = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    participant = em.find(Participant.class, noParticipant);
	} catch (IllegalStateException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return participant;
    }

    @Override
    public Participant updateParticipant(Participant participant) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    transaction = em.getTransaction();
	    transaction.begin();
	    em.merge(participant);
	    transaction.commit();
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
	    participant = null;
	} finally {
	    if (transaction.isActive()) {
		transaction.rollback();
	    }
	    em.close();
	}
	return participant;
    }

    @Override
    public Boolean removeParticipant(final Integer noParticipant) {
	EntityManager em = null;
	EntityTransaction transaction = null;
	Participant participant = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    participant = em.find(Participant.class, noParticipant);
	    transaction = em.getTransaction();
	    if (participant != null) {
		transaction.begin();
		em.remove(participant);
		transaction.commit();
	    }
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    e.printStackTrace();
	    participant = null;
	} finally {
	    if (transaction.isActive()) {
		transaction.rollback();
	    }
	    em.close();
	}
	if (participant != null) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public Collection<Participant> getAllParticipant() {
	EntityManager em = null;
	Collection<Participant> listParticipant = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    listParticipant = em.createQuery(QUERY_PARTICIPANT_ALL, Participant.class).getResultList();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return listParticipant;
    }

    @Override
    public Participant findParticipantByMail(String mail) {
	EntityManager em = null;
	Participant participant = null;
	try {
	    em = getEntityManagerFactory().createEntityManager();
	    participant = em.createQuery(QUERY_PARTICIPANT_BY_MAIL, Participant.class).setParameter(MAIL, mail)
		    .getSingleResult();
	} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return participant;
    }

}
