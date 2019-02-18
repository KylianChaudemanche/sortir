package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.SortiesDao;
import fr.eni.sortir.dao.VillesDao;
import fr.eni.sortir.entities.Villes;

public class JpaVillesDao extends JpaDao implements VillesDao {

	protected JpaVillesDao(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Villes addVilles(Villes villes) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(villes);
			transaction.commit();
			em.flush();

			if (villes.getNoVille() != 0) {
				return villes;
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
	public Villes findVilles(Integer noVilles) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Villes villes = em.find(Villes.class, noVilles);
		try {
			if (villes != null) {
				return villes;
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	@Override
	public Villes updateVilles(Villes villes) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(villes);
			transaction.commit();

			return villes;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeVilles(Integer noVilles) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Villes villes = em.find(Villes.class, noVilles);
			
			if (villes != null) {
				transaction.begin();
				
				em.remove(villes);

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
	public Collection<Villes> getAllVilles() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT v FROM Villes AS v", Villes.class);

			Collection<Villes> listVilles = query.getResultList();

			return listVilles;
		} finally {
			em.close();
		}
	}
}
