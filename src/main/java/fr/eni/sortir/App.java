package fr.eni.sortir;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.eni.sortir.entities.Lieu;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Sortie;

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
//    	em.persist(participant);
    	/*Ville redon = new Ville("REDON","35600",new ArrayList<>());
    	em.persist(redon);
    	em.flush();
    	Lieu lieu = new Lieu("chémoa","saint michel",(float)47.655,(float)-2.07217,redon,new ArrayList<>());
    	em.persist(lieu);
    	*/
    	Lieu chemoa = em.find(Lieu.class, 3);
    	System.out.println(chemoa.toString());
    	
    	//Etat etat = new Etat(State.ACTIVITY_IN_PROGRESS.toString(), new ArrayList<>());
    	
    	//Etat etat = em.find(Etat.class, 1);
    	//Lieu lieu = em.find(Lieu.class, 1);
    	//Participant organisateur = em.find(Participant.class, 2);

    	//Sortie sortie = new Sortie("test", new Date(), 60, new Date(), 60, "test", "test", etat, lieu, new ArrayList<>(), organisateur);
    	//Sortie sortie = em.find(Sortie.class, 2);
    	//Inscription inscription = new Inscription();
    	
    	Participant participant = em.find(Participant.class, 2);
    	Sortie sortie = em.find(Sortie.class, 2);
    	sortie.removeParticipant(participant);
    	em.persist(participant);

    	
    	em.getTransaction().commit();
    	em.close();

    }
}
