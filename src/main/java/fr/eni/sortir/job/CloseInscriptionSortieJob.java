package fr.eni.sortir.job;

import java.util.Calendar;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.eni.sortir.dao.DaoFactory;

public class CloseInscriptionSortieJob implements Runnable {

    private final ScheduledExecutorService service;

    public CloseInscriptionSortieJob(ScheduledExecutorService service) {
	this.service = service;
    }

    public void run() {
	try {
	    DaoFactory.getSortieDao().closeInscription();
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	} finally {
	    Calendar now = Calendar.getInstance();
	    Calendar nextExec = Calendar.getInstance();
	    nextExec.set(Calendar.HOUR_OF_DAY, 23);
	    nextExec.set(Calendar.MINUTE, 59);
	    nextExec.set(Calendar.SECOND, 59);
	    long untilNextInvocation = nextExec.getTimeInMillis() - now.getTimeInMillis();
	    service.schedule(new CloseInscriptionSortieJob(service), untilNextInvocation, TimeUnit.MILLISECONDS);
	    System.out.println("Next exec in " + untilNextInvocation + " ms");
	}
    }

}
