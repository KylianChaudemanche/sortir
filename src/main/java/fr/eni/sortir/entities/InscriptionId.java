package fr.eni.sortir.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class InscriptionId implements Serializable {
	private Participant participant;
	private Sortie sortie;
	
	@ManyToOne
	public Participant getParticipant() {
		return participant;
	}
	public void setParticipant(Participant participant) {
		this.participant = participant;
	}
	@ManyToOne
	public Sortie getSortie() {
		return sortie;
	}
	public void setSortie(Sortie sortie) {
		this.sortie = sortie;
	}
}
