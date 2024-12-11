package com.publi.gestionpub.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.publi.gestionpub.Dto.AppUserDto;
import com.publi.gestionpub.entité.AppRoles;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.mapper.Mapper;
import com.publi.gestionpub.repository.AppRolesRepository;
import com.publi.gestionpub.repository.UserRepository;
import com.publi.gestionpub.service.UtilisateurService;



@Service
public class UtilisateurServiceImpl implements UtilisateurService{
	@Autowired
	 private UserRepository userRepository;
	 @Autowired
	 private AppRolesRepository roleRepository;
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	  
	@Override
	public AppUserDto save(AppUserDto userDto) {
		AppUserDto response=new AppUserDto();
		AppUser utilisateur = new AppUser();
		 utilisateur.setEmail(userDto.getEmail());
   	     utilisateur.setPassword(passwordEncoder.encode(userDto.getPassword()));
		 utilisateur.setRoles(userDto.getRoles());
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AppUserDto> getByUserOnLigne() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppUserDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void addRolesToUse(String username, String roleName) {
		// TODO Auto-generated method stub
				AppRoles role=roleRepository.findByRoleName(roleName);
				AppUser user=userRepository.findByUsername(username);
				user.getRoles().add(role);
	}

	@Override
	public List<AppRoles> touslesroles() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	@Override
	public AppUser findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleterole(Long id) {
		// TODO Auto-generated method stub
		AppRoles approle=roleRepository.getOne(id);
		roleRepository.delete(approle);;
	}

	@Override
	public int nbruser() {
		// TODO Auto-generated method stub
		return userRepository.nbruser();
	}

}
