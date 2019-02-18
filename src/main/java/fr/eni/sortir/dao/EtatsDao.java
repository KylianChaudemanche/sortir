package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Etats;

public interface EtatsDao {
	Etats addEtats(Etats etats);

	Etats findEtats(Integer noEtats);

	Etats updateEtats(Etats etats);

	Boolean removeEtats(Integer noEtats);

	Collection<Etats> getAllEtats();
}
