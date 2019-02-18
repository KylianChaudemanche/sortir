package fr.eni.sortir.dao;

import fr.eni.sortir.dao.jpa.JpaEtatsDao;
import fr.eni.sortir.utils.PersistenceManager;

public class DaoFactory {
	public static EtatsDao getEtatsDao() {
		return new JpaEtatsDao(PersistenceManager.getEntityManagerFactory());
	}
}
