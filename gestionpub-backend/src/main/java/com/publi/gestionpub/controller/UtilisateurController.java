package com.publi.gestionpub.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.publi.gestionpub.Dto.AppUserDto;
import com.publi.gestionpub.Dto.ApproleDto;
import com.publi.gestionpub.Dto.UserDTO;
import com.publi.gestionpub.entité.AppRoles;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.service.UtilisateurService;
import com.publi.gestionpub.serviceImpl.ReviseurDto;






@RestController
@CrossOrigin(origins = "*")
public class UtilisateurController {

	
	@Autowired
	UtilisateurService  utilisateurService;
	
	@GetMapping("/utilisateur/password/{username}/{password}")
	public AppUserDto getPassword(@PathVariable String username,@PathVariable String password) {
		return utilisateurService.getPassword(username, password);
	}
	
	@PostMapping("/utilisateur/add")
    public AppUserDto newUtilisateurDto(@RequestBody AppUserDto newUtilisateurdto) {
        //logger.info("\n\ninserting new DistinctionRef {}\n\n", newDistinctionRef.getLibelle());
        return utilisateurService.save(newUtilisateurdto);
    }
	@PostMapping("/users/add")
    public AppUserDto ajouterUtilisateurDto(@RequestBody AppUserDto newUtilisateurdto) {
        //logger.info("\n\ninserting new DistinctionRef {}\n\n", newDistinctionRef.getLibelle());
        return utilisateurService.addUtilisateur(newUtilisateurdto);
    }
	
	@PutMapping("/utilisateur/{id}")
    public AppUserDto updateUtilisateurDto(@RequestBody AppUserDto utilisateurDto, @PathVariable Long id) {
    	return utilisateurService.updateUtilisateur(utilisateurDto, id);       
    }
	
	@PostMapping("/role/add")
	public ApproleDto roles(@RequestBody ApproleDto approle) {
		return utilisateurService.addRoles(approle);
		
	}
	@RequestMapping(value="/roles" ,method=RequestMethod.GET)	
	public List<AppRoles> listerroles(){
		return utilisateurService.touslesroles();
	}

	
	@GetMapping("/utilisateur/{id}")
	public AppUserDto findById(@PathVariable String uuid){
		return utilisateurService.getUtilisateur(uuid);
	}
	
	@GetMapping("/utilisateurEmail/{email}")
	public AppUserDto findByEmail(@PathVariable String email){
		return utilisateurService.getUtilisateurByEmail(email);
	}
	@GetMapping("/utilisateur/all")
    public List<AppUserDto> getAllUsers() {
        return utilisateurService.findAll();  
    }
	@GetMapping("/reviseurs/all")
    public ResponseEntity<List<ReviseurDto>> getReviseurs() {
        List<ReviseurDto> reviseurs = utilisateurService.getReviseurs();
        return ResponseEntity.ok(reviseurs);
    }
	 @GetMapping("/info/utilisateur")
	    public ResponseEntity<UserDTO> getUserInfo() {
	        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	        UserDTO userDTO = utilisateurService.getUserInfoByEmail(email);
	        return ResponseEntity.ok(userDTO);
	    }
}
