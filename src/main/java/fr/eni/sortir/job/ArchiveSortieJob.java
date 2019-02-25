package fr.eni.sortir.job;

import fr.eni.sortir.dao.DaoFactory;

public class ArchiveSortieJob implements Runnable {
    @Override
    public void run() {
	DaoFactory.getSortieDao().archiveAllSortie();
    }

}
