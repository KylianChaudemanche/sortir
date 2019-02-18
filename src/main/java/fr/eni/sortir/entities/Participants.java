package fr.eni.sortir.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PARTICIPANTS")
public class Participants implements Serializable {
	@Id
	@Column(name="no_participant")
	private Integer noParticipant;
	private String pseudo;
	private String nom;
	private String prenom;
	private String telephone;
	private String mail;
	@Column(name="mot_de_passe")
	private String motDePasse;
	private Boolean administrateur;
	private Boolean actif;
}
