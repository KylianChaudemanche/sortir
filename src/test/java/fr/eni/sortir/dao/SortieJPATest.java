package fr.eni.sortir.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.eni.sortir.PersistenceTest;
import fr.eni.sortir.entities.Etat;
import fr.eni.sortir.entities.Lieu;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Site;
import fr.eni.sortir.entities.Sortie;
import fr.eni.sortir.entities.Ville;

public class SortieJPATest extends PersistenceTest {
    private static final Logger LOGGER = Logger.getLogger(SortieJPATest.class.getName());

    public void testPersistence() {
	try {
	    // Init values
	    Etat etat = new Etat("Test", new ArrayList<>());
	    DaoFactory.getEtatDao().addEtat(etat);
	    Ville ville = new Ville("Test", "35213", new ArrayList<>());
	    DaoFactory.getVilleDao().addVille(ville);
	    Lieu lieu = new Lieu("Test", "Test", 5.2356f, 7.2356f, ville, new ArrayList<>());
	    DaoFactory.getLieuDao().addLieu(lieu);
	    Site site = new Site("Test", new ArrayList<>());
	    DaoFactory.getSiteDao().addSite(site);
	    Participant organisateur = new Participant("Test", "Test", "Test", "Test", "Test", "Test", true, true,
		    new ArrayList<>(), site, new ArrayList<>());
	    DaoFactory.getParticipantDao().addParticipant(organisateur);
	    Sortie sortie = new Sortie("Test", new Date(), 60, new Date(), 50, "Test", null, etat, lieu,
		    new ArrayList<>(), organisateur);

	    // Test insertion
	    DaoFactory.getSortieDao().addSortie(sortie);
	    assertTrue(DaoFactory.getSortieDao().findSortie(sortie.getNoSortie()) != null);

	    // Test update
	    Sortie sortieUpdate = DaoFactory.getSortieDao().findSortie(sortie.getNoSortie());
	    sortieUpdate.setNom("Test2");
	    DaoFactory.getSortieDao().updateSortie(sortieUpdate);
	    assertTrue(!sortie.equals(DaoFactory.getSortieDao().findSortie(sortie.getNoSortie())));

	    // Test get all entities
	    assertTrue(DaoFactory.getSortieDao().getAllSortie().size() == 1);

	    // Test deletion
	    DaoFactory.getSortieDao().removeSortie(sortie.getNoSortie());
	    assertTrue(DaoFactory.getSortieDao().findSortie(sortie.getNoSortie()) == null);
	} catch (Exception e) {
	    em.getTransaction().rollback();
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	}
    }
}
