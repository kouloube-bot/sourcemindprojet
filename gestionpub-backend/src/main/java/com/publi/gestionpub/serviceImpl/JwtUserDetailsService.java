package com.publi.gestionpub.serviceImpl;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.mapper.Mapper;
import com.publi.gestionpub.repository.UserRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    UserRepository utilisateurRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> findByEmail = utilisateurRepository.findByEmail(username);
        if (findByEmail.isPresent()) {
            logger.info("user found {}", findByEmail.get().getPassword());
            return (UserDetails) (Mapper.toUserDetails(findByEmail.get()));
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
    
}
