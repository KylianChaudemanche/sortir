package fr.eni.sortir.job;

import java.util.Calendar;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.eni.sortir.dao.DaoFactory;

public class SupprimerTokenExpireJob implements Runnable {

	private final ScheduledExecutorService service;

	public SupprimerTokenExpireJob(ScheduledExecutorService service) {
		this.service = service;
	}

	public void run() {
		try {
			DaoFactory.getTokenDao().supprimerTokenExpire();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		} finally {
			Calendar now = Calendar.getInstance();
			Calendar nextExec = Calendar.getInstance();
			nextExec.set(Calendar.HOUR_OF_DAY, 23);
			nextExec.set(Calendar.MINUTE, 59);
			nextExec.set(Calendar.SECOND, 59);
			long untilNextInvocation = nextExec.getTimeInMillis() - now.getTimeInMillis();
			service.schedule(new CloseInscriptionSortieJob(service), untilNextInvocation, TimeUnit.MILLISECONDS);
		}
	}

}
