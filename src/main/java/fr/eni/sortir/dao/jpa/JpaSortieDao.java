package fr.eni.sortir.dao.jpa;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.dao.SortieDao;
import fr.eni.sortir.entities.Etat;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Site;
import fr.eni.sortir.entities.Sortie;
import fr.eni.sortir.utils.State;

public class JpaSortieDao extends JpaDao implements SortieDao {
    private static final Logger LOGGER = Logger.getLogger(JpaSortieDao.class.getName());
    private final String QUERY_SORTIE_ALL = "SELECT s FROM Sortie AS s";
    private final String QUERY_SORTIE_WHERE_BY_SITE = " WHERE s.organisateur.site.noSite = :noSite";
    private final String QUERY_SORTIE_AND_BY_ORGANISATEUR = " AND s.organisateur.noParticipant = :noParticipant";
    private final String QUERY_SORTIE_WHERE_BY_INSCRIT = " WHERE i.participant.noParticipant = :noParticipant";
    private final String QUERY_SORTIE_AND_BY_INSCRIT = " AND i.participant.noParticipant = :noParticipant";
    private final String QUERY_SORTIE_JOIN_BY_INSCRIT = " INNER JOIN s.inscriptions AS i";
    private final String QUERY_SORTIE_AND_BY_PAS_INSCRIT = " AND s.noSortie NOT IN(SELECT sortie.noSortie FROM Sortie as sortie INNER JOIN sortie.inscriptions as i WHERE i.participant.noParticipant = :noParticipant)";
    private final String QUERY_SORTIE_AND_PASSEE = " AND s.dateDebut < :today";
    private final String QUERY_SORTIE_AND_BY_DATE_DEBUT= " AND s.dateDebut >= :dateDebut";
    private final String QUERY_SORTIE_AND_BY_DATE_FIN= " AND s.dateDebut <= :dateFin";
    private final String QUERY_SORTIE_AND_BY_DATE_ARCHIVAGE = " AND s.dateDebut >= :dateArchivage";    
    private final String QUERY_SORTIE_WHERE_BY_DATE_ARCHIVAGE = " WHERE s.dateDebut >= :dateArchivage";    
    private final String QUERY_SORTIE_WHERE_ONLY_CREATE = " AND s.noSortie NOT IN (SELECT noSortie FROM Sortie WHERE etat.libelle = :etat AND organisateur.noParticipant != :organisateur AND dateDebut >= :dateArchivage)";

	private Date today = new Date();
	private Date dateArchivage = new Date();

	public JpaSortieDao(EntityManagerFactory emf) {
		super(emf);

		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DATE, -30);
		dateArchivage = c.getTime();
	}

	@Override
	public Sortie addSortie(Sortie sortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(sortie);
			em.flush();
			transaction.commit();
		} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			sortie = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return sortie;
	}

	@Override
	public Sortie findSortie(final Integer noSortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Sortie sortie = null;
		try {
			sortie = em.find(Sortie.class, noSortie);
		} catch (IllegalStateException | IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			em.close();
		}
		return sortie;
	}

	@Override
	public Sortie updateSortie(Sortie sortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.merge(sortie);
			transaction.commit();
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			sortie = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return sortie;
	}

	@Override
	public Boolean removeSortie(final Integer noSortie) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Sortie sortie = null;
		try {
			sortie = em.find(Sortie.class, noSortie);
			if (sortie != null) {
				transaction.begin();
				em.remove(sortie);
				transaction.commit();
			}
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			sortie = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return true;
	}

	@Override

	public Collection<Sortie> getAllSortie(Participant participant) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Collection<Sortie> listeSortie = null;
		try {
			TypedQuery<Sortie> query = em.createQuery("SELECT s FROM Sortie AS s"+QUERY_SORTIE_WHERE_BY_DATE_ARCHIVAGE+QUERY_SORTIE_WHERE_ONLY_CREATE , Sortie.class)
					.setParameter("dateArchivage", dateArchivage)
					.setParameter("etat", State.CREATED.toString())
					.setParameter("organisateur", participant.getNoParticipant());

			listeSortie = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			em.close();
		}
		return listeSortie;
	}

	@Override
	public Collection<Sortie> getAllSortieFiltre(Site site, Boolean organisateur, Boolean inscrit, 
			Boolean pasInscrit, Boolean passee, Date dateDebut, Date dateFin, Participant participant) {
		Collection<Sortie> listeSortie = null;

		/* Construction de la requête dynamique */
		String queryOrganisateur = (organisateur) ? QUERY_SORTIE_AND_BY_ORGANISATEUR : "";
		String queryInscrit = "";
		String queryJoin = "";
		String queryPasInscrit = "";
		if(inscrit ^ pasInscrit) {
			queryInscrit = (inscrit) ? QUERY_SORTIE_AND_BY_INSCRIT : "";
			queryJoin = (inscrit) ? QUERY_SORTIE_JOIN_BY_INSCRIT : "";
			queryPasInscrit= (pasInscrit) ? QUERY_SORTIE_AND_BY_PAS_INSCRIT : "";
		}
		String queryPassee = (passee) ? QUERY_SORTIE_AND_PASSEE : "";
		String queryDateDebut = (dateDebut != null) ? QUERY_SORTIE_AND_BY_DATE_DEBUT : "";
		String queryDateFin = (dateFin != null) ? QUERY_SORTIE_AND_BY_DATE_FIN : "";

		EntityManager em = getEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Sortie> query = em.createQuery(QUERY_SORTIE_ALL+ queryJoin+QUERY_SORTIE_WHERE_BY_SITE+queryOrganisateur
					+queryInscrit+queryPasInscrit+queryPassee+queryDateDebut+queryDateFin+QUERY_SORTIE_AND_BY_DATE_ARCHIVAGE
					,Sortie.class)
					.setParameter("noSite", site.getNoSite())
					.setParameter("dateArchivage", dateArchivage);

			if(organisateur || (inscrit ^ pasInscrit) ) {
				query.setParameter("noParticipant", participant.getNoParticipant());
			}
			if(passee) {
				query.setParameter("today", today);
			}
			if(dateDebut != null) {
				query.setParameter("dateDebut", dateDebut);
			}
			if(dateFin != null) {
				query.setParameter("dateFin", dateFin);
			}

			listeSortie = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listeSortie;
	}

	@Override
	public Collection<Sortie> getAllSortieInscrit(Participant participant) {
		Collection<Sortie> listeSortie = null;
		EntityManager em = getEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Sortie> query = em.createQuery(QUERY_SORTIE_ALL+QUERY_SORTIE_JOIN_BY_INSCRIT+
					QUERY_SORTIE_WHERE_BY_INSCRIT+QUERY_SORTIE_AND_BY_DATE_ARCHIVAGE
					,Sortie.class)
					.setParameter("dateArchivage", dateArchivage)
					.setParameter("noParticipant", participant.getNoParticipant());
			listeSortie = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listeSortie;
	}

	public int closeInscription() {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		int result = 0;
		try {
			transaction.begin();
			Etat etatOpened = DaoFactory.getEtatDao().findEtatByName(State.OPENED.toString());
			Etat etatClosed = DaoFactory.getEtatDao().findEtatByName(State.CLOSED.toString());
			Query query = em
					.createQuery(
							"UPDATE Sortie SET etat = :etatClosed WHERE dateCloture < :date AND etat = :etatOpened")
					.setParameter("etatClosed", etatClosed).setParameter("date", cal.getTime())
					.setParameter("etatOpened", etatOpened);

			result = query.executeUpdate();
			transaction.commit();
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			result = 0;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return result;
	}
}
