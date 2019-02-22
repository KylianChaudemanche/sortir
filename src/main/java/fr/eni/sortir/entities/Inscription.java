package fr.eni.sortir.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

@Entity
@Table(name = "INSCRIPTIONS")
public class Inscription implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1648545103735279144L;

    @EmbeddedId
    private InscriptionId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("noParticipant")
    @JoinColumn(name = "participants_no_participant")
    private Participant participant;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("noSortie")
    @JoinColumn(name = "sorties_no_sortie")
    private Sortie sortie;
    @Column(name = "date_inscription")
    private Date dateInscription = new Date();

    public Inscription() {
    }

    public Inscription(Participant participant, Sortie sortie) {
	this.participant = participant;
	this.sortie = sortie;
	this.id = new InscriptionId(participant.getNoParticipant(), sortie.getNoSortie());
    }

    public InscriptionId getId() {
	return id;
    }

    public void setId(InscriptionId id) {
	this.id = id;
    }

    public Participant getParticipant() {
	return participant;
    }

    public void setParticipant(Participant participant) {
	this.participant = participant;
    }

    public Sortie getSortie() {
	return sortie;
    }

    public void setSortie(Sortie sortie) {
	this.sortie = sortie;
    }

    public Date getDateInscription() {
	return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
	this.dateInscription = dateInscription;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;

	if (o == null || getClass() != o.getClass())
	    return false;

	Inscription that = (Inscription) o;
	return Objects.equals(participant, that.participant) && Objects.equals(sortie, that.sortie);
    }

    @Override
    public int hashCode() {
	return Objects.hash(participant, sortie);
    }

    @Override
    public String toString() {
	return "Inscription [primaryKey=" + id + ", dateInscription=" + dateInscription + "]";
    }
}
