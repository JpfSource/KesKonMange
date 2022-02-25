package com.keskonmange.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.keskonmange.enums.Role;


/**
 * Classe qui définie l'utilisateur ainsi que si oui ou non il est administrateur d'un groupe.
 * 
 * @author Christian Ingold, Jean-Philippe Francisco, Steeve Dombald, Antonin Guillon.
 *
 */

@Entity
@Table(name = "UTILISATEUR")
public class Utilisateur extends Personne {

	/* FIELDS */
	@NotNull
	@NotBlank
	@Column(name = "EMAIL", length = 150, nullable = false)
	private String email;
	
	@NotNull
	@NotBlank
	@Column(name = "PWD", length = 150, nullable = false)
	private String pwd;

	@Basic
	@Column(name = "ROLE", length = 20, nullable = true, unique = false)
	private String roleLibelle;

	@Transient
	private Role role;

	
	/* RELATIONS */

	
	/* CONSTRUCTORS */

	public Utilisateur() {
		super();
	}

	public Utilisateur(@NotNull @NotBlank String email, @NotNull @NotBlank String pwd, String roleLibelle, Role role) {
		super();
		this.email = email;
		this.pwd = pwd;
		this.roleLibelle = roleLibelle;
		this.role = role;
	}

	
	/* GETTERS & SETTERS */
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the roleLibelle
	 */
	public String getRoleLibelle() {
		return roleLibelle;
	}

	/**
	 * @param roleLibelle the roleLibelle to set
	 */
	public void setRoleLibelle(String roleLibelle) {
		this.roleLibelle = roleLibelle;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	
	/* PUBLIC METHODS */	
	
	@Override
	public String toString() {
		return "Utilisateur [email=" + email + ", pwd=" + pwd + ", roleLibelle=" + roleLibelle + ", role=" + role + "]";
	}
	
	
	/* PERSISTENT METHODS */
	@PostLoad
	void fillTransient() {
		if (!roleLibelle.isEmpty()) {
			this.role = Role.of(roleLibelle);
		}
	}

	@PrePersist
	void fillPersistent() {
		if (role != null) {
			this.roleLibelle = this.role.getLibelle();
		}
	}
	
}
