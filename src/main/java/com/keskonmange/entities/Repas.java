package com.keskonmange.entities;

import java.time.LocalDate;
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
@Table(name = "REPAS")
public class Repas {
	
	/* FIELDS */
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
	private TypeRepas typeRepas;
	
	@Transient
	private String typeRepasLibelle;
	
	
	/* RELATIONS */
	@ManyToOne(fetch = FetchType.LAZY)
	private Groupe groupe;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Plat plat;
	
	
	/* CONSTRUCTORS */
	public Repas() {
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
	/**
	 * @return the plat
	 */
	public Plat getPlat() {
		return plat;
	}
	/**
	 * @param plat the plat to set
	 */
	public void setPlat(Plat plat) {
		this.plat = plat;
	}
	
	
	/* PUBLIC METHODS */	
	@Override
	public String toString() {
		return "Repas [id=" + id + ", Date_Repas=" + Date_Repas + ", typeRepasLibelle=" + typeRepasLibelle
				+ ", typeRepas=" + typeRepas + ", groupe=" + groupe + ", plat=" + plat + "]";
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
