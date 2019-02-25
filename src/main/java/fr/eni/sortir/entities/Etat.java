package fr.eni.sortir.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name = "ETATS")
public class Etat implements Serializable {
    private static final long serialVersionUID = 3247440578284786710L;
    @Id
    @GeneratedValue
    @Column(name = "no_etat")
    private Integer noEtat;
    private String libelle;
    @JsonManagedReference
    @OneToMany(mappedBy = "etat", fetch=FetchType.LAZY)
    private Collection<Sortie> listSortie = new ArrayList<>();

    public Etat() {
	super();
    }

    public Etat(Integer noEtat, String libelle, Collection<Sortie> listSortie) {
	super();
	this.noEtat = noEtat;
	this.libelle = libelle;
	this.listSortie = listSortie;
    }

    public Etat(String libelle, Collection<Sortie> listSortie) {
	super();
	this.libelle = libelle;
	this.listSortie = listSortie;
    }

    public Integer getNoEtat() {
	return noEtat;
    }

    public void setNoEtat(Integer noEtat) {
	this.noEtat = noEtat;
    }

    public String getLibelle() {
	return libelle;
    }

    public void setLibelle(String libelle) {
	this.libelle = libelle;
    }

    public Collection<Sortie> getListSortie() {
	return listSortie;
    }

    public void setListSortie(Collection<Sortie> listSortie) {
	this.listSortie = listSortie;
    }

    @Override
    public String toString() {
	return "Etat [noEtat=" + noEtat + ", libelle=" + libelle + ", listSortie=" + listSortie + "]";
    }
}