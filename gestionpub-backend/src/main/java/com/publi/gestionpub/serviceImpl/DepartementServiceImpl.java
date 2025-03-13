package com.publi.gestionpub.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.publi.gestionpub.Dto.DepartementDto;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.entité.Departement;
import com.publi.gestionpub.mapper.Mapper;
import com.publi.gestionpub.repository.DepartementRepository;
import com.publi.gestionpub.repository.UserRepository;
import com.publi.gestionpub.service.DepartementService;

@Service
public class DepartementServiceImpl implements DepartementService{
	private static final Logger log = LoggerFactory.getLogger(DepartementServiceImpl.class);
   @Autowired
	private UserRepository userRepository;
   @Autowired	
   private DepartementRepository departementRepository;

	@Override
	public DepartementDto addDepartement(DepartementDto departementDto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        log.info("Utilisateur trouvé : " + utilisateur.getUsername());
        // Vérifier le rôle de l'utilisateur
        boolean hasadminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));
        log.info("L'utilisateur a le rôle ADMIN : " + hasadminRole);
        if (!hasadminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour valide toutes les publications");
        }
		
		Departement dept=new Departement();
	
		dept.setDescription(departementDto.getDescription());
	    dept.setLibelle(departementDto.getLibelle());
		Departement savedep=departementRepository.save(dept);
	
		return Mapper.toDepartementDto(savedep);
	}

	@Override
	public DepartementDto modifierdepartement(Long id, DepartementDto departementDto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        log.info("Utilisateur trouvé : " + utilisateur.getUsername());
        // Vérifier le rôle de l'utilisateur
        boolean hasadminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));
        log.info("L'utilisateur a le rôle ADMIN : " + hasadminRole);
        if (!hasadminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour effectuer cette operation");
        }
		Departement dept=departementRepository.findById(id).orElseThrow(null);
		dept.setId(departementDto.getId());
		dept.setDescription(departementDto.getDescription());
		dept.setLibelle(departementDto.getLibelle());
		return Mapper.toDepartementDto(dept);
		
		
		
	}

	@Override
	public DepartementDto getOne(Long id){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        log.info("Utilisateur trouvé : " + utilisateur.getUsername());
        // Vérifier le rôle de l'utilisateur
        boolean hasadminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));
        log.info("L'utilisateur a le rôle ADMIN : " + hasadminRole);
        if (!hasadminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour effectuer cette operation");
        }
		Departement dept=departementRepository.findById(id).orElseThrow(null);
		return Mapper.toDepartementDto(dept);
	}

	@Override
	public List<DepartementDto> affichertous() {
		
		List<Departement> departements = departementRepository.findAll();
		List<DepartementDto> departementDtos = new ArrayList<DepartementDto>();
		departements.forEach(dep -> departementDtos.add(Mapper.toDepartementDto(dep)));
		return departementDtos;
	}

}
