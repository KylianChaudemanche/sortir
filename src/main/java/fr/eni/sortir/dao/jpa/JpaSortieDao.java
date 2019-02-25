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
import fr.eni.sortir.utils.State;;

public class JpaSortieDao extends JpaDao implements SortieDao {
    private static final Logger LOGGER = Logger.getLogger(JpaSortieDao.class.getName());
    private final String QUERY_SORTIE_ALL = "SELECT s FROM Sortie AS s";

    public JpaSortieDao(EntityManagerFactory emf) {
	super(emf);
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
    public Collection<Sortie> getAllSortie() {
	EntityManager em = getEntityManagerFactory().createEntityManager();
	Collection<Sortie> listeSortie = null;
	try {
	    TypedQuery<Sortie> query = em.createQuery(QUERY_SORTIE_ALL, Sortie.class);

	    listeSortie = query.getResultList();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    em.close();
	}
	return listeSortie;
    }

    @Override
    public Collection<Sortie> getAllSortieFiltre(Site site, Boolean organisateur, Boolean inscrit, Boolean pasInscrit,
	    Boolean passee, Date dateDebut, Date dateFin, Participant participant) {
	Collection<Sortie> listeSortie = null;
	Date today = new Date();

	/* Construction de la requête dynamique */
	String queryOrganisateur = (organisateur) ? " AND s.organisateur.noParticipant = :no_participant" : "";
	String queryInscrit = (inscrit) ? " AND i.participant.noParticipant = :no_participant" : "";
	String queryPasInscrit = (pasInscrit) ? " AND i.participant.noParticipant != :no_participant" : "";
	String queryPassee = (passee) ? " AND s.dateDebut < :today" : "";
	String queryDateDebut = (dateDebut != null) ? " AND s.dateDebut > :dateDebut" : "";
	String queryDateFin = (dateFin != null) ? " AND s.dateDebut < :dateFin" : "";

	EntityManager em = getEntityManagerFactory().createEntityManager();
	try {
	    TypedQuery<Sortie> query = em.createQuery("SELECT s FROM Sortie AS s"
		    // + " INNER JOIN fetch s.inscriptions AS i"
		    + " WHERE s.organisateur.site.noSite = :noSite" + queryOrganisateur + queryInscrit + queryPasInscrit
		    + queryPassee + queryDateDebut + queryDateFin, Sortie.class)
		    .setParameter("noSite", site.getNoSite());

	    if (organisateur || inscrit || pasInscrit) {
		query.setParameter("no_participant", participant.getNoParticipant());
	    }
	    if (passee) {
		query.setParameter("today", today);
	    }
	    if (dateDebut != null) {
		query.setParameter("dateDebut", dateDebut);
	    }
	    if (dateFin != null) {
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
