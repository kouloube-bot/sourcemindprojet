package com.publi.gestionpub.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publi.gestionpub.entité.AppRoles;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.repository.AppRolesRepository;
import com.publi.gestionpub.repository.UserRepository;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Service
public class AccountServiceImpl implements AccountService {
	
	
 @Autowired
 private PasswordEncoder passwordEncoder;
 @Autowired
 private UserRepository userRepository;
 @Autowired
 private AppRolesRepository roleRepository;
  
	@Override
	public AppUser saveUser(AppUser user) {
		String hashpw=passwordEncoder.encode(user.getPassword());
		user.setPassword(hashpw);
		return userRepository.saveAndFlush(user);
		
	}

	@Override
	public AppRoles saveRoles(AppRoles role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	@Override
	public void addRolesToUse(String username, String roleName) {
		// TODO Auto-generated method stub
		AppRoles role=roleRepository.findByRoleName(roleName);
		AppUser user=userRepository.findByUsername(username);
		user.getRoles().add(role);
		
	}

	@Override
	public AppUser findUserByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}

	@Override
	public void updateUser(Long id ,AppUser user) {
		AppUser users=findById(id);
		String hashpw=passwordEncoder.encode(user.getPassword());
		users.setPassword(hashpw);
		users.setUsername(user.getUsername());
		users.setEmail(user.getEmail());
		users.setRoles(user.getRoles());
		
		//List<AppRoles> listesRoles=roleRepository.findAll();
		//users.setRoles(listesRoles);
		 userRepository.saveAndFlush(users);
	}

	@Override
	public List<AppUser> Touslesuser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public void deleteuser(Long id) {
		// TODO Auto-generated method stub
		AppUser user=findById(id);
		userRepository.delete(user);
	}

	@Override
	public List<AppRoles> touslesroles() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	@Override
	public void deleterole(Long id) {
		// TODO Auto-generated method stub
		AppRoles approle=roleRepository.getOne(id);
		roleRepository.delete(approle);;
		
	}

	@Override
	public AppUser findById(Long id) {
		if(userRepository.findById(id).isPresent()) {
			return userRepository.findById(id).get();
		}
		return null;
	}

	@Override
	public int nbruser() {
		// TODO Auto-generated method stub
		return userRepository.nbruser();
	}

	
}
