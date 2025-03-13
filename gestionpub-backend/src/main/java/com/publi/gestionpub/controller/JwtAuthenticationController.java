package com.publi.gestionpub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.publi.gestionpub.Dto.JwtRequest;
import com.publi.gestionpub.Dto.JwtResponse;
import com.publi.gestionpub.config.JwtTokenUtil;
import com.publi.gestionpub.serviceImpl.JwtUserDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class JwtAuthenticationController {

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        logger.info("Authenticating user");

        // Authentifier l'utilisateur
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        logger.info("User authenticated");

        // Charger les détails de l'utilisateur
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        logger.info("User loaded");

        // Générer un token JWT
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Récupérer le rôle de l'utilisateur
        String role = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("USER");  // Valeur par défaut si aucun rôle trouvé

        // Retourner le token et le rôle dans la réponse
        return ResponseEntity.ok(new JwtResponse(token, role)); // Ajout du rôle à la réponse
    }

    // Méthode pour authentifier l'utilisateur
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            logger.info("USER_DISABLED");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            logger.error("INVALID_CREDENTIALS", e);
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (Exception e) {
            logger.error("Exception", e);
            throw new Exception("Exception", e);
        }
    }
}
