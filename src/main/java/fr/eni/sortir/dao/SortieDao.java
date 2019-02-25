package fr.eni.sortir.dao;

import java.util.Collection;
import java.util.Date;

import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Site;
import fr.eni.sortir.entities.Sortie;

public interface SortieDao {
    Sortie addSortie(Sortie sortie);

    Sortie findSortie(Integer noSortie);

    Sortie updateSortie(Sortie sortie);

    Boolean removeSortie(Integer noSortie);

	Collection<Sortie> getAllSortie();

	Collection<Sortie> getAllSortieFiltre(Site site, Boolean organisateur, Boolean inscrit, Boolean pasInscrit,
			Boolean passee, Date dateDebut, Date dateFin, Participant participant);
}
