package com.publi.gestionpub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.publi.gestionpub.entité.CommentRequest;
import com.publi.gestionpub.entité.CommentaireVisiteur;
import com.publi.gestionpub.service.CommentaireVisiteurService;

@RestController
@CrossOrigin(origins = "*")
public class CommentaireVisiteurController {
	
	 @Autowired
	    private CommentaireVisiteurService commentaireService;

	    @GetMapping("/publications/comments/{publicationId}")
	    public ResponseEntity<List<CommentaireVisiteur>> getComments(@PathVariable Long publicationId) {
	        try {
	            List<CommentaireVisiteur> comments = commentaireService.getCommentairesByPublication(publicationId);
	            return ResponseEntity.ok(comments);
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().build();
	        }
	    }
	    
	    @PostMapping("/publications/comments/{publicationId}")
	    public ResponseEntity<CommentaireVisiteur> addComment(
	            @PathVariable Long publicationId,
	            @RequestBody CommentRequest request) {
	        try {
	            CommentaireVisiteur comment = commentaireService.ajouterCommentaire(
	                publicationId, 
	                request.getNomVisiteur(), 
	                request.getContent()
	            );
	            return ResponseEntity.ok(comment);
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().build();
	        }
	    }

}
