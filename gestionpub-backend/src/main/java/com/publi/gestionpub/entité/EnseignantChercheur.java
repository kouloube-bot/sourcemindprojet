package com.publi.gestionpub.entité;

import javax.persistence.*;

@Entity 
public class EnseignantChercheur {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(unique=true,nullable=false)
	private String email;
	private String password;
	private String nom;
	private String prenom;
	@ManyToOne
	private Departement departement;
	
	
	
	public EnseignantChercheur(Long id, String email, String password,String nom,String prenom,Departement departement) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.nom=nom;
		this.prenom=prenom;
		this.departement=departement;
		
	}
	

	public EnseignantChercheur() {
		super();
	}


	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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


	public Departement getDepartement() {
		return departement;
	}


	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	
	

}
