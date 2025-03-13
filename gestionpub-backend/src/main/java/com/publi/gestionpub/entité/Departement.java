package com.publi.gestionpub.entité;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Departement {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String libelle;
	private String description;
	
	
	public Departement() {
		super();
	}
	public Departement(Long id, String libelle, String description) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
