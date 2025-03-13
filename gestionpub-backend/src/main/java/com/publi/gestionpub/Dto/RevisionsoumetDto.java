package com.publi.gestionpub.Dto;

public class RevisionsoumetDto {
	private Long idRevision;
	private String commentaires;
    private int note;// Note de 1 à 5
    //private boolean effectuee=false;
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
	
    
}
