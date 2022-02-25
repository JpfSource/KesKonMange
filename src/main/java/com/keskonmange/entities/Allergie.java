package com.keskonmange.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ALLERGIE")
public class Allergie
{
	/* FIELDS */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	@Column(name = "libelle", length = 50, nullable=false)
	private String libelle;

	
	/* RELATIONS */

	
	/* CONSTRUCTORS */
	
	public Allergie() {
		super();
	}
	public Allergie(@NotNull @NotBlank String libelle) {
		super();
		this.libelle = libelle;
	}
	public Allergie(Integer id, @NotNull @NotBlank String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}

	
	/* GETTERS & SETTERS */

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	
	/* PUBLIC METHODS */	
	
	@Override
	public String toString() {
		return "Allergie [id=" + id + ", libelle=" + libelle + "]";
	}

	/* PERSISTENT METHODS */
}
