package com.publi.gestionpub.mapper;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.publi.gestionpub.Dto.AppUserDto;
import com.publi.gestionpub.Dto.ApproleDto;
import com.publi.gestionpub.Dto.DepartementDto;
import com.publi.gestionpub.Dto.EnseignantChercheurDto;
import com.publi.gestionpub.entité.AppRoles;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.entité.Departement;
import com.publi.gestionpub.entité.EnseignantChercheur;




public class Mapper {
	
	
	public static AppUserDto toUtilisateurDto(AppUser user) {
		AppUserDto utilisateurDto = new AppUserDto();
		utilisateurDto.setId(user.getId());
		utilisateurDto.setEmail(user.getEmail());
		utilisateurDto.setRoles(user.getRoles());
		
		
	
		return utilisateurDto;
	}
	
//	public static AppUserDto toUserDetails (AppUser user) {
//		AppUserDto dto = new AppUserDto();
//		 dto.setId(user.getId());
//		dto.setEmail(user.getEmail());
//		dto.setPassword(user.getPassword());
//		return dto;
//		
//	
//		
//	}
	
	
	
	public static UserDetails toUserDetails(AppUser user) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for (Iterator iterator = user.getRoles().iterator(); iterator.hasNext();) {
			AppRoles enumRole = (AppRoles) iterator.next();
			authorities.add(new SimpleGrantedAuthority(enumRole.getRoleName()));
		}
		UserDetails userdetails = new User(user.getEmail(), user.getPassword(), authorities);
		return userdetails;
	}

	
	public static EnseignantChercheurDto toEnseignantChercheurDto(EnseignantChercheur chercheur) {
		EnseignantChercheurDto dto = new EnseignantChercheurDto();
		dto.setEmail(chercheur.getEmail());
		dto.setPassword(chercheur.getPassword());
		dto.setNom(chercheur.getNom());
		dto.setPrenom(chercheur.getPrenom());
		dto.setDepartement(chercheur.getDepartement().getLibelle());
		dto.setIdDepartement(chercheur.getDepartement().getId());
		return dto;
	}
	public static DepartementDto toDepartementDto(Departement departement) {
		DepartementDto dto=new DepartementDto();
		dto.setDescription(departement.getDescription());
		dto.setLibelle(departement.getLibelle());
		dto.setId(departement.getId());
		return dto;
	}
	public static ApproleDto toRolesDto(AppRoles role) {
		ApproleDto dto=new ApproleDto();
		dto.setRoleName(role.getRoleName());
		return dto;
	}
	
	

}