package com.publi.gestionpub.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.publi.gestionpub.Dto.EnseignantChercheurDto;
import com.publi.gestionpub.entité.EnseignantChercheur;
import com.publi.gestionpub.repository.AppRolesRepository;
import com.publi.gestionpub.repository.EnseignantChercheurRepository;
import com.publi.gestionpub.repository.UserRepository;
import com.publi.gestionpub.service.EnseignantChercheurService;


@CrossOrigin(origins = "*")
@RestController
public class EnseignantChercheurController {
	
	@Autowired
	 private PasswordEncoder passwordEncoder;
	@Autowired
	private EnseignantChercheurRepository  enseignantChercheurRepository;
	@Autowired
	private AppRolesRepository appRolesRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private  EnseignantChercheurService  enseignantChercheurService;
	
	@PostMapping("/chercheuradd" )
	public   EnseignantChercheurDto ajouterformateur(@RequestBody  EnseignantChercheurDto  enseignantChercheur) {
		return enseignantChercheurService.addEnseignantChercheur(enseignantChercheur);
	}
	@RequestMapping(value = "/chercheur/all", method=RequestMethod.GET)
	public List<EnseignantChercheur> afficherTousFormateurcontrole(){
		return enseignantChercheurService.aficherenseignantChercheurtous();
	}
	@RequestMapping(value = "/chercheur/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<String>supprimercontrole(@PathVariable("id") Long id) {
		enseignantChercheurService.supprimerenseignantChercheur(id);
		return new ResponseEntity<String>("etudiant a bien été suprimé", HttpStatus.OK);
		
	}
	@RequestMapping(value = "/chercheur/detail/{id}", method = RequestMethod.GET)   
	public ResponseEntity<EnseignantChercheur> getEtudiantId(@PathVariable("id") Long id) {
		EnseignantChercheur enseignantChercheur=enseignantChercheurService.findById(id);
		return new ResponseEntity<EnseignantChercheur>(enseignantChercheur, HttpStatus.OK);
	}
	@RequestMapping(value = "/chercheur/nbr", method = RequestMethod.GET)   
	public int nbrFormateur(){
		
		return enseignantChercheurService.nbrenseignantChercheur();
	}
	@RequestMapping(value = "/chercheur/{username}", method = RequestMethod.GET)   
	public ResponseEntity<EnseignantChercheur> getEtudiantByUsername(@PathVariable("username") String username) {
		EnseignantChercheur enseignantChercheur=enseignantChercheurService.findByUsername(username);
		return new ResponseEntity<EnseignantChercheur>(enseignantChercheur, HttpStatus.OK);
	}
	@GetMapping("/enseignat/all")
    public List<EnseignantChercheur> getAllChercheurs() {
        return enseignantChercheurService.aficherenseignantChercheurtous();
    }
	
	

}
