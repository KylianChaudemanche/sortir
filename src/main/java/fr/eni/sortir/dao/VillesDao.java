package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Villes;

public interface VillesDao {
	Villes addVilles(Villes villes);

	Villes findVilles(Integer noVilles);

	Villes updateVilles(Villes villes);

	Boolean removeVilles(Integer noVilles);

	Collection<Villes> getAllVilles();
}
