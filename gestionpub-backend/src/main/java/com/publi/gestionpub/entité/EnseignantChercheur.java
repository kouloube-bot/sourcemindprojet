package com.publi.gestionpub.entité;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;

@Entity 
public class EnseignantChercheur {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(unique=true,nullable=false)
	private String username;
	@Column(unique=true,nullable=false)
	private String email;
	private String password;
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<AppRoles> roles=new ArrayList<>();
	
	public EnseignantChercheur(Long id, String username, String email, String password, Collection<AppRoles> roles) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Collection<AppRoles> getRoles() {
		return roles;
	}
	public void setRoles(Collection<AppRoles> roles) {
		this.roles = roles;
	}
	

}
