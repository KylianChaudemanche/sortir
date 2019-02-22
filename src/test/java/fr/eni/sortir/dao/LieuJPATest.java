package fr.eni.sortir.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import fr.eni.sortir.PersistenceTest;
import fr.eni.sortir.entities.Lieu;
import fr.eni.sortir.entities.Ville;

public class LieuJPATest extends PersistenceTest {
    private static final Logger LOGGER = Logger.getLogger(LieuJPATest.class);

    public void testPersistence() {
	try {
	    // Init values
	    Ville ville = new Ville("Test", "35698", new ArrayList<>());
	    DaoFactory.getVilleDao().addVille(ville);
	    Lieu lieu = new Lieu("Test", "Rue de la gare", 5.5653f, 7.5236f, ville, new ArrayList<>());

	    // Test insertion
	    DaoFactory.getLieuDao().addLieu(lieu);
	    assertTrue(DaoFactory.getLieuDao().findLieu(lieu.getNoLieu()) != null);

	    // Test update
	    Lieu lieuUpdate = DaoFactory.getLieuDao().findLieu(lieu.getNoLieu());
	    lieuUpdate.setNomLieu("Test2");
	    DaoFactory.getLieuDao().updateLieu(lieuUpdate);
	    assertTrue(!lieu.equals(DaoFactory.getLieuDao().findLieu(lieu.getNoLieu())));

	    // Test get all entities
	    assertTrue(DaoFactory.getLieuDao().getAllLieu().size() == 1);

	    // Test deletion
	    DaoFactory.getLieuDao().removeLieu(lieu.getNoLieu());
	    assertTrue(DaoFactory.getLieuDao().findLieu(lieu.getNoLieu()) == null);
	} catch (Exception e) {
	    em.getTransaction().rollback();
	    LOGGER.error(e.getMessage(), e);
	    fail("EtatJPATest.testPersistence");
	}
    }
}
