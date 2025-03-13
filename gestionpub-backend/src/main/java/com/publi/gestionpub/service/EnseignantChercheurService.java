package com.publi.gestionpub.service;

import java.util.List;

import com.publi.gestionpub.Dto.EnseignantChercheurDto;
import com.publi.gestionpub.entité.EnseignantChercheur;




public interface EnseignantChercheurService {
	public EnseignantChercheur ajouterchercheur(EnseignantChercheur enseignantChercheur);
	public EnseignantChercheurDto addEnseignantChercheur(EnseignantChercheurDto chercheurDto);
	//public void modifierenseignantChercheur(Long id ,EnseignantChercheur enseignantChercheur);
	public void supprimerenseignantChercheur(Long id);
	public List<EnseignantChercheur>aficherenseignantChercheurtous();
	EnseignantChercheur findById(Long id);
	//public Etudiant findByNom(String nom);
	public EnseignantChercheur findByEmail(String Email);
	public EnseignantChercheur findByUsername(String username);
	public int nbrenseignantChercheur();
	

}
