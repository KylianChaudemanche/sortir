package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.ParticipantsDao;
import fr.eni.sortir.entities.Etat;
import fr.eni.sortir.entities.Inscription;
import fr.eni.sortir.entities.Participant;

public class JpaParticipants extends JpaDao implements ParticipantsDao {

	protected JpaParticipants(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	public Participant addParticipant(Participant participant) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(participant);
			transaction.commit();
			em.flush();

			if (participant.getNoParticipant() != 0) {
				return participant;
			} else {
				return null;
			}
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Participant findParticipant(Integer noParticipant) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Participant participant = em.find(Participant.class, noParticipant);
		try {
			if (participant != null) {
				return participant;
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	@Override
	public Participant updateParticipant(Participant participant) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(participant);
			transaction.commit();

			return participant;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeParticipant(Integer noParticipant) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Participant participant = em.find(Participant.class, noParticipant);
			
			if (participant != null) {
				transaction.begin();
				
				em.remove(participant);

				transaction.commit();
				
				return true;
			} else {
				return false;
			}
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Collection<Participant> getAllParticipant() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT p FROM Participant AS p", Inscription.class);

			Collection<Participant> listParticipant = query.getResultList();

			return listParticipant;
		} finally {
			em.close();
		}
	}

}
