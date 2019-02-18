package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Participants;

public interface ParticipantsDao {
	Participants addParticipants(Participants inscriptions);

	Participants findParticipants(Integer noParticipants);

	Participants updateParticipants(Participants participants);

	Boolean removeParticipants(Integer noParticipants);

	Collection<Participants> getAllParticipants(); 
}
