package com.publi.gestionpub.service;
 
import java.util.List;

import com.publi.gestionpub.Dto.AppUserDto;
import com.publi.gestionpub.Dto.ApproleDto;
import com.publi.gestionpub.Dto.DepartementDto;
import com.publi.gestionpub.Dto.UserDTO;
import com.publi.gestionpub.entité.AppRoles;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.serviceImpl.ReviseurDto;





 
public interface UtilisateurService {

	AppUserDto addUtilisateur(AppUserDto appUserDto);
	AppUserDto save(AppUserDto userDto);
	AppUserDto updateUtilisateur(AppUserDto utilisateurDto,Long id);
	AppUserDto getPassword(String usernameOrPhoneNumber,String newPassowd);
	void deleteUtilisateur(String id);
	AppUserDto getUtilisateur(String id);
	AppUserDto getUtilisateurByEmail(String email);
	void deconnecterUtilisateur();
	List<AppUserDto> getByUserOnLigne();
	List<AppUserDto> findAll();
	//public void addRolesToUse(String username, String roleName);
	public List<AppRoles> touslesroles();
	public AppUser  findById(Long id);
	public void deleterole(Long id);
	public int nbruser();
	public AppRoles saveRoles(AppRoles role);
	public ApproleDto addRoles(ApproleDto approleDto);
	public List<ReviseurDto> getReviseurs() ;
	 public UserDTO getUserInfoByEmail(String email);

}
