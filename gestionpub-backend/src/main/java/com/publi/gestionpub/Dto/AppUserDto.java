package com.publi.gestionpub.Dto;

import java.util.ArrayList;
import java.util.Collection;


import com.publi.gestionpub.entité.AppRoles;





public class AppUserDto  {

    private Long id;
    private String email;
    private String password;
    private Collection<AppRoles> roles = new ArrayList<AppRoles>();
    
	public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
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

    

    

   

	


	
		

	

	

	
	
}
