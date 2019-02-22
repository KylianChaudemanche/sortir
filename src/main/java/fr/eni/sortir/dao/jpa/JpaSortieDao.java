package fr.eni.sortir.dao.jpa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.eni.sortir.dao.SortieDao;
import fr.eni.sortir.entities.Etat;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Site;
import fr.eni.sortir.entities.Sortie;;

public class JpaSortieDao extends JpaDao implements SortieDao{

	public JpaSortieDao(EntityManagerFactory emf) {
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
		Collection<Sortie> listeSortie = null;
		try {
			TypedQuery<Sortie> query = em.createQuery("SELECT s FROM Sortie AS s", Sortie.class);

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
		Date today = new Date();

		/* Construction de la requête dynamique */
		String queryOrganisateur = (organisateur) ? " AND s.organisateur.noParticipant = :no_participant" : "";
		String queryInscrit = (inscrit) ? " AND i.participant.noParticipant = :no_participant" : "";
		String queryPasInscrit= (pasInscrit) ? " AND i.participant.noParticipant != :no_participant" : "";
		String queryPassee = (passee) ? " AND s.dateDebut < :today" : "";
		String queryDateDebut = (dateDebut != null) ? " AND s.dateDebut > :dateDebut" : "";
		String queryDateFin = (dateFin != null) ? " AND s.dateDebut < :dateFin" : "";

		EntityManager em = getEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Sortie> query = em.createQuery("SELECT s FROM Sortie AS s"
					//+ " INNER JOIN fetch s.inscriptions AS i"
					+ " WHERE s.organisateur.site.noSite = :noSite"
					+queryOrganisateur+queryInscrit+queryPasInscrit
					+queryPassee+queryDateDebut+queryDateFin
					,Sortie.class)
					.setParameter("noSite", site.getNoSite());

			if(organisateur || inscrit || pasInscrit) {
				query.setParameter("no_participant", participant.getNoParticipant());
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

}
