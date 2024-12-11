package com.publi.gestionpub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.publi.gestionpub.entité.AppRoles;
import com.publi.gestionpub.entité.EnseignantChercheur;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.repository.AppRolesRepository;
import com.publi.gestionpub.repository.EnseignantChercheurRepository;
import com.publi.gestionpub.repository.UserRepository;
import com.publi.gestionpub.service.EnseignantChercheurService;



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
	public   EnseignantChercheur ajouterformateur(@RequestBody  EnseignantChercheur  enseignantChercheur) {
		 EnseignantChercheur  enseignantChercheurs  = this.enseignantChercheurRepository.findByUsername(enseignantChercheur.getUsername());
		if(enseignantChercheurs!=null)
			throw new RuntimeException("ce nom existe");
		String ashpw=passwordEncoder.encode(enseignantChercheur.getPassword());
		enseignantChercheur.setPassword(ashpw);
		List<AppRoles> roles = new ArrayList<AppRoles>();
		AppRoles app = this.appRolesRepository.findByRoleName("CHERCHEUR");
		System.out.println(" ****************** " + app.getRoleName() );
		roles.add(app);
		enseignantChercheur.setRoles(roles);
		 EnseignantChercheur savedchercheur= this.enseignantChercheurRepository.save(enseignantChercheur);
		if(savedchercheur!=null) {
			AppUser appUser = new AppUser(null,savedchercheur.getUsername(),savedchercheur.getEmail(),savedchercheur.getPassword(),roles);
			userRepository.save(appUser);
		}
		return savedchercheur;
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
	@RequestMapping(value="/chercheur/edit/{id}" ,method=RequestMethod.PUT)
	public void modifieruser(@PathVariable("id") Long id ,@RequestBody EnseignantChercheur enseignantChercheur) {
		EnseignantChercheur enseignantChercheurs=enseignantChercheurService.findById(id);
		String hashpw=passwordEncoder.encode(enseignantChercheur.getPassword());
		enseignantChercheurs.setPassword(hashpw);
		enseignantChercheurs.setUsername(enseignantChercheur.getUsername());
		enseignantChercheurs.setEmail(enseignantChercheur.getEmail());
		enseignantChercheurs.setRoles(enseignantChercheur.getRoles());
		enseignantChercheurService.modifierenseignantChercheur(id, enseignantChercheurs);
	}
	

}
