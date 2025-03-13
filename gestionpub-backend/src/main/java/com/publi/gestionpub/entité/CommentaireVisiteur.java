package com.publi.gestionpub.entité;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "commentaires_visiteur")
public class CommentaireVisiteur {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @ManyToOne
	    @JoinColumn(name = "publication_id")
	    private Publication publication;
	    
	    @Column(nullable = false)
	    private String nomVisiteur;  
	    
	    @Column(columnDefinition = "TEXT", nullable = false)
	    private String content;
	    
	    @Column(nullable = false)
	    private LocalDateTime date;

	    // Constructeur par défaut
	    public CommentaireVisiteur() {
	        this.date = LocalDateTime.now();
	    }

	    // Getters et Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Publication getPublication() {
	        return publication;
	    }

	    public void setPublication(Publication publication) {
	        this.publication = publication;
	    }

	    public String getNomVisiteur() {
	        return nomVisiteur;
	    }

	    public void setNomVisiteur(String nomVisiteur) {
	        this.nomVisiteur = nomVisiteur;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

	    public LocalDateTime getDate() {
	        return date;
	    }

	    public void setDate(LocalDateTime date) {
	        this.date = date;
	    }

}
