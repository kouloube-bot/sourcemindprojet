package com.publi.gestionpub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publi.gestionpub.entité.CommentaireVisiteur;

public interface CommentaireVisiteurRepository extends JpaRepository<CommentaireVisiteur, Long> {
	List<CommentaireVisiteur> findByPublicationIdPublicationOrderByDateDesc(Long publicationId);
	
}
