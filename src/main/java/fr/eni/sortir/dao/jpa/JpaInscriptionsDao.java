package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.EtatsDao;
import fr.eni.sortir.dao.InscriptionsDao;
import fr.eni.sortir.entities.Etats;
import fr.eni.sortir.entities.Inscriptions;

public class JpaInscriptionsDao extends JpaDao implements InscriptionsDao {
	public JpaInscriptionsDao(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	public Inscriptions addInscriptions(Inscriptions inscriptions) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(inscriptions);
			transaction.commit();
			em.flush();

			if ((inscriptions.getNoSortie() != 0) && (inscriptions.getNoParticipant() != 0)) {
				return inscriptions;
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
	public Inscriptions findInscriptions(Integer noInscriptions) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Inscriptions inscriptions = em.find(Inscriptions.class, noInscriptions);
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
	public Inscriptions updateInscriptions(Inscriptions inscriptions) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(inscriptions);
			transaction.commit();

			return inscriptions;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeInscriptions(Integer noInscriptions) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Etats etats = em.find(Etats.class, noInscriptions);
			
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
	public Collection<Inscriptions> getAllInscriptions() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT e FROM Inscriptions AS e", Inscriptions.class);

			Collection<Inscriptions> listInscriptions = query.getResultList();

			return listInscriptions;
		} finally {
			em.close();
		}
	}

}
