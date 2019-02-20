package fr.eni.sortir.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SITES")
public class Site implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8468692258408794359L;
	@Id
	@GeneratedValue
	@Column(name="no_site")
	private Integer noSite;
	@Column(name="nom_site")
	private String nomSite;
	@OneToMany(mappedBy = "site")
	private Collection<Participant> listInscrit = new ArrayList<>();
	
	public Site() {
		super();
	}

	public Site(String nomSite, Collection<Participant> listInscrit) {
		super();
		this.nomSite = nomSite;
		this.listInscrit = listInscrit;
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

	public Collection<Participant> getListInscrit() {
		return listInscrit;
	}

	public void setListInscrit(Collection<Participant> listInscrit) {
		this.listInscrit = listInscrit;
	}

	@Override
	public String toString() {
		return "Site [noSite=" + noSite + ", nomSite=" + nomSite + "]";
	}
	
}
