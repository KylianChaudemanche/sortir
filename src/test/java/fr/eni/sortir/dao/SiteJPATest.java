package fr.eni.sortir.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import fr.eni.sortir.PersistenceTest;
import fr.eni.sortir.entities.Site;

public class SiteJPATest extends PersistenceTest {
    private static final Logger LOGGER = Logger.getLogger(SiteJPATest.class);

    public void testPersistence() {
	try {
	    // Init values
	    Site site = new Site("Test", new ArrayList<>());

	    // Test insertion
	    DaoFactory.getSiteDao().addSite(site);
	    assertTrue(DaoFactory.getSiteDao().findSite(site.getNoSite()) != null);

	    // Test update
	    Site siteUpdate = DaoFactory.getSiteDao().findSite(site.getNoSite());
	    siteUpdate.setNomSite("Test2");
	    DaoFactory.getSiteDao().updateSite(siteUpdate);
	    assertTrue(!site.equals(DaoFactory.getSiteDao().findSite(site.getNoSite())));

	    // Test get all entities
	    assertTrue(DaoFactory.getSiteDao().getAllSite().size() == 1);

	    // Test deletion
	    DaoFactory.getSiteDao().removeSite(site.getNoSite());
	    assertTrue(DaoFactory.getSiteDao().findSite(site.getNoSite()) == null);
	} catch (Exception e) {
	    em.getTransaction().rollback();
	    LOGGER.error(e.getMessage(), e);
	    fail("EtatJPATest.testPersistence");
	}
    }
}
