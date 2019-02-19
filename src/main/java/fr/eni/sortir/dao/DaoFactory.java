package fr.eni.sortir.dao;

import fr.eni.sortir.dao.jpa.JpaEtatDao;
import fr.eni.sortir.utils.PersistenceManager;

public class DaoFactory {
	public static EtatDao getEtatDao() {
		return new JpaEtatDao(PersistenceManager.getEntityManagerFactory());
	}
}
