package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Sortie;

public interface SortiesDao {
	Sortie addSortie(Sortie sorties);

	Sortie findSortie(Integer noSortie);

	Sortie updateSortie(Sortie sorties);

	Boolean removeSortie(Integer noSortie);

	Collection<Sortie> getAllSortie();
}
