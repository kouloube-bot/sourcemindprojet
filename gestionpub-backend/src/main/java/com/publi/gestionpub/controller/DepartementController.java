package com.publi.gestionpub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.publi.gestionpub.Dto.DepartementDto;
import com.publi.gestionpub.entité.Departement;
import com.publi.gestionpub.mapper.Mapper;
import com.publi.gestionpub.repository.DepartementRepository;
import com.publi.gestionpub.service.DepartementService;


@RestController
@CrossOrigin(origins = "*")
public class DepartementController {
	
@Autowired
private DepartementRepository departementRepository;
@Autowired
DepartementService departementService;
	@PostMapping("/departement" )
	public DepartementDto ajouterDepartement(@RequestBody DepartementDto departementDto){
	
		return departementService.addDepartement(departementDto);
	}
	
	@GetMapping("/departements")
	public List<Departement> getDepartements() {
	  // Récupérer tous les départements
	    return departementRepository.findAll(); // Mapper les entités en DTO
	}
	@GetMapping("/departemen/all")
    public List<DepartementDto> getAllDepartements() {
        return departementService.affichertous();  // Appeler la méthode pour récupérer les départements
    }

}
