package com.publi.gestionpub.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.publi.gestionpub.Dto.PublicationDto;
import com.publi.gestionpub.entité.Publication;
import com.publi.gestionpub.entité.Revision;


public interface PublicationService {
	public Publication  createPublication(PublicationDto publicationDto, MultipartFile fichierPDF);
	public List<Publication> getPublicationsForCurrentUser();
	public List<Publication> getAll();
	public void validerPublication(Long idPublication);
	public void rejetterPublication(Long idPublication);
	public List<Publication> rechercherParType(@Param("type") String type);
	public void EncourdeRevisionPublication(Long idPublication);
	public List<PublicationDto> getValidatedPublications();
	public List<PublicationDto> getSoumisPublications();
	public List<PublicationDto> getrevisionPublications();
	public List<PublicationDto> getValidatedPublicationsAll();
	public long countValidatedPublications() ;
	public long countSoumisPublications();
	public long countEnrevisionPublications();
	public Publication updatePublication(Long publicationId, PublicationDto publicationDto);
	public void deletePublication(Long publicationId);
	public List<Publication> getPublicationsEnRevisionNonEffectuee(String username);
	public List<Publication> getValidatedRevisionsForCurrentUserchercheur(String email);
	//public StatistiquesDTO getStats();
	
	
   
}
