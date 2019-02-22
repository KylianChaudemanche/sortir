package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Ville;

public interface VilleDao {
    Ville addVille(Ville ville);

    Ville findVille(Integer noVille);

    Ville updateVille(Ville ville);

    Boolean removeVille(Integer noVille);

    Collection<Ville> getAllVille();
}
