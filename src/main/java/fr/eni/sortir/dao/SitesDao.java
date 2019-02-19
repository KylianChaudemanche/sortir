package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Site;

public interface SitesDao {
	Site addSite(Site sites);

	Site findSite(Integer noSite);

	Site updateSite(Site sites);

	Boolean removeSite(Integer noSite);

	Collection<Site> getAllSite();
}
