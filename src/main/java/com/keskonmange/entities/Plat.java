package com.keskonmange.entities;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keskonmange.enums.TypePlat;

@Entity
@Table(name = "PLAT")
public class Plat
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@NotBlank
	@Column(name = "LIBELLE", length = 150, nullable = false, unique = false)
	private String libellePlat;
	
/*	
	@ManyToMany
	@JoinTable(name = "ALIMENT_PLAT", joinColumns = @JoinColumn(name = "ID_PLAT", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_ALIMENT", referencedColumnName = "ID"))
	private Set<Aliment> aliment_plat;
*/	
	@Basic
	@NotNull
	@NotBlank
	@Column(name = "TYPE_PLAT", length = 20, nullable = false, unique = false)
	private String typePlatLibelle;
	
	@Transient
	private TypePlat typePlat;
/*
	@JsonIgnore
	@ManyToOne
	private Repas repas;
*/

	public Plat() {
		super();
	}
	
	public Plat(Integer id, @NotNull @NotBlank String libellePlat, @NotNull @NotBlank String typePlatLibelle,
			TypePlat typePlat) {
		super();
		this.id = id;
		this.libellePlat = libellePlat;
		this.typePlatLibelle = typePlatLibelle;
		this.typePlat = typePlat;
	}
	

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
	 * @return the libellePlat
	 */
	public String getLibellePlat() {
		return libellePlat;
	}

	/**
	 * @param libellePlat the libellePlat to set
	 */
	public void setLibellePlat(String libellePlat) {
		this.libellePlat = libellePlat;
	}

	/**
	 * @return the typePlatLibelle
	 */
	public String getTypePlatLibelle() {
		return typePlatLibelle;
	}

	/**
	 * @param typePlatLibelle the typePlatLibelle to set
	 */
	public void setTypePlatLibelle(String typePlatLibelle) {
		this.typePlatLibelle = typePlatLibelle;
	}

	/**
	 * @return the typePlat
	 */
	public TypePlat getTypePlat() {
		return typePlat;
	}

	/**
	 * @param typePlat the typePlat to set
	 */
	public void setTypePlat(TypePlat typePlat) {
		this.typePlat = typePlat;
	}

	
	
	/* PERSISTENT METHODS */
	@PostLoad
	void fillTransient() {
		if (!typePlatLibelle.isEmpty()) {
			this.typePlat = TypePlat.of(typePlatLibelle);
		}
	}


	@PrePersist
	void fillPersistent() {
		if (typePlat != null) {
			this.typePlatLibelle = this.typePlat.getLibelle();
		}
	}
}
