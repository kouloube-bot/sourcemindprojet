package com.publi.gestionpub.entité;

import javax.persistence.*;

import com.publi.gestionpub.enume.PublicationStatus;

import java.util.Date;
import java.util.List;

@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublication;
    private String titre;
    private String resume;
    private String fichierPDF;
    private Date dateSoumission;
    @Enumerated(EnumType.STRING)
    private PublicationStatus status = PublicationStatus.SOUMISE;// "soumise", "en révision", "validée"
    private String type;
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Revision> revisions;
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private AppUser utilisateur;
    public Publication() {}
    public Publication(String titre, String resume, String fichierPDF, Date dateSoumission, PublicationStatus status, String type, AppUser utilisateur) {
        this.titre = titre;
        this.resume = resume;
        this.fichierPDF = fichierPDF;
        this.dateSoumission = dateSoumission;
        this.status = status;
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
    public PublicationStatus getStatus() {
        return status;
    }
    public void setStatus(PublicationStatus status) {
        this.status = status;
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
