package fr.eni.sortir;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Inscription;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Sortie;

public class App {

    public static void main(String[] args) {
	Sortie sortie = DaoFactory.getSortieDao().findSortie(2);
	Participant participant = DaoFactory.getParticipantDao().findParticipant(9);
	Inscription i = DaoFactory.getInscriptionDao().findInscription(participant, sortie);
	DaoFactory.getInscriptionDao().removeInscription(i);
    }

}