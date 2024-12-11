package com.publi.gestionpub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publi.gestionpub.entité.AppRoles;


public interface AppRolesRepository extends JpaRepository<AppRoles, Long> {
	public AppRoles  findByRoleName(String roleName);
}

