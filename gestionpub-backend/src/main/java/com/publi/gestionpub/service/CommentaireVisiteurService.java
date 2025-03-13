package com.publi.gestionpub.service;

import java.util.List;

import com.publi.gestionpub.entité.CommentaireVisiteur;

public interface CommentaireVisiteurService {
	public List<CommentaireVisiteur> getCommentairesByPublication(Long publicationId);
	public CommentaireVisiteur ajouterCommentaire(Long publicationId, String nomVisiteur, String content);

}
