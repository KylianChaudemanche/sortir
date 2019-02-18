package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.SitesDao;
import fr.eni.sortir.entities.Inscriptions;
import fr.eni.sortir.entities.Participants;
import fr.eni.sortir.entities.Sites;

public class JpaSitesDao extends JpaDao implements SitesDao  {

	protected JpaSitesDao(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Sites addSites(Sites sites) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(sites);
			transaction.commit();
			em.flush();

			if (sites.getNoSite() != 0) {
				return sites;
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
	public Sites findSites(Integer noSites) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Sites sites = em.find(Sites.class, noSites);
		try {
			if (sites != null) {
				return sites;
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	@Override
	public Sites updateSites(Sites sites) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(sites);
			transaction.commit();

			return sites;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeSites(Integer noSites) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Sites sites = em.find(Sites.class, noSites);
			
			if (sites != null) {
				transaction.begin();
				
				em.remove(sites);

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
	public Collection<Sites> getAllSites() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT e FROM Participants AS e", Inscriptions.class);

			Collection<Sites> listSites = query.getResultList();

			return listSites;
		} finally {
			em.close();
		}
	}

}
