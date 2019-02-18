package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.LieuxDao;
import fr.eni.sortir.entities.Lieux;

public class JpaLieuxDao extends JpaDao implements LieuxDao{
	
	protected JpaLieuxDao(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Lieux addLieux(Lieux lieux) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(lieux);
			transaction.commit();
			em.flush();

			if (lieux.getNoLieux() != 0) {
				return lieux;
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
	public Lieux findLieux(Integer noLieux) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Lieux lieux = em.find(Lieux.class, noLieux);
		try {
			if (lieux != null) {
				return lieux;
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	@Override
	public Lieux updateLieux(Lieux lieux) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(lieux);
			transaction.commit();

			return lieux;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeLieux(Integer noLieux) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Lieux lieux = em.find(Lieux.class, noLieux);
			
			if (lieux != null) {
				transaction.begin();
				
				em.remove(lieux);

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
	public Collection<Lieux> getAllLieux() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT v FROM Lieux AS v", Lieux.class);

			Collection<Lieux> listLieux = query.getResultList();

			return listLieux;
		} finally {
			em.close();
		}
	}
}
