package fr.eni.sortir.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import fr.eni.sortir.job.CloseInscriptionSortieJob;

/**
 * Application Lifecycle Listener implementation class archiveSortieListener
 *
 */
@WebListener
public class TaskSchedulerListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    public TaskSchedulerListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
	scheduler = Executors.newSingleThreadScheduledExecutor();
	new CloseInscriptionSortieJob(scheduler).run();
    }

    public void contextDestroyed(ServletContextEvent sce) {
	scheduler.shutdownNow();
    }

}
