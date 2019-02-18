package fr.eni.sortir.entities;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "INSCRIPTIONS")
@AssociationOverrides({
	@AssociationOverride(name = "primaryKey.participant", 
		joinColumns = @JoinColumn(name = "no_participant")),
	@AssociationOverride(name = "primaryKey.sortie", 
		joinColumns = @JoinColumn(name = "no_sortie")) })
public class Inscription {
    // composite-id key
    private InscriptionId primaryKey = new InscriptionId();
     
    // additional fields
    @Column(name = "date_inscription")
    @Temporal(TemporalType.DATE)
    private Date dateInscription;
 
    @EmbeddedId
    public InscriptionId getPrimaryKey() {
        return primaryKey;
    }
 
    public void setPrimaryKey(InscriptionId primaryKey) {
        this.primaryKey = primaryKey;
    }
 
    @Transient
    public Participant getParticipant() {
        return getPrimaryKey().getParticipant();
    }
 
    public void setParticipant(Participant participant) {
        getPrimaryKey().setParticipant(participant);
    }
 
    @Transient
    public Sortie getGroup() {
        return getPrimaryKey().getSortie();
    }
 
    public void setGroup(Sortie sortie) {
        getPrimaryKey().setSortie(sortie);
    }

	public Date getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
	}
}
