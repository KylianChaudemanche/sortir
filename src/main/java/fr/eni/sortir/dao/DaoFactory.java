package fr.eni.sortir.dao;

import fr.eni.sortir.dao.jpa.JpaEtatDao;
import fr.eni.sortir.dao.jpa.JpaInscriptionDao;
import fr.eni.sortir.dao.jpa.JpaLieuDao;
import fr.eni.sortir.dao.jpa.JpaParticipantDao;
import fr.eni.sortir.dao.jpa.JpaSiteDao;
import fr.eni.sortir.dao.jpa.JpaSortieDao;
import fr.eni.sortir.dao.jpa.JpaVilleDao;
import fr.eni.sortir.utils.PersistenceManager;

public class DaoFactory {
    public static EtatDao getEtatDao() {
	return new JpaEtatDao(PersistenceManager.getEntityManagerFactory());
    }

    public static LieuDao getLieuDao() {
	return new JpaLieuDao(PersistenceManager.getEntityManagerFactory());
    }

    public static ParticipantDao getParticipantDao() {
	return new JpaParticipantDao(PersistenceManager.getEntityManagerFactory());
    }

    public static SiteDao getSiteDao() {
	return new JpaSiteDao(PersistenceManager.getEntityManagerFactory());
    }

    public static SortieDao getSortieDao() {
	return new JpaSortieDao(PersistenceManager.getEntityManagerFactory());
    }

    public static VilleDao getVilleDao() {
	return new JpaVilleDao(PersistenceManager.getEntityManagerFactory());
    }
    
    public static InscriptionDao getInscriptionDao() {
	return new JpaInscriptionDao(PersistenceManager.getEntityManagerFactory());
    }
}
