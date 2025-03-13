package com.publi.gestionpub.Dto;

import java.util.Date;

import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.enume.PublicationStatus;

public class PublicationDto {
	private Long idPublication;
	private String titre;
    private String resume;
    private String fichierPDF;
    private Date dateSoumission;
    private PublicationStatus status = PublicationStatus.SOUMISE;
    private Long utilisateurId;
    private String type;
    
    
    
	public Long getIdPublication() {
		return idPublication;
	}
	public void setIdPublication(Long idPublication) {
		this.idPublication = idPublication;
	}
	public Long getUtilisateurId() {
		return utilisateurId;
	}
	public void setUtilisateurId(Long utilisateurId) {
		this.utilisateurId = utilisateurId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getFichierPDF() {
		return fichierPDF;
	}
	public void setFichierPDF(String fichierPDF) {
		this.fichierPDF = fichierPDF;
	}
	
	public Date getDateSoumission() {
		return dateSoumission;
	}
	public void setDateSoumission(Date dateSoumission) {
		this.dateSoumission = dateSoumission;
	}
	public PublicationStatus getStatus() {
		return status;
	}
	public void setStatus(PublicationStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
	    return "PublicationDto{" +
	           "idPublication=" + idPublication +
	           ", titre='" + titre + '\'' +
	           ", resume='" + resume + '\'' +
	           ", fichierPDF='" + fichierPDF + '\'' +
	           ", dateSoumission=" + dateSoumission +
	           ", status=" + status +
	           ", utilisateurId=" + utilisateurId +
	           ", type='" + type + '\'' +
	           '}';
	}
}
