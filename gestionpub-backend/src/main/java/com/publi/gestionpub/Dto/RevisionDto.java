package com.publi.gestionpub.Dto;

public class RevisionDto {
	private Long idRevision;
	private Long idPublication;
	private Long idUtilisateur;
	private String commentaires;
    private int note;// Note de 1 à 5
    private boolean effectuee=false;
	public Long getIdPublication() {
		return idPublication;
	}
	public void setIdPublication(Long idPublication) {
		this.idPublication = idPublication;
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
	public boolean isEffectuee() {
		return effectuee;
	}
	public void setEffectuee(boolean effectuee) {
		this.effectuee = effectuee;
	}
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
    

}
