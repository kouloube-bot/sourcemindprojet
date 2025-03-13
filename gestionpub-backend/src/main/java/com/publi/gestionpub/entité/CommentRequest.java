package com.publi.gestionpub.entité;

public class CommentRequest {
	private String nomVisiteur;
    private String content;

    // Getters et Setters
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
}
