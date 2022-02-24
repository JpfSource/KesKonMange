package com.keskonmange.entities;

import java.time.LocalDate;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keskonmange.enums.TypeRepas;

@Entity
@Table(name = "REPAS")
public class Repas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Column(name = "DATE_REPAS", nullable = false, unique = false)
	private LocalDate Date_Repas;

	@Basic
	@NotNull
	@NotBlank
	@Column(name = "TypeRepas", length = 20, nullable = false, unique = false)
	private String typeRepasLibelle;
	
	@Transient
	private TypeRepas typeRepas;
	

	public Repas() {
		super();
	}

	public Repas(Integer id, @NotNull LocalDate date_Repas, @NotNull @NotBlank String typeRepasLibelle,
			TypeRepas typeRepas) {
		super();
		this.id = id;
		Date_Repas = date_Repas;
		this.typeRepasLibelle = typeRepasLibelle;
		this.typeRepas = typeRepas;
	}

	
/*	@OneToMany(mappedBy = "repas")
	private Set<Plat> plats;
	
	@JsonIgnore
	@ManyToOne
	private Groupe groupe;
*/

	

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
	 * @return the date_Repas
	 */
	public LocalDate getDate_Repas() {
		return Date_Repas;
	}

	/**
	 * @param date_Repas the date_Repas to set
	 */
	public void setDate_Repas(LocalDate date_Repas) {
		Date_Repas = date_Repas;
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

	
	/* PERSISTENT METHODS */
	@PostLoad
	void fillTransient() {
		if (!typeRepasLibelle.isEmpty()) {
			this.typeRepas = TypeRepas.of(typeRepasLibelle);
		}
	}


	@PrePersist
	void fillPersistent() {
		if (typeRepas != null) {
			this.typeRepasLibelle = this.typeRepas.getLibelle();
		}
	}

}
