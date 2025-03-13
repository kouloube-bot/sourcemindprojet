package com.publi.gestionpub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.publi.gestionpub.Dto.PublicationDto;
import com.publi.gestionpub.entité.Publication;
import com.publi.gestionpub.entité.Revision;
import com.publi.gestionpub.enume.PublicationStatus;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
	@Query("SELECT p FROM Publication p WHERE p.utilisateur.id = :id_utilisateur")
    List<Publication> findByUtilisateurId(@Param("id_utilisateur") Long id_utilisateur);
	@Query("SELECT p FROM Publication p")
	List<Publication> findAll();
	@Query("SELECT p FROM Publication p WHERE p.type LIKE %:type%")
	List<Publication> rechercherParType(@Param("type") String type);
	List<Publication> findByStatus(PublicationStatus status);
	public long countByStatus(PublicationStatus status);
	//Requête pour récupérer les publications d'un utilisateur avec un statut "soumise"
    List<Publication> findByUtilisateurIdAndStatus(Long utilisateurId, PublicationStatus status);
    @Query("SELECT COUNT(p) FROM Publication p WHERE p.status = :status")
    long countPublicationsByStatus(@Param("status") PublicationStatus status);
    //permet d'afficher liste publication encour de revision d'un enseignant chercheur
    @Query("SELECT p FROM Publication p " +
    	       "JOIN Revision r ON r.publication.idPublication = p.idPublication " +
    	       "WHERE p.utilisateur.id = :userId " +
    	       "AND p.status = :status " +
    	       "AND r.effectuee = false")
    	List<Publication> findPublicationsEnRevisionNonEffectuee(@Param("userId") Long userId, 
    	@Param("status") PublicationStatus status);
    int countByUtilisateurEmail(String email);
    int countByUtilisateurEmailAndStatus(String email, PublicationStatus status);
    List<Publication> findByStatusAndUtilisateur_Email(PublicationStatus status, String email);
    
}