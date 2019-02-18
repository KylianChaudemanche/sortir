package fr.eni.sortir.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ETATS")
public class Etats implements Serializable {
	@Id
	@Column(name="no_etat")
	private Integer noEtat;

	private String libelle;
	
	public Etats() {
		super();
	}

	public Etats(Integer noEtat, String libelle) {
		super();
		this.noEtat = noEtat;
		this.libelle = libelle;
	}

	public Integer getNoEtat() {
		return noEtat;
	}

	public void setNoEtat(Integer noEtat) {
		this.noEtat = noEtat;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
