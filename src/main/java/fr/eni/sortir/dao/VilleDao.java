package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Ville;

public interface VilleDao {
    Ville addVille(Ville villes);

    Ville findVille(Integer noVille);

    Ville updateVille(Ville villes);

    Boolean removeVille(Integer noVille);

    Collection<Ville> getAllVille();
}
