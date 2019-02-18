package fr.eni.sortir.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SORTIES")
public class Sorties implements Serializable {
	@Id
	@Column(name="no_sortie")
	private Integer noSortie;
	private String nom;
	@Column(name="datedebut")
	private Date dateDebut;
	private Integer duree;
	@Column(name="datecloture")
	private Date dateCloture;
	@Column(name="nbinscriptionsmax")
	private Integer nbInscriptionsMax;
	@Column(name="descriptioninfos")
	private String descriptionInfos;
	@Column(name="etatsortie")
	private Integer etatSortie;
	@Column(name="datecloture")
	private String urlPhoto;
}
