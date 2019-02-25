package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Sortie;

public interface SortieDao {
    Sortie addSortie(Sortie sortie);

    Sortie findSortie(Integer noSortie);

    Sortie updateSortie(Sortie sortie);

    Boolean removeSortie(Integer noSortie);

    Collection<Sortie> getAllSortie();
}
