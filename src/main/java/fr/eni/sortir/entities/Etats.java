package fr.eni.sortir.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ETATS")
public class Etats {
	@Id
	Integer no_etat;
	String libelle;
	
	public Etats() {
		super();
	}
	
	public Etats(Integer no_etat, String libelle) {
		super();
		this.no_etat = no_etat;
		this.libelle = libelle;
	}
	
	public Etats(String libelle) {
		super();
		this.libelle = libelle;
	}

	public Integer getNo_etat() {
		return no_etat;
	}

	public void setNo_etat(Integer no_etat) {
		this.no_etat = no_etat;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
}
