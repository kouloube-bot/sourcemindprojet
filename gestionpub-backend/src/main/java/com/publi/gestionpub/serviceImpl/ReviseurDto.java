package com.publi.gestionpub.serviceImpl;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviseurDto{
	private Long id;
    private String email;
    private String username;

    // Constructeurs, getters, setters
    public ReviseurDto(Long id, String email, String username) {
        this.id = id;
        this.username = username;
        this.email = email;

}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Override
    public String toString() {
        return "ReviseurDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
    
}
