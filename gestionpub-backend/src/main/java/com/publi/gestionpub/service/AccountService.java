package com.publi.gestionpub.service;

import java.util.List;

import com.publi.gestionpub.entité.AppRoles;
import com.publi.gestionpub.entité.AppUser;


public interface AccountService {
	public AppUser saveUser(AppUser user);
	public AppRoles saveRoles(AppRoles role);
	public void addRolesToUse(String username, String roleName);
	public  AppUser findUserByUsername(String username);
	public void updateUser(Long id ,AppUser user);
	public List<AppUser> Touslesuser();
	public List<AppRoles> touslesroles();
	public AppUser  findById(Long id);
	public void deleteuser(Long id);
	public void deleterole(Long id);
	public int nbruser();
	
	

}
