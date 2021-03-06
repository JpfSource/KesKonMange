package com.keskonmange.security.payload;

/**
 * Classe qui permet de récupérer les données de connexion venant du client.
 * 
 * @author Christian Ingold
 *
 */
public class LoginRequest {

	private String email;

	private String pwd;

	public LoginRequest() {
		super();
	}
	
	
	public LoginRequest(String email, String pwd) {
		super();
		this.email = email;
		this.pwd = pwd;
	}


	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}




}
