package com.keskonmange.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ALLERGIE")
public class Allergie
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	@Column(name = "libelle", length = 50, nullable=false)
	private String libelle;

	/*
	@JsonIgnore
	@ManyToMany(mappedBy = "allergies")
	private Set<Aliment> aliments;
*/
	public Allergie(Integer id, @NotNull @NotBlank String libelle)
	{
		super();
		this.id = id;
		this.libelle = libelle;
	//	this.aliments = new HashSet<>();
	}

	public Allergie() {
		super();
//		this.aliments = new HashSet<>();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
/*
	public Set<Aliment> getAliments() {
		return aliments;
	}

	public void setAliments(Set<Aliment> aliments) {
		this.aliments = aliments;
	}
*/
	@Override
	public String toString() {
		//return "Allergie [id=" + id + ", libelle=" + libelle + ", aliments=" + aliments + "]";
		return "Allergie [id=" + id + ", libelle=" + libelle + "]";
	}
}
