package com.publi.gestionpub.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.publi.gestionpub.Dto.AppUserDto;
import com.publi.gestionpub.Dto.ApproleDto;
import com.publi.gestionpub.Dto.UserDTO;
import com.publi.gestionpub.entité.AppRoles;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.entité.EnseignantChercheur;
import com.publi.gestionpub.mapper.Mapper;
import com.publi.gestionpub.repository.AppRolesRepository;
import com.publi.gestionpub.repository.EnseignantChercheurRepository;
import com.publi.gestionpub.repository.UserRepository;
import com.publi.gestionpub.service.UtilisateurService;



@Service
public class UtilisateurServiceImpl implements UtilisateurService{
	private static final Logger log = LoggerFactory.getLogger(UtilisateurServiceImpl.class);
	@Autowired
	 private UserRepository userRepository;
	 @Autowired
	 private AppRolesRepository roleRepository;
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 @Autowired
	 private EnseignantChercheurRepository chercheurRepository;
	  
	@Override
	public AppUserDto save(AppUserDto userDto) {
		
		AppUser utilisateur = new AppUser();
		EnseignantChercheur chercheur = chercheurRepository.findById(userDto.getIdEnseignantChercheur()).orElseThrow(null);
		 utilisateur.setEmail(userDto.getEmail());
   	     utilisateur.setPassword(passwordEncoder.encode(userDto.getPassword()));
   	     utilisateur.setUsername(userDto.getUsername());
		 utilisateur.setRoles(userDto.getRoles());
		 utilisateur.setEnseignantChercheur(chercheur);
//		 utilisateur.setOnline(false);
		 
		 AppUser savedUtilisateur = userRepository.save(utilisateur);
		 return Mapper.toUtilisateurDto(savedUtilisateur);
	}

	@Override
	public AppUserDto updateUtilisateur(AppUserDto utilisateurDto, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUserDto getPassword(String usernameOrPhoneNumber, String newPassowd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUtilisateur(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AppUserDto getUtilisateur(String id) {
		return null;
	}

	@Override
	public AppUserDto getUtilisateurByEmail(String email) {
		Optional<AppUser> utilisateurOptional = userRepository.findByEmail(email);
		AppUser utilisateur = utilisateurOptional.get();
		return Mapper.toUtilisateurDto(utilisateur);
	}

	@Override
	public void deconnecterUtilisateur() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AppUser utilisateur = userRepository.findByEmail(auth.getName()).orElseThrow(null);
		utilisateur.setOnline(false);
		
	}

	@Override
	public List<AppUserDto> getByUserOnLigne() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppUserDto> findAll() {
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
				List<AppUser> liste =userRepository.findAll();
				final List<AppUserDto> listeDto = new ArrayList<AppUserDto>();
				liste.forEach(el -> listeDto.add(Mapper.toUtilisateurDto(el)));
				return listeDto;
	}

	@Override
	public List<AppRoles> touslesroles() {
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
		return roleRepository.findAll();
	}

	@Override
	public AppUser findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleterole(Long id) {
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
		AppRoles approle=roleRepository.getOne(id);
		roleRepository.delete(approle);;
	}

	@Override
	public int nbruser() {
		// TODO Auto-generated method stub
		return userRepository.nbruser();
	}

	@Override
	public AppRoles saveRoles(AppRoles role) {
		
		return null;
	}

	@Override
	public AppUserDto addUtilisateur(AppUserDto userDto) {
	
		AppUser utilisateur = new AppUser();
		 utilisateur.setEmail(userDto.getEmail());
   	     utilisateur.setPassword(passwordEncoder.encode(userDto.getPassword()));
   	     utilisateur.setUsername(userDto.getUsername());
   	  AppRoles reviseurRole = roleRepository.findByRoleName("REVISEUR");
      if (reviseurRole == null) {
          throw new RuntimeException("Le rôle REVISEUR est introuvable dans la base de données");
      }
      // Ajouter le rôle REVISEUR à l'utilisateur
      Collection<AppRoles> roles = new ArrayList<>();
      roles.add(reviseurRole);
      utilisateur.setRoles(roles);
	  utilisateur.setEnseignantChercheur(null);
         //utilisateur.setOnline(false);
		 AppUser savedUtilisateur = userRepository.save(utilisateur);
		 return Mapper.toUtilisateurDto(savedUtilisateur);
	}

	@Override
	public ApproleDto addRoles(ApproleDto approleDto) {
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
        AppRoles app=new AppRoles();
        app.setRoleName(approleDto.getRoleName());
		return Mapper.toRolesDto(roleRepository.save(app));
	}
	@Override
	public List<ReviseurDto> getReviseurs() {
        List<AppUser> reviseurs = userRepository.findUsersWithReviseurRole();
        return reviseurs.stream()
                .map(user -> new ReviseurDto(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }

	@Override
	public UserDTO getUserInfoByEmail(String email) {
		 AppUser user = userRepository.findByEmail(email)
		           .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

		       UserDTO userDTO = new UserDTO();
		       if(user.getEnseignantChercheur() != null) {
		           userDTO.setNom(user.getEnseignantChercheur().getNom());
		           userDTO.setPrenom(user.getEnseignantChercheur().getPrenom());
		       } else {
		           userDTO.setNom(user.getUsername());
		       }
		       userDTO.setEmail(user.getEmail());
		       
		       return userDTO;
	}
}
