package fr.eni.sortir.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import fr.eni.sortir.job.ArchiveSortieJob;

/**
 * Application Lifecycle Listener implementation class archiveSortieListener
 *
 */
@WebListener
public class ArchiveSortieListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    public ArchiveSortieListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
	scheduler = Executors.newSingleThreadScheduledExecutor();
	scheduler.scheduleAtFixedRate(new ArchiveSortieJob(), 0, 1, TimeUnit.DAYS);
    }

    public void contextDestroyed(ServletContextEvent sce) {
	scheduler.shutdownNow();
    }

}
