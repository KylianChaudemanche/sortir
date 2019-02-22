package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Site;

public interface SiteDao {
    Site addSite(Site site);

    Site findSite(Integer noSite);

    Site updateSite(Site site);

    Boolean removeSite(Integer noSite);

    Collection<Site> getAllSite();
}
