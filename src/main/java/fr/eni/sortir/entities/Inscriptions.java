package fr.eni.sortir.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="INSCRIPTIONS")
public class Inscriptions implements Serializable {
	Date date_inscription;
	
} 
