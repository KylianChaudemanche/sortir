package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Lieu;

public interface LieuDao {
    Lieu addLieu(Lieu lieu);

    Lieu findLieu(Integer noLieu);

    Lieu updateLieu(Lieu lieu);

    Boolean removeLieu(Integer noLieu);

    Collection<Lieu> getAllLieu();
}
