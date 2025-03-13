package com.publi.gestionpub.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.publi.gestionpub.entité.CommentaireVisiteur;
import com.publi.gestionpub.entité.Publication;
import com.publi.gestionpub.repository.CommentaireVisiteurRepository;
import com.publi.gestionpub.repository.PublicationRepository;
import com.publi.gestionpub.service.CommentaireVisiteurService;

@Service
public class CommentaireVisiteurServiceImple implements CommentaireVisiteurService{
	
	 @Autowired
    private CommentaireVisiteurRepository commentaireRepository;
    
    @Autowired
    private PublicationRepository publicationRepository;

	@Override
	public List<CommentaireVisiteur> getCommentairesByPublication(Long publicationId) {
        return commentaireRepository.findByPublicationIdPublicationOrderByDateDesc(publicationId);
    }

	@Override
	public CommentaireVisiteur ajouterCommentaire(Long publicationId, String nomVisiteur, String content) {
        if (nomVisiteur == null || nomVisiteur.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du visiteur est requis");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Le commentaire ne peut pas être vide");
        }

        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication non trouvée"));

        CommentaireVisiteur commentaire = new CommentaireVisiteur();
        commentaire.setPublication(publication);
        commentaire.setNomVisiteur(nomVisiteur.trim());
        commentaire.setContent(content.trim());
        
        return commentaireRepository.save(commentaire);
    }
	}


