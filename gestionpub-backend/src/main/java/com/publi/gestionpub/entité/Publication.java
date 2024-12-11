package com.publi.gestionpub.entité;

import javax.persistence.*;

import java.util.Date;

@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublication;
    private String titre;
    private String resume;
    private String fichierPDF;
    private Date dateSoumission;
    private String statut; // "soumise", "en révision", "validée"
    private String type;
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private AppUser utilisateur;
    public Publication() {}
    public Publication(String titre, String resume, String fichierPDF, Date dateSoumission, String statut, String type, AppUser utilisateur) {
        this.titre = titre;
        this.resume = resume;
        this.fichierPDF = fichierPDF;
        this.dateSoumission = dateSoumission;
        this.statut = statut;
        this.type = type;
        this.utilisateur = utilisateur;

    }
    public Long getIdPublication() {
        return idPublication;
    }
    public void setIdPublication(Long idPublication) {
        this.idPublication = idPublication;
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
    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public AppUser getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(AppUser utilisateur) {
        this.utilisateur = utilisateur;
    }

}
