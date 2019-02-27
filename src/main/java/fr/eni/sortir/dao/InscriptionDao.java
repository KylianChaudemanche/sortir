package fr.eni.sortir.dao;

import fr.eni.sortir.entities.Inscription;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Sortie;

public interface InscriptionDao {
    Boolean removeInscription(Inscription inscription);
    Inscription findInscription(Participant participant, Sortie sortie);
}
