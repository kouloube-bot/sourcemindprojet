package com.publi.gestionpub.entité;

import javax.persistence.*;

@Entity
public class Revision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRevision;
    private String commentaires;
    private int note;// Note de 1 à 5
    private boolean effectuee;
    public boolean isEffectuee() {
		return effectuee;
	}
	public void setEffectuee(boolean effectuee) {
		this.effectuee = effectuee;
	}
	@ManyToOne
    @JoinColumn(name = "id_publication")
    private Publication publication;
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private AppUser utilisateur;
    public Revision() {}
    
    public Revision(Long idRevision, String commentaires, int note, boolean effectuee, Publication publication,
			AppUser utilisateur) {
		super();
		this.idRevision = idRevision;
		this.commentaires = commentaires;
		this.note = note;
		this.effectuee = effectuee;
		this.publication = publication;
		this.utilisateur = utilisateur;
	}
	public Long getIdRevision() {
        return idRevision;
    }
    public void setIdRevision(Long idRevision) {
        this.idRevision = idRevision;
    }
    public String getCommentaires() {
        return commentaires;
    }
    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }
    public int getNote() {
        return note;
    }
    public void setNote(int note) {
        this.note = note;
    }
    public Publication getPublication() {
        return publication;
    }
    public void setPublication(Publication publication) {
        this.publication = publication;
    }
    public AppUser getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(AppUser utilisateur) {
        this.utilisateur = utilisateur;
    }

}
