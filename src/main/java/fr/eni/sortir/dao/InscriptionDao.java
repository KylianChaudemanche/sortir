package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Inscription;

public interface InscriptionDao {
	Inscription addInscription(Inscription inscriptions);

	Inscription findInscription(Integer noInscription);

	Inscription updateInscription(Inscription inscriptions);

	Boolean removeInscription(Integer noInscription);

	Collection<Inscription> getAllInscription(); 
}
