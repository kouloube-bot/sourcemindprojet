package com.publi.gestionpub.Dto;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    
    private final String jwttoken;  // Le token JWT
    private final String role;      // Le rôle de l'utilisateur

    // Constructeur
    public JwtResponse(String jwttoken, String role) {
        this.jwttoken = jwttoken;
        this.role = role;
    }

    // Getter pour le token
    public String getToken() {
        return this.jwttoken;
    }

    // Getter pour le rôle
    public String getRole() {
        return this.role;
    }
}
