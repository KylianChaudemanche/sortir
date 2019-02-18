package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.SortiesDao;
import fr.eni.sortir.entities.Etats;
import fr.eni.sortir.entities.Sorties;;

public class JpaSortiesDao extends JpaDao implements SortiesDao{

	protected JpaSortiesDao(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Sorties addSorties(Sorties sorties) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(sorties);
			transaction.commit();
			em.flush();

			if (sorties.getNoSortie() != 0) {
				return sorties;
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
	public Sorties findSorties(Integer noSorties) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Sorties sorties = em.find(Sorties.class, noSorties);
		try {
			if (sorties != null) {
				return sorties;
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	@Override
	public Sorties updateSorties(Sorties sorties) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(sorties);
			transaction.commit();

			return sorties;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeSorties(Integer noSorties) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Sorties sorties = em.find(Sorties.class, noSorties);
			
			if (sorties != null) {
				transaction.begin();
				
				em.remove(sorties);

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
	public Collection<Sorties> getAllSorties() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT s FROM Sorties AS s", Sorties.class);

			Collection<Sorties> listSorties = query.getResultList();

			return listSorties;
		} finally {
			em.close();
		}
	}

}
