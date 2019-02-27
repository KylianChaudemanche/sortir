package fr.eni.sortir.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "PARTICIPANTS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Participant implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1017103974819937156L;

    @Id
    @GeneratedValue
    @Column(name = "no_participant")
    private Integer noParticipant;
    @Column(unique = true)
    private String pseudo;
    private String nom;
    private String prenom;
    private String telephone;
    @Column(unique = true)
    private String mail;
    @Column(name = "mot_de_passe")
    private String motDePasse;
    private Boolean administrateur;
    private Boolean actif;
    @OneToMany(fetch=FetchType.EAGER,mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Inscription> inscriptions = new ArrayList<>();
    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "sites_no_site")
    private Site site;
    @JsonManagedReference
    @OneToMany(mappedBy = "organisateur", fetch=FetchType.LAZY)
    private Collection<Sortie> listSortie = new ArrayList<>();

    public Participant() {
	super();
    }

    public Participant(String pseudo, String nom, String prenom, String telephone, String mail, String motDePasse,
	    Boolean administrateur, Boolean actif, Collection<Inscription> inscriptions, Site site,
	    Collection<Sortie> listSortie) {
	super();
	this.pseudo = pseudo;
	this.nom = nom;
	this.prenom = prenom;
	this.telephone = telephone;
	this.mail = mail;
	this.motDePasse = motDePasse;
	this.administrateur = administrateur;
	this.actif = actif;
	this.inscriptions = inscriptions;
	this.site = site;
	this.listSortie = listSortie;
    }

    public Integer getNoParticipant() {
	return noParticipant;
    }

    public void setNoParticipant(Integer noParticipant) {
	this.noParticipant = noParticipant;
    }

    public String getPseudo() {
	return pseudo;
    }

    public void setPseudo(String pseudo) {
	this.pseudo = pseudo;
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public String getPrenom() {
	return prenom;
    }

    public void setPrenom(String prenom) {
	this.prenom = prenom;
    }

    public String getTelephone() {
	return telephone;
    }

    public void setTelephone(String telephone) {
	this.telephone = telephone;
    }

    public String getMail() {
	return mail;
    }

    public void setMail(String mail) {
	this.mail = mail;
    }

    public String getMotDePasse() {
	return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
	this.motDePasse = motDePasse;
    }
    //TODO modifier getAdministrateur en isAdministrateur
    public Boolean getAdministrateur() {
	return administrateur;
    }

    public void setAdministrateur(Boolean administrateur) {
	this.administrateur = administrateur;
    }
    //TODO modifier getActif en isActif
    public Boolean getActif() {
	return actif;
    }

    public void setActif(Boolean actif) {
	this.actif = actif;
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

    public Site getSite() {
	return site;
    }

    public void setSite(Site site) {
	this.site = site;
    }

    public Collection<Sortie> getListSortie() {
	return listSortie;
    }

    public void setListSortie(Collection<Sortie> listSortie) {
	this.listSortie = listSortie;
    }

    public void addSortie(Sortie sortie) {
	Inscription inscription = new Inscription(this, sortie);
	inscriptions.add(inscription);
	sortie.getInscriptions().add(inscription);
    }

    public void removeSortie(Sortie sortie) {
	for (Iterator<Inscription> iterator = inscriptions.iterator(); iterator.hasNext();) {
	    Inscription inscription = iterator.next();

	    if (inscription.getParticipant().equals(this) && inscription.getSortie().equals(sortie)) {
		iterator.remove();
		inscription.getParticipant().getInscriptions().remove(inscription);
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

	Participant that = (Participant) o;
	return Objects.equals(pseudo, that.pseudo) && Objects.equals(nom, that.nom)
		&& Objects.equals(prenom, that.prenom) && Objects.equals(telephone, that.telephone)
		&& Objects.equals(mail, that.mail) && Objects.equals(motDePasse, that.motDePasse)
		&& Objects.equals(administrateur, that.administrateur) && Objects.equals(actif, that.actif);
    }

    @Override
    public int hashCode() {
	return Objects.hash(pseudo, nom, prenom, telephone, mail, motDePasse, administrateur, actif);
    }

    @Override
    public String toString() {
	return "Participant [noParticipant=" + noParticipant + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
		+ prenom + ", telephone=" + telephone + ", mail=" + mail + ", motDePasse=" + motDePasse
		+ ", administrateur=" + administrateur + ", actif=" + actif + ", inscriptions=" + inscriptions
		+ ", site=" + site + "]";
    }
}
