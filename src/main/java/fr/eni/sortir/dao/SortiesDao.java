package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Sorties;

public interface SortiesDao {
	Sorties addSorties(Sorties sorties);

	Sorties findSorties(Integer noSorties);

	Sorties updateSorties(Sorties sorties);

	Boolean removeSorties(Integer noSorties);

	Collection<Sorties> getAllSorties();
}
