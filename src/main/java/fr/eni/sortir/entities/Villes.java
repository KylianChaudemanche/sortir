package fr.eni.sortir.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VILLES")
public class Villes implements Serializable {
	@Id
	@Column(name="no_ville")
	private Integer noVille;
	@Column(name="nom_ville")
	private String nomVille;
}
