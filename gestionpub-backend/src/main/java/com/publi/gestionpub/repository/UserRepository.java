package com.publi.gestionpub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.publi.gestionpub.entité.AppUser;


public interface UserRepository extends JpaRepository<AppUser, Long> {
	public AppUser findByUsername(String username);
	
	@Query("SELECT COUNT(*) FROM AppUser")
	public int nbruser();
	@Query("select u from AppUser u where u.email=:x")
    Optional<AppUser> findByEmail(@Param("x") String email);
    @Query("select u from AppUser u where u.online=1")
    public List<AppUser> getByUserOnLigne();
    @Query("select u from AppUser u")
    List<AppUser> findAll();
    
    
    @Query("select p from Utilisateur p where p.supprimer is false and lower(p.email) like concat('%', :key,'%')")
    Page<AppUser> findAllByKey(Pageable pageable, @Param("key") String key);
    

}
