package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.LieuxDao;
import fr.eni.sortir.entities.Lieu;

public class JpaLieuxDao extends JpaDao implements LieuxDao{
	
	protected JpaLieuxDao(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Lieu addLieu(Lieu lieu) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(lieu);
			transaction.commit();
			em.flush();

			if (lieu.getNoLieu() != 0) {
				return lieu;
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
	public Lieu findLieu(Integer noLieu) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Lieu lieu = em.find(Lieu.class, noLieu);
		try {
			if (lieu != null) {
				return lieu;
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	@Override
	public Lieu updateLieu(Lieu lieu) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(lieu);
			transaction.commit();

			return lieu;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeLieu(Integer noLieu) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Lieu lieu = em.find(Lieu.class, noLieu);
			
			if (lieu != null) {
				transaction.begin();
				
				em.remove(lieu);

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
	public Collection<Lieu> getAllLieu() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT l FROM Lieu AS l", Lieu.class);

			Collection<Lieu> listLieu = query.getResultList();

			return listLieu;
		} finally {
			em.close();
		}
	}
}
