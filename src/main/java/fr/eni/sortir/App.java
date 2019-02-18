package fr.eni.sortir;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Site;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    	EntityManager em = emf.createEntityManager();
    	em.getTransaction().begin();
    	
    	Site site = new Site("test");
    	em.persist(site);
    	em.flush();
    	
    	Participant participant = new Participant("test", "Henkes", "Kevin", "0299095421", "kevin@henkes.com", "test", true, true, new ArrayList<>());
    	em.persist(participant);

    	em.getTransaction().commit();
    	em.close();

    }
}
