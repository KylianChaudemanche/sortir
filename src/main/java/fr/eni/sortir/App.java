package fr.eni.sortir;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.eni.sortir.entities.Etat;

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
//    	Site site = em.find(Site.class, 1);

//    	Participant participant = new Participant("test", "Henkes", "Kevin", "0299095421", "kevin@henkes.com", "test", true, true, new ArrayList<>(), site);
    	
//    	Ville ville = em.find(Ville.class, 1);
    	
//    	Lieu lieu = new Lieu("test","test",15.0f,12.3f,ville,new ArrayList<>());
    	
    	
    	
    	em.getTransaction().commit();
    	em.close();

    }
}
