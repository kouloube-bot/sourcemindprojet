package com.publi.gestionpub.entité;


import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.Email;



@Entity
@Table(name = "utilisateur")
public class AppUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    private String email;
    private boolean online;
    @Column(name = "HASHED_PASSWORD", nullable = true)
    private String password;
    @OneToOne
    private EnseignantChercheur enseignantChercheur;
    @Column(unique = true)
    private String username;
    
    @ManyToMany(fetch=FetchType.EAGER)
	private Collection<AppRoles> roles=new ArrayList<>();
    
    public AppUser() {}
    public AppUser(Long id, String email, String password, Collection<AppRoles> roles) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.roles = roles;;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
 
	public Collection<AppRoles> getRoles() {
		return roles;
	}
	public void setRoles(Collection<AppRoles> roles) {
		this.roles = roles;
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
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public EnseignantChercheur getEnseignantChercheur() {
		return enseignantChercheur;
	}
	public void setEnseignantChercheur(EnseignantChercheur enseignantChercheur) {
		this.enseignantChercheur = enseignantChercheur;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
   
	

}
