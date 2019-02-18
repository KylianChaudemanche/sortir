package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Inscriptions;

public interface InscriptionsDao {
	Inscriptions addInscriptions(Inscriptions inscriptions);

	Inscriptions findInscriptions(Integer noInscriptions);

	Inscriptions updateInscriptions(Inscriptions inscriptions);

	Boolean removeInscriptions(Integer noInscriptions);

	Collection<Inscriptions> getAllInscriptions(); 
}
