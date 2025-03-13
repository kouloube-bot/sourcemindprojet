package com.publi.gestionpub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.publi.gestionpub.entité.EnseignantChercheur;

public interface EnseignantChercheurRepository extends JpaRepository<EnseignantChercheur, Long>{
	
	public EnseignantChercheur findByEmail(String Email);
	@Query("SELECT COUNT(*) FROM EnseignantChercheur")
	public int nbrenseignantChercheur();
}
