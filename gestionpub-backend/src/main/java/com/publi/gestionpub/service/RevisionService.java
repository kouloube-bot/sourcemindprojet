package com.publi.gestionpub.service;

import java.util.List;

import com.publi.gestionpub.Dto.RevisionDto;
import com.publi.gestionpub.Dto.RevisionsoumetDto;
import com.publi.gestionpub.entité.Publication;
import com.publi.gestionpub.entité.Revision;

public interface RevisionService {
	public void AffecterRevision(RevisionDto dto);
	public void SoumettreRevisionPublication(RevisionsoumetDto dto);
	public List<Revision> getRevisionForCurrentUser();
	public List<Revision> getRevisionAllTrue();
	public List<Revision> getRevisionAllFalse();
	public List<Revision> getRevisionCurrentUser();
	public List<Revision> getRevisionForCurrentUserValide();
	public String validerPublication(Long idRevision);
	//public List<Publication> getPublicationsEnRevisionForCurrentUser();
	 public List<Revision> getValidatedRevisionsForCurrentUserchercheur();
	 public List<Revision> getRevisionsByTerminer(String email);
	

}
