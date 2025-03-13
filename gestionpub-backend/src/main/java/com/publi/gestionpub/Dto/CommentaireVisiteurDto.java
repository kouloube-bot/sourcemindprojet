package com.publi.gestionpub.Dto;

import java.time.LocalDateTime;

public class CommentaireVisiteurDto {
	
	    private Long id;
	    private String nomVisiteur;
	    private String content;
	    private LocalDateTime date;

	    public CommentaireVisiteurDto(Long id, String nomVisiteur, String content, LocalDateTime date) {
	        this.id = id;
	        this.nomVisiteur = nomVisiteur;
	        this.content = content;
	        this.date = date;
	    }

	    // Getters et Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
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

