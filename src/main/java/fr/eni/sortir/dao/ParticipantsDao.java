package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Participant;

public interface ParticipantsDao {
	Participant addParticipant(Participant inscriptions);

	Participant findParticipant(Integer noParticipant);

	Participant updateParticipant(Participant participants);

	Boolean removeParticipant(Integer noParticipant);

	Collection<Participant> getAllParticipant(); 
}
