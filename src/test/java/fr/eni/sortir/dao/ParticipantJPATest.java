package fr.eni.sortir.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import fr.eni.sortir.PersistenceTest;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Site;

public class ParticipantJPATest extends PersistenceTest {
    private static final Logger LOGGER = Logger.getLogger(ParticipantJPATest.class);

    public void testPersistence() {
	try {
	    // Init values
	    Site site = new Site("Test", new ArrayList<>());
	    DaoFactory.getSiteDao().addSite(site);
	    Participant partcipant = new Participant("Test", "Test", "Test", "Test", "Test", "Test", true, true, new ArrayList<>(), site, new ArrayList<>());
	    DaoFactory.getParticipantDao().addParticipant(partcipant);

	    // Test insertion
	    DaoFactory.getParticipantDao().addParticipant(partcipant);
	    assertTrue(DaoFactory.getParticipantDao().findParticipant(partcipant.getNoParticipant()) != null);

	    // Test update
	    Participant participantUpdate = DaoFactory.getParticipantDao().findParticipant(partcipant.getNoParticipant());
	    participantUpdate.setPseudo("Test2");
	    DaoFactory.getParticipantDao().updateParticipant(participantUpdate);
	    assertTrue(!partcipant.equals(DaoFactory.getParticipantDao().findParticipant(partcipant.getNoParticipant())));

	    // Test get all entities
	    assertTrue(DaoFactory.getParticipantDao().getAllParticipant().size() == 1);
	    
	    // Test find entity by mail
	    assertTrue(DaoFactory.getParticipantDao().findParticipantByMail("Test") != null);
	
	    // Test deletion
	    DaoFactory.getParticipantDao().removeParticipant(partcipant.getNoParticipant());
	    assertTrue(DaoFactory.getParticipantDao().findParticipant(partcipant.getNoParticipant()) == null);
	} catch (Exception e) {
	    em.getTransaction().rollback();
	    LOGGER.error(e.getMessage(), e);
	    fail("EtatJPATest.testPersistence");
	}
    }
}
