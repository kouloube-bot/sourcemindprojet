package com.publi.gestionpub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.entité.Publication;
import com.publi.gestionpub.entité.Revision;
import com.publi.gestionpub.enume.PublicationStatus;



public interface RevisionRepository extends JpaRepository<Revision, Long> {
	@Query("SELECT r FROM Revision r WHERE r.utilisateur.id = :id_utilisateur")
    List<Revision> findByUtilisateurId(@Param("id_utilisateur") Long id_utilisateur);
	@Query("SELECT r FROM Revision r WHERE r.effectuee=true")
	List<Revision> findByeffectueTrue();
	@Query("SELECT r FROM Revision r WHERE r.effectuee=false")
	List<Revision> findByeffectueFalse();
	Optional<Revision> findByUtilisateurAndEffectueeFalse(AppUser utilisateur);
	public List<Revision> findByUtilisateurIdAndPublicationStatusAndEffectueeFalse(Long utilisateurId, String status);
	 @Query("SELECT r FROM Revision r " +
	           "WHERE r.publication.status = :status " +
	           "AND r.utilisateur.email = :email")
	    List<Revision> findByPublicationStatusAndUtilisateurEmail(
	        @Param("status") PublicationStatus status, 
	        @Param("email") String email
	    );

	 @Query("SELECT r FROM Revision r WHERE r.effectuee = true AND r.utilisateur.email = :email")
	    List<Revision> findCompletedRevisionsByReviewer(@Param("email") String email);
}