package fr.eni.sortir.dao.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import fr.eni.sortir.dao.InscriptionDao;
import fr.eni.sortir.entities.Inscription;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Sortie;

public class JpaInscriptionDao extends JpaDao implements InscriptionDao {
    private static final Logger LOGGER = Logger.getLogger(JpaInscriptionDao.class.getName());

    public JpaInscriptionDao(EntityManagerFactory emf) {
	super(emf);
    }

    @Override
    public Boolean removeInscription(Inscription inscription) {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	EntityTransaction transaction = em.getTransaction();
	int result = 0;
	try {
	    if (inscription != null) {
		transaction.begin();
		Query q = em.createQuery("delete Inscription where participant.noParticipant = :noParticipant AND sortie.noSortie = :noSortie")
			.setParameter("noParticipant", inscription.getParticipant().getNoParticipant())
			.setParameter("noSortie",inscription.getSortie().getNoSortie());
		result = q.executeUpdate();
		transaction.commit();
	    }
	} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	    result = 0;
	} finally {
	    if (transaction.isActive()) {
		transaction.rollback();
	    }
	    em.close();
	}
	if (result != 0) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public Inscription findInscription(Participant participant, Sortie sortie) {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	Inscription inscription = null;
	try {
	    inscription = em.createQuery(
		    "From Inscription as i WHERE i.participant.noParticipant = :noParticipant AND i.sortie.noSortie = :noSortie",
		    Inscription.class).setParameter("noParticipant", participant.getNoParticipant())
		    .setParameter("noSortie", sortie.getNoSortie()).getSingleResult();
	} catch (IllegalStateException | IllegalArgumentException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	} finally {
	    em.close();
	}
	return inscription;
    }
}
