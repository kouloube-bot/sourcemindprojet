package com.publi.gestionpub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.entité.Publication;


public interface UserRepository extends JpaRepository<AppUser, Long> {
	@Query("SELECT COUNT(*) FROM AppUser")
	public int nbruser();
	@Query("select u from AppUser u where u.email=:x")
    Optional<AppUser> findByEmail(@Param("x") String email);
    @Query("select u from AppUser u where u.online=1")
    public List<AppUser> getByUserOnLigne();
    @Query("select u from AppUser u")
    List<AppUser> findAll();
    @Query("SELECT u FROM AppUser u JOIN FETCH u.roles WHERE u.email = :email")
    Optional<AppUser> findByEmailWithRoles(@Param("email") String email);
    @Query("SELECT u FROM AppUser u JOIN u.roles r WHERE r.roleName = 'REVISEUR'")
    List<AppUser> findUsersWithReviseurRole();
 
    
    

}
