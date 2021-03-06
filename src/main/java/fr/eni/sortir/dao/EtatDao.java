package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Etat;

public interface EtatDao {
    Etat addEtat(Etat etat);

    Etat findEtat(Integer noEtat);

    Etat updateEtat(Etat etat);

    Boolean removeEtat(Integer noEtat);

    Collection<Etat> getAllEtat();
    
    Etat findEtatByName(String name);
}
