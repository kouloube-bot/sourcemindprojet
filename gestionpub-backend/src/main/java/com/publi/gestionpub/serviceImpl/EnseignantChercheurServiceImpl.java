package com.publi.gestionpub.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publi.gestionpub.Dto.AppUserDto;
import com.publi.gestionpub.Dto.EnseignantChercheurDto;
import com.publi.gestionpub.entité.AppRoles;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.entité.Departement;
import com.publi.gestionpub.entité.EnseignantChercheur;
import com.publi.gestionpub.mapper.Mapper;
import com.publi.gestionpub.repository.AppRolesRepository;
import com.publi.gestionpub.repository.DepartementRepository;
import com.publi.gestionpub.repository.EnseignantChercheurRepository;
import com.publi.gestionpub.repository.UserRepository;
import com.publi.gestionpub.service.EnseignantChercheurService;
import com.publi.gestionpub.service.UtilisateurService;

@Service("enseignantChercheurService")
public class EnseignantChercheurServiceImpl implements EnseignantChercheurService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private  EnseignantChercheurRepository  enseignantChercheurRepository;
	@Autowired
	private AppRolesRepository appRolesRepository;
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private DepartementRepository departementRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public EnseignantChercheur ajouterchercheur(EnseignantChercheur enseignantChercheur) {
		// TODO Auto-generated method stub
	String hashpw=passwordEncoder.encode(enseignantChercheur.getPassword());
		enseignantChercheur.setPassword(hashpw);
		return enseignantChercheurRepository.save(enseignantChercheur);
	}

	/*@Override
	public void modifierenseignantChercheur(Long id, EnseignantChercheur enseignantChercheur) {
		EnseignantChercheur enseignantChercheurs=findById(id);
		String hashpw=passwordEncoder.encode(enseignantChercheur.getPassword());
		enseignantChercheurs.setPassword(hashpw);
		enseignantChercheurs.setUsername(enseignantChercheur.getUsername());
		enseignantChercheurs.setEmail(enseignantChercheur.getEmail());
		enseignantChercheurs.setRoles(enseignantChercheur.getRoles());
		enseignantChercheurRepository.saveAndFlush(enseignantChercheurs);
		
	}*/

	@Override
	public void supprimerenseignantChercheur(Long id) {
		// TODO Auto-generated method stub
		EnseignantChercheur enseignantChercheur=findById(id);
		enseignantChercheurRepository.delete(enseignantChercheur);
		
	}

	@Override
	public List<EnseignantChercheur> aficherenseignantChercheurtous() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    AppUser utilisateur = userRepository.findByEmail(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
	    
	    // Vérifier si l'utilisateur a le rôle ADMIN
	    boolean hasAdminRole = utilisateur.getRoles().stream()
	            .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));
	    
	    if (!hasAdminRole) {
	        throw new AccessDeniedException("Vous devez être un admin pour accéder à cette page");
	    }

	    List<EnseignantChercheur> chercheurs = enseignantChercheurRepository.findAll();
	   
	    
	    return chercheurs;
	}
	@Override
	public EnseignantChercheur findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public EnseignantChercheurDto addEnseignantChercheur(EnseignantChercheurDto chercheurDto) {
		// TODO Auto-generated method stub
		EnseignantChercheur enseignantChercheur=new EnseignantChercheur();
		Departement departement = departementRepository.findById(chercheurDto.getIdDepartement()).orElseThrow(null);
		enseignantChercheur.setEmail(chercheurDto.getEmail());
		enseignantChercheur.setNom(chercheurDto.getNom());
		enseignantChercheur.setPrenom(chercheurDto.getPrenom());
		
		enseignantChercheur.setDepartement(departement);
		
		enseignantChercheur = enseignantChercheurRepository.save(enseignantChercheur);
		AppRoles chercheurRole = appRolesRepository.findByRoleName("CHERCHEUR");
	    if (chercheurRole == null) {
	        throw new RuntimeException("Le rôle CHERCHEUR est introuvable dans la base de données");
	    }
		if(enseignantChercheur.getEmail() == chercheurDto.getEmail()) {
			
			AppUserDto appUserDto = new AppUserDto();
			appUserDto.setEmail(enseignantChercheur.getEmail());
			appUserDto.setPassword(chercheurDto.getPassword());
			appUserDto.setUsername(chercheurDto.getUsername());
			appUserDto.setIdEnseignantChercheur(enseignantChercheur.getId());
			Collection<AppRoles> roles = new ArrayList<>();
		    roles.add(chercheurRole);
		    appUserDto.setRoles(roles);
			utilisateurService.save(appUserDto);
		}
		
		return Mapper.toEnseignantChercheurDto(enseignantChercheur);
	}

}
