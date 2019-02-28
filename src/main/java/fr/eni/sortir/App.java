package fr.eni.sortir;

import fr.eni.sortir.dao.DaoFactory;

public class App {

    public static void main(String[] args) {
	DaoFactory.getParticipantDao().removeParticipant(13);

    }

}
