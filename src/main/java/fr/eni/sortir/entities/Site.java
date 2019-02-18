package fr.eni.sortir.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Site implements Serializable {
	@Id
	@GeneratedValue
	@Column(name="no_site")
	private Integer noSite;
	@Column(name="nom_site")
	private String nomSite;
	
	public Site() {
		super();
	}

	public Site(String nomSite) {
		super();
		this.nomSite = nomSite;
	}

	public Integer getNoSite() {
		return noSite;
	}

	public void setNoSite(Integer noSite) {
		this.noSite = noSite;
	}

	public String getNomSite() {
		return nomSite;
	}

	public void setNomSite(String nomSite) {
		this.nomSite = nomSite;
	}
}
