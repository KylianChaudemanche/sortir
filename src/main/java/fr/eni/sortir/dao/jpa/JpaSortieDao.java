package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.SortieDao;
import fr.eni.sortir.entities.Etat;
import fr.eni.sortir.entities.Sortie;;

public class JpaSortieDao extends JpaDao implements SortieDao{

	protected JpaSortieDao(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Sortie addSortie(Sortie sortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(sortie);
			transaction.commit();
			em.flush();

			if (sortie.getNoSortie() != 0) {
				return sortie;
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
	public Sortie findSortie(Integer noSortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Sortie sortie = em.find(Sortie.class, noSortie);
		try {
			if (sortie != null) {
				return sortie;
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	@Override
	public Sortie updateSortie(Sortie sortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(sortie);
			transaction.commit();

			return sortie;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeSortie(Integer noSortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Sortie sortie = em.find(Sortie.class, noSortie);
			
			if (sortie != null) {
				transaction.begin();
				
				em.remove(sortie);

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
	public Collection<Sortie> getAllSortie() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT s FROM Sortie AS s", Sortie.class);

			Collection<Sortie> listSortie = query.getResultList();

			return listSortie;
		} finally {
			em.close();
		}
	}

}
