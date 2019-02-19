package fr.eni.sortir;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.eni.sortir.entities.Lieu;
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
    	/*Ville redon = new Ville("REDON","35600",new ArrayList<>());
    	em.persist(redon);
    	em.flush();
    	Lieu lieu = new Lieu("chémoa","saint michel",(float)47.655,(float)-2.07217,redon,new ArrayList<>());
    	em.persist(lieu);
    	*/
    	Lieu chemoa = em.find(Lieu.class, 3);
    	System.out.println(chemoa.toString());
    	
    	em.getTransaction().commit();
    	em.close();

    }
}
