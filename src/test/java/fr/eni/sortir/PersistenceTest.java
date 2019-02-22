package fr.eni.sortir;

import java.sql.DriverManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public abstract class PersistenceTest extends TestCase {
    private static final Logger LOGGER = Logger.getLogger(PersistenceTest.class);
    private EntityManagerFactory emFactory;
    protected EntityManager em;
    
    public PersistenceTest() {
	
    }

    public PersistenceTest(String name) {
	super(name);
    }

    protected void setUp() throws Exception {
	super.setUp();
        try {
            LOGGER.info("Starting in-memory database for unit tests");
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            DriverManager.getConnection("jdbc:derby:memory:unit-testing-jpa;create=true").close();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception during database startup.");
        }
        try {
            LOGGER.info("Building JPA EntityManager for unit tests");
            emFactory = Persistence.createEntityManagerFactory("pu");
            em = emFactory.createEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception during JPA EntityManager instanciation.");
        }    }

    protected void tearDown() throws Exception {
	super.tearDown();
    }

}
