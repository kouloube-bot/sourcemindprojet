package com.publi.gestionpub.controller;

import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.query.JpaCountQueryCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.publi.gestionpub.entité.AppRoles;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.service.AccountService;
import com.publi.gestionpub.service.AccountServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
public class AccountRestController {
	
	@Autowired
	 private PasswordEncoder passwordEncoder;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private AccountService accountService;
	
	


	@RequestMapping(value = "/login/{username}", method = RequestMethod.GET)
	public AppUser getUsername(@PathVariable("username") String username ) {
		return accountService.findUserByUsername(username);
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public AppUser regitre(@RequestBody AppUser userForm) {
		//AppRoles approl=new AppRoles();
		AppUser user=accountService.findUserByUsername(userForm.getUsername());
		if(user!=null) 
			throw new RuntimeException("ce nom existe");
		AppUser appUser=new AppUser();
		appUser.setUsername(userForm.getUsername());
		appUser.setPassword(userForm.getPassword());
		appUser.setEmail(userForm.getEmail());
		appUser.setRoles(userForm.getRoles());
		System.out.println(userForm.getRoles());
		accountService.saveUser(appUser);
		// accountService.addRolesToUse(userForm.getUsername(), "USER");
		 
		 return appUser;
	}
	@RequestMapping(value="/role/add" ,method=RequestMethod.POST)
	public AppRoles roles(@RequestBody AppRoles approle) {
		return accountService.saveRoles(approle);
		
	}
	
	@RequestMapping(value="/roles" ,method=RequestMethod.GET)	
	public List<AppRoles> listerroles(){
		return accountService.touslesroles();
	}
	/*@RequestMapping(value="/role/re" ,method=RequestMethod.POST)
	public void affecterrole(@RequestBody @PathVariable("username") String username,@PathVariable("roleName") String roleName) {
		AppUser appuser=new AppUser();
		
		accountService.addRolesToUse(username, roleName);
	}*/
@RequestMapping(value="/registrer/{id}" ,method=RequestMethod.PUT)
public void modifieruser(@PathVariable("id") Long id ,@RequestBody AppUser user) {
	AppUser users=accountService.findById(id);
	String hashpw=passwordEncoder.encode(user.getPassword());
	users.setPassword(hashpw);
	users.setUsername(user.getUsername());
	users.setEmail(user.getEmail());
	users.setRoles(user.getRoles());
	accountService.updateUser(id,users);
}
@RequestMapping(value="/all" ,method=RequestMethod.GET)
public List<AppUser> listuser(){
	return accountService.Touslesuser();
}
@RequestMapping(value="/userdelete/{id}" ,method=RequestMethod.DELETE)
public void supprimeruser(@PathVariable("id") Long id) {
	accountService.deleteuser(id);
}
@RequestMapping(value="/role/delete/{id}" ,method=RequestMethod.DELETE)
public void supprimerrole(@PathVariable("id") Long id) {
	accountService.deleterole(id);
}
@RequestMapping(value = "/register/{id}", method = RequestMethod.GET)   
public ResponseEntity<AppUser> getUserId(@PathVariable("id") Long id) {
	AppUser appuser=accountService.findById(id);
	return new ResponseEntity<AppUser>(appuser, HttpStatus.OK);
}
@RequestMapping(value = "/register/nbruser", method = RequestMethod.GET)   
public int nbrusers() {
	
	return accountService.nbruser();
}
}
