package fr.eni.sortir.entities;

import java.io.Serializable;

import javax.persistence.Column;

public class Sites implements Serializable {
	private Integer no_site;
	@Column(name="nom_site")
	private String nomSite;
}
