package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.EtatDao;
import fr.eni.sortir.dao.InscriptionDao;
import fr.eni.sortir.entities.Etat;
import fr.eni.sortir.entities.Inscription;

public class JpaInscriptionDao extends JpaDao implements InscriptionDao {
	public JpaInscriptionDao(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	public Inscription addInscription(Inscription inscription) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(inscription);
			transaction.commit();
			em.flush();

			if ((inscription.getSortie() != null) && (inscription.getParticipant() != null)) {
				return inscription;
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
	public Inscription findInscription(Integer noInscription) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Inscription inscriptions = em.find(Inscription.class, noInscription);
		try {
			if (inscriptions != null) {
				return inscriptions;
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	@Override
	public Inscription updateInscription(Inscription inscription) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(inscription);
			transaction.commit();

			return inscription;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeInscription(Integer noInscription) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Etat etats = em.find(Etat.class, noInscription);
			
			if (etats != null) {
				transaction.begin();
				
				em.remove(etats);

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
	public Collection<Inscription> getAllInscription() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT i FROM Inscription AS i", Inscription.class);

			Collection<Inscription> listInscriptions = query.getResultList();

			return listInscriptions;
		} finally {
			em.close();
		}
	}

}
