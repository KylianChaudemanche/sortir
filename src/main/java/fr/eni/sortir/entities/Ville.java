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
@Table(name="VILLES")
public class Ville implements Serializable {
	@Id
	@GeneratedValue
	@Column(name="no_ville")
	private Integer noVille;
	@Column(name="nom_ville")
	private String nomVille;
	@Column(name="code_postal")
	private String codePostal;
	@OneToMany(mappedBy="ville")
	private Collection<Lieu> listLieu = new ArrayList<>();
	
	public Ville() {
		super();
	}

	public Ville(String nomVille, String codePostal, Collection<Lieu> listLieu) {
		super();
		this.nomVille = nomVille;
		this.codePostal = codePostal;
		this.listLieu = listLieu;
	}

	public Integer getNoVille() {
		return noVille;
	}

	public void setNoVille(Integer noVille) {
		this.noVille = noVille;
	}

	public String getNomVille() {
		return nomVille;
	}

	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public Collection<Lieu> getListLieu() {
		return listLieu;
	}

	public void setListLieu(Collection<Lieu> listLieu) {
		this.listLieu = listLieu;
	}
}
