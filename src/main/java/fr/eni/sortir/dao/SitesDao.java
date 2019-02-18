package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Sites;

public interface SitesDao {
	Sites addSites(Sites sites);

	Sites findSites(Integer noSites);

	Sites updateSites(Sites sites);

	Boolean removeSites(Integer noSites);

	Collection<Sites> getAllSites();
}
