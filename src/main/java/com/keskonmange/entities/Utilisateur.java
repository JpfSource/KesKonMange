package com.keskonmange.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//import com.keskonmange.enumeration.Genre;

/**
 * Classe qui définie l'utilisateur ainsi que si oui ou non il est administrateur d'un groupe.
 * 
 * @author Christian Ingold, Jean-Philippe Francisco, Steeve Dombald, Antonin Guillon.
 *
 */

@Entity
@Table(name = "UTILISATEUR")
public class Utilisateur extends Personne {

	@NotNull
	@NotBlank
	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	
	@Column(name = "PWD", nullable = false)
	private String pwd;

//	@ManyToMany(mappedBy="administrateurs")
//	private Set<Groupe> groupes;

	
	public Utilisateur() {
		this(null, null, null, null, null);
	}
	
/*
	public Utilisateur(String nom, String prenom, Genre genre, Date dateNaissance, Integer taille, Integer poids, Integer objectifCalorique, String urlPhoto, String email, String pwd) {
		super(nom, prenom, genre, dateNaissance, taille, poids, objectifCalorique, urlPhoto);
		this.email = email;
		this.pwd = pwd;
	}
*/

	public Utilisateur(Integer id, @NotNull @NotBlank String nom, @NotNull @NotBlank String prenom,String email, String pwd) {
		super(id, nom, prenom);
		this.email = email;
		this.pwd = pwd;
	}


	public Utilisateur(String email, String pwd) {
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

//	public Set<Groupe> getGroupes() {
//		return groupes;
//	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
//	public void setGroupes(Set<Groupe> groupes) {
//		this.groupes = groupes;
//	}
	

}
