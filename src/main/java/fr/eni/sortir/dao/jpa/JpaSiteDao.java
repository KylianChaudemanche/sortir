package fr.eni.sortir.dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.eni.sortir.dao.SiteDao;
import fr.eni.sortir.entities.Inscription;
import fr.eni.sortir.entities.Site;

public class JpaSiteDao extends JpaDao implements SiteDao  {

	public JpaSiteDao(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	public Site addSite(Site site) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(site);
			transaction.commit();
			em.flush();

			if (site.getNoSite() != 0) {
				return site;
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
	public Site findSite(Integer noSite) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Site site = em.find(Site.class, noSite);
		try {
			if (site != null) {
				return site;
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	@Override
	public Site updateSite(Site site) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(site);
			transaction.commit();

			return site;
		} finally {
			if (transaction.isActive())
				transaction.rollback();
			em.close();
		}
	}

	@Override
	public Boolean removeSite(Integer noSite) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			Site site = em.find(Site.class, noSite);
			
			if (site != null) {
				transaction.begin();
				
				em.remove(site);

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

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Site> getAllSite() {
		EntityManager em = getEntityManagerFactory().createEntityManager();

		try {
			Query query = em.createQuery("SELECT s FROM Sites AS s", Inscription.class);

			Collection<Site> listSite = query.getResultList();

			return listSite;
		} finally {
			em.close();
		}
	}

}
