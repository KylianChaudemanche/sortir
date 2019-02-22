package fr.eni.sortir.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "SORTIES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sortie implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "no_sortie")
	private Integer noSortie;
	private String nom;
	@Column(name = "datedebut")
	@Temporal(TemporalType.DATE)
	private Date dateDebut;
	private Integer duree;
	@Column(name = "datecloture")
	@Temporal(TemporalType.DATE)
	private Date dateCloture;
	@Column(name = "nbinscriptionsmax")
	private Integer nbInscriptionsMax;
	@Column(name = "descriptioninfos")
	private String descriptionInfos;
	@Column(name = "urlPhoto")
	private String urlPhoto;
	@ManyToOne
	@JoinColumn(name = "etats_no_etat")
	private Etat etat;
	@ManyToOne
	@JoinColumn(name = "lieux_no_lieu")
	private Lieu lieu;
	@OneToMany(fetch= FetchType.EAGER, mappedBy = "sortie", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Inscription> inscriptions = new ArrayList<>();
	@ManyToOne
	@JoinColumn(name = "organisateur")
	private Participant organisateur;

	public Sortie() {
		super();
	}

	public Sortie(String nom, Date dateDebut, Integer duree, Date dateCloture, Integer nbInscriptionsMax,
			String descriptionInfos, String urlPhoto, Etat etat, Lieu lieu, Collection<Inscription> inscriptions,
			Participant organisateur) {
		super();
		this.nom = nom;
		this.dateDebut = dateDebut;
		this.duree = duree;
		this.dateCloture = dateCloture;
		this.nbInscriptionsMax = nbInscriptionsMax;
		this.descriptionInfos = descriptionInfos;
		this.urlPhoto = urlPhoto;
		this.etat = etat;
		this.lieu = lieu;
		this.inscriptions = inscriptions;
		this.organisateur = organisateur;
	}

	public Integer getNoSortie() {
		return noSortie;
	}

	public void setNoSortie(Integer noSortie) {
		this.noSortie = noSortie;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Integer getDuree() {
		return duree;
	}

	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	public Date getDateCloture() {
		return dateCloture;
	}

	public void setDateCloture(Date dateCloture) {
		this.dateCloture = dateCloture;
	}

	public Integer getNbInscriptionsMax() {
		return nbInscriptionsMax;
	}

	public void setNbInscriptionsMax(Integer nbInscriptionsMax) {
		this.nbInscriptionsMax = nbInscriptionsMax;
	}

	public String getDescriptionInfos() {
		return descriptionInfos;
	}

	public void setDescriptionInfos(String descriptionInfos) {
		this.descriptionInfos = descriptionInfos;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public Lieu getLieu() {
		return lieu;
	}

	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}
	
	public Participant getOrganisateur() {
		return organisateur;
	}

	public void setOrganisateur(Participant organisateur) {
		this.organisateur = organisateur;
	}

	public Collection<Inscription> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(Collection<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}

	public void addInscription(Inscription inscription) {
		this.inscriptions.add(inscription);
	}

	public void removeInscription(Inscription inscription) {
		this.inscriptions.remove(inscription);
	}
	
	public void addPartcipant(Participant participant) {
		Inscription inscription = new Inscription(participant, this);
		inscriptions.add(inscription);
		participant.getInscriptions().add(inscription);
	}
	
	public void removeParticipant(Participant participant) {
		for (Iterator<Inscription> iterator = inscriptions.iterator(); iterator.hasNext();) {
			Inscription inscription = iterator.next();

			if (inscription.getParticipant().equals(participant) && inscription.getSortie().equals(this)) {
				iterator.remove();
				inscription.getSortie().getInscriptions().remove(inscription);
				inscription.setParticipant(null);
				inscription.setSortie(null);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Sortie that = (Sortie) o;
		return Objects.equals(nom, that.nom) && Objects.equals(dateDebut, that.dateDebut)
				&& Objects.equals(duree, that.duree) && Objects.equals(dateCloture, that.dateCloture)
				&& Objects.equals(nbInscriptionsMax, that.nbInscriptionsMax)
				&& Objects.equals(descriptionInfos, that.descriptionInfos)
				&& Objects.equals(urlPhoto, that.urlPhoto);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nom, dateDebut, duree, dateCloture, nbInscriptionsMax, descriptionInfos, urlPhoto);
	}
	
	@Override
	public String toString() {
		return "Sortie [noSortie=" + noSortie + ", nom=" + nom + ", dateDebut=" + dateDebut + ", duree=" + duree
				+ ", dateCloture=" + dateCloture + ", nbInscriptionsMax=" + nbInscriptionsMax + ", descriptionInfos="
				+ descriptionInfos + ", urlPhoto=" + urlPhoto + ", etat=" + etat + ", lieu=" + lieu + ", inscriptions="
				+ inscriptions + ", organisateur=" + organisateur + "]";
	}
}
