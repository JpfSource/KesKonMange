package com.keskonmange.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.keskonmange.enums.TypeRepas;

@Entity
@Table(name = "ABSENCE")
public class Absence {
	
	/* FIELDS */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// Test commit JPF !

	@Basic
	@NotNull
	@NotBlank
	@Column(name = "TYPE_REPAS", length = 20, nullable = false, unique = false)
	private TypeRepas typeRepas;

	@Transient
	private String typeRepasLibelle;

	
	/* RELATIONS */
	@ManyToOne(fetch = FetchType.LAZY)
	private Personne personne;

	@ManyToOne(fetch = FetchType.LAZY)
	private Groupe groupe;

	
	/* CONSTRUCTORS */
	
	public Absence() {
		super();
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
	 * @return the typeRepasLibelle
	 */
	public String getTypeRepasLibelle() {
		return typeRepasLibelle;
	}

	/**
	 * @param typeRepasLibelle the typeRepasLibelle to set
	 */
	public void setTypeRepasLibelle(String typeRepasLibelle) {
		this.typeRepasLibelle = typeRepasLibelle;
	}

	/**
	 * @return the typeRepas
	 */
	public TypeRepas getTypeRepas() {
		return typeRepas;
	}

	/**
	 * @param typeRepas the typeRepas to set
	 */
	public void setTypeRepas(TypeRepas typeRepas) {
		this.typeRepas = typeRepas;
	}

	/**
	 * @return the personne
	 */
	public Personne getPersonne() {
		return personne;
	}

	/**
	 * @param personne the personne to set
	 */
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	/**
	 * @return the groupe
	 */
	public Groupe getGroupe() {
		return groupe;
	}

	/**
	 * @param groupe the groupe to set
	 */
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	
	/* PUBLIC METHODS */	
	
	@Override
	public String toString() {
		return "Absence [id=" + id + ", typeRepasLibelle=" + typeRepasLibelle + ", typeRepas=" + typeRepas
				+ ", personne=" + personne + ", groupe=" + groupe + "]";
	}

	/* PERSISTENT METHODS */		
	@PostLoad
	void fillTransient() {
		if (typeRepas != null) {
			this.typeRepasLibelle = this.typeRepas.getLibelle();
		}
	}

	@PrePersist
	void fillPersistent() {
		if (typeRepasLibelle != null) {
			this.typeRepas = TypeRepas.of(typeRepasLibelle);
		}
	}

}
