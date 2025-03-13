package com.publi.gestionpub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.publi.gestionpub.entité.AppRoles;


public interface AppRolesRepository extends JpaRepository<AppRoles, Long> {
	@Query("select r from AppRoles r where r.roleName =:x")
	public AppRoles  findByRoleName(@Param("x") String roleName);
}

