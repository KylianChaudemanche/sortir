package fr.eni.sortir;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Site;
import fr.eni.sortir.entities.Ville;

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
    	
//    	Site site = new Site("test");
//    	em.persist(site);
//    	em.flush();
    	
//    	Site site = em.find(Site.class, 1);
//    	
//    	Participant participant = new Participant("test", "Henkes", "Kevin", "0299095421", "kevin@henkes.com", "test", true, true, new ArrayList<>(), site);
//    	em.persist(participant);
    	
    	Ville ville = new Ville("Rennes", "35000", new ArrayList<>());
    	em.persist(ville);
    	
    	em.getTransaction().commit();
    	em.close();

    }
}
