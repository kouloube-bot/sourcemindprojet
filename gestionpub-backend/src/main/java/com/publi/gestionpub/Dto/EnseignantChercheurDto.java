package com.publi.gestionpub.Dto;




import java.util.ArrayList;
import java.util.List;

import com.publi.gestionpub.entité.Departement;

public class EnseignantChercheurDto {
	private Long id;
	private String email;
	private String password;
	private String nom;
	private String prenom;
	private String departement;
	private Long idDepartement;
	private String username;
	private List<String> roles = new ArrayList<String>();
	
	
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

	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public Long getIdDepartement() {
		return idDepartement;
	}
	public void setIdDepartement(Long idDepartement) {
		this.idDepartement = idDepartement;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	

}
