package fr.eni.sortir.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LIEUX")
public class Lieux implements Serializable {
	@Id
	@Column(name="no_lieu")
	private Integer noLieu;
	@Column(name="nom_lieu")
	private String nomLieu;
	private String rue;
	private Float lagitude;
	private Float longitude;
}
