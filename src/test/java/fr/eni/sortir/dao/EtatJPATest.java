package fr.eni.sortir.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import fr.eni.sortir.PersistenceTest;
import fr.eni.sortir.entities.Etat;

public class EtatJPATest extends PersistenceTest {
    private static final Logger LOGGER = Logger.getLogger(EtatJPATest.class);
    
    public void testPersistence() {
        try {
            //Init values
            Etat etat = new Etat("Test", new ArrayList<>());
            
            //Test insertion
            DaoFactory.getEtatDao().addEtat(etat);
            assertTrue(DaoFactory.getEtatDao().findEtat(etat.getNoEtat()) != null);
            
            //Test update
            Etat etatUpdate = DaoFactory.getEtatDao().findEtat(etat.getNoEtat());
            etatUpdate.setLibelle("Test2");
            DaoFactory.getEtatDao().updateEtat(etatUpdate);
            assertTrue(!etat.equals(DaoFactory.getEtatDao().findEtat(etat.getNoEtat())));

            //Test deletion
            DaoFactory.getEtatDao().removeEtat(etat.getNoEtat());
            assertTrue(DaoFactory.getEtatDao().findEtat(etat.getNoEtat()) == null);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e.getMessage(), e);
            fail("EtatJPATest.testPersistence");
        }
    }
}
