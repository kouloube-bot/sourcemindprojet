package com.publi.gestionpub.entité;

import javax.persistence.*;

@Entity
public class AppRoles {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	 @Column(unique = true, nullable = false)
	private String roleName;
	
	public AppRoles(Long id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}
	
	public AppRoles() {
		super();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return this.roleName;
	}
	

}

