package com.publi.gestionpub.mapper;



import com.publi.gestionpub.Dto.AppUserDto;
import com.publi.gestionpub.entité.AppUser;



public class Mapper {
	
	
	public static AppUserDto toUtilisateurDto(AppUser user) {
		AppUserDto utilisateurDto = new AppUserDto();
		utilisateurDto.setId(user.getId());
		utilisateurDto.setEmail(user.getEmail());
		utilisateurDto.setRoles(user.getRoles());
		
		
	
		return utilisateurDto;
	}
	
	public static AppUserDto toUserDetails (AppUser user) {
		AppUserDto dto = new AppUserDto();
		 dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		return dto;
		
	
		
	}

}