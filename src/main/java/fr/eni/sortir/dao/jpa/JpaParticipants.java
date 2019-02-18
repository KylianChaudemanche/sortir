package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.ParticipantsDao;
import fr.eni.sortir.entities.Etats;
import fr.eni.sortir.entities.Inscriptions;
import fr.eni.sortir.entities.Participants;

public class JpaParticipants extends JpaDao implements ParticipantsDao {

	protected JpaParticipants(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	public Participants addParticipants(Participants participants) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(participants);
			transaction.commit();
			em.flush();

			if (participants.getNoParticipant() != 0) {
				return participants;
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
	public Participants findParticipants(Integer noParticipants) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Participants participants = em.find(Participants.class, noParticipants);
		try {
			if (participants != null) {
				return participants;
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	@Override
	public Participants updateParticipants(Participants participants) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(participants);
			transaction.commit();

			return participants;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeParticipants(Integer noParticipants) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Participants participants = em.find(Participants.class, noParticipants);
			
			if (participants != null) {
				transaction.begin();
				
				em.remove(participants);

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
	public Collection<Participants> getAllParticipants() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT e FROM Participants AS e", Inscriptions.class);

			Collection<Participants> listParticipants = query.getResultList();

			return listParticipants;
		} finally {
			em.close();
		}
	}

}
