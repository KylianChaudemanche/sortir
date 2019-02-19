package fr.eni.sortir.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class InscriptionId implements Serializable {
	private Integer noParticipant;
	private Integer noSortie;
	
	public InscriptionId() {
		super();
	}
	
	public InscriptionId(Integer noParticipant, Integer noSortie) {
		super();
		this.noParticipant = noParticipant;
		this.noSortie = noSortie;
	}

	public Integer getNoParticipant() {
		return noParticipant;
	}

	public Integer getNoSortie() {
		return noSortie;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        InscriptionId that = (InscriptionId) o;
        return Objects.equals(noParticipant, that.noParticipant) &&
               Objects.equals(noSortie, that.noSortie);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(noParticipant, noSortie);
    }

	@Override
	public String toString() {
		return "InscriptionId [participant=" + noParticipant + ", sortie=" + noSortie + "]";
	}
	
}
