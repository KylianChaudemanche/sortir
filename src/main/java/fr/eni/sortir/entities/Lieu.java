package fr.eni.sortir.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="LIEUX")
public class Lieu implements Serializable {
	@Id
	@GeneratedValue
	@Column(name="no_lieu")
	private Integer noLieu;
	@Column(name="nom_lieu")
	private String nomLieu;
	private String rue;
	private Float latitude;
	private Float longitude;
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="villes_no_ville")
	private Ville ville;
	@OneToMany(mappedBy="lieu")
	private Collection<Sortie> listSortie = new ArrayList<>();
	
	public Lieu() {
		super();
	}

	public Lieu(String nomLieu, String rue, Float latitude, Float longitude, Ville ville,
			Collection<Sortie> listSortie) {
		super();
		this.nomLieu = nomLieu;
		this.rue = rue;
		this.latitude = latitude;
		this.longitude = longitude;
		this.ville = ville;
		this.listSortie = listSortie;
	}

	public Integer getNoLieu() {
		return noLieu;
	}

	public void setNoLieu(Integer noLieu) {
		this.noLieu = noLieu;
	}

	public String getNomLieu() {
		return nomLieu;
	}

	public void setNomLieu(String nomLieu) {
		this.nomLieu = nomLieu;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Ville getVille() {
		return ville;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

	public Collection<Sortie> getListSortie() {
		return listSortie;
	}

	public void setListSortie(Collection<Sortie> listSortie) {
		this.listSortie = listSortie;
	}

	@Override
	public String toString() {
		return "Lieu [noLieu=" + noLieu + ", nomLieu=" + nomLieu + ", rue=" + rue + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", ville=" + ville + "]";
	}
	
}
