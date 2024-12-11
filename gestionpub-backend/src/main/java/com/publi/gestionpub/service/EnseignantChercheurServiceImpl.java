package com.publi.gestionpub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publi.gestionpub.entité.EnseignantChercheur;
import com.publi.gestionpub.repository.EnseignantChercheurRepository;

@Service
public class EnseignantChercheurServiceImpl implements EnseignantChercheurService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private  EnseignantChercheurRepository  enseignantChercheurRepository;
	
	@Override
	public EnseignantChercheur ajouterchercheur(EnseignantChercheur enseignantChercheur) {
		// TODO Auto-generated method stub
	String hashpw=passwordEncoder.encode(enseignantChercheur.getPassword());
		enseignantChercheur.setPassword(hashpw);
		return enseignantChercheurRepository.save(enseignantChercheur);
	}

	@Override
	public void modifierenseignantChercheur(Long id, EnseignantChercheur enseignantChercheur) {
		EnseignantChercheur enseignantChercheurs=findById(id);
		String hashpw=passwordEncoder.encode(enseignantChercheur.getPassword());
		enseignantChercheurs.setPassword(hashpw);
		enseignantChercheurs.setUsername(enseignantChercheur.getUsername());
		enseignantChercheurs.setEmail(enseignantChercheur.getEmail());
		enseignantChercheurs.setRoles(enseignantChercheur.getRoles());
		enseignantChercheurRepository.saveAndFlush(enseignantChercheurs);
		
	}

	@Override
	public void supprimerenseignantChercheur(Long id) {
		// TODO Auto-generated method stub
		EnseignantChercheur enseignantChercheur=findById(id);
		enseignantChercheurRepository.delete(enseignantChercheur);
		
	}

	@Override
	public List<EnseignantChercheur> aficherenseignantChercheurtous() {
		// TODO Auto-generated method stub
		return enseignantChercheurRepository.findAll();
	}
	@Override
	public EnseignantChercheur findByUsername(String username) {
		// TODO Auto-generated method stub
		return enseignantChercheurRepository.findByUsername(username);
	}
	@Override
	public EnseignantChercheur findById(Long id) {
		// TODO Auto-generated method stub
		if(enseignantChercheurRepository.findById(id).isPresent()) {
			return enseignantChercheurRepository.findById(id).get();
		}
		return null;
	}
	
	@Override
	public EnseignantChercheur findByEmail(String Email) {
		// TODO Auto-generated method stub
		return enseignantChercheurRepository.findByEmail(Email);
	}
	@Override
	public int nbrenseignantChercheur() {
		// TODO Auto-generated method stub
		return enseignantChercheurRepository.nbrenseignantChercheur();
	}

}
