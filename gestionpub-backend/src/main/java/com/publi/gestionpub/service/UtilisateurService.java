package com.publi.gestionpub.service;
 
import java.util.List;

import com.publi.gestionpub.Dto.AppUserDto;
import com.publi.gestionpub.entité.AppRoles;
import com.publi.gestionpub.entité.AppUser;





 
public interface UtilisateurService {

	AppUserDto save(AppUserDto userDto);
	AppUserDto updateUtilisateur(AppUserDto utilisateurDto,Long id);
	AppUserDto getPassword(String usernameOrPhoneNumber,String newPassowd);
	void deleteUtilisateur(String id);
	AppUserDto getUtilisateur(String id);
	AppUserDto getUtilisateurByEmail(String email);
	void  deconnecterUtilisateur();
	List<AppUserDto> getByUserOnLigne();
	List<AppUserDto> findAll();
	public void addRolesToUse(String username, String roleName);
	public List<AppRoles> touslesroles();
	public AppUser  findById(Long id);
	public void deleterole(Long id);
	public int nbruser();
	//PageDataDto<UtilisateurDto> getAllWithKeyAndPage(String key, int page, int size);

}
