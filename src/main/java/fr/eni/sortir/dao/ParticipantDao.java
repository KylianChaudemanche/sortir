package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Participant;

public interface ParticipantDao {
    Participant addParticipant(Participant participant);

    Participant findParticipant(Integer noParticipant);

    Participant updateParticipant(Participant participant);

    Boolean removeParticipant(Integer noParticipant);

    Collection<Participant> getAllParticipant();

    Participant findParticipantByMail(String mail);
}
