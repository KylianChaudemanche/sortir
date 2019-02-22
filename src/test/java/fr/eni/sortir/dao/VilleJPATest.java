package fr.eni.sortir.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import fr.eni.sortir.PersistenceTest;
import fr.eni.sortir.entities.Ville;

public class VilleJPATest extends PersistenceTest {
    private static final Logger LOGGER = Logger.getLogger(VilleJPATest.class);

    public void testPersistence() {
	try {
	    // Init values
	    Ville ville = new Ville("Test", "35623", new ArrayList<>());

	    // Test insertion
	    DaoFactory.getVilleDao().addVille(ville);
	    assertTrue(DaoFactory.getVilleDao().findVille(ville.getNoVille()) != null);

	    // Test update
	    Ville villeUpdate = DaoFactory.getVilleDao().findVille(ville.getNoVille());
	    villeUpdate.setNomVille("Test2");
	    DaoFactory.getVilleDao().updateVille(villeUpdate);
	    assertTrue(!ville.equals(DaoFactory.getVilleDao().findVille(ville.getNoVille())));

	    // Test get all entities
	    assertTrue(DaoFactory.getVilleDao().getAllVille().size() == 1);

	    // Test deletion
	    DaoFactory.getVilleDao().removeVille(ville.getNoVille());
	    assertTrue(DaoFactory.getVilleDao().findVille(ville.getNoVille()) == null);
	} catch (Exception e) {
	    em.getTransaction().rollback();
	    LOGGER.error(e.getMessage(), e);
	    fail("EtatJPATest.testPersistence");
	}
    }
}
