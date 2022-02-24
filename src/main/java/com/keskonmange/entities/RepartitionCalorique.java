package com.keskonmange.entities;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.keskonmange.enums.TypeRepas;


@Entity
@Table(name = "REPARTITION_CALORIQUE")
public class RepartitionCalorique {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Basic
	@NotNull
	@Column(name = "TYPE_REPAS", length = 20, nullable = false, unique = false)
	private String typeRepasLibelle;

	@Transient
	private TypeRepas typeRepas;

	@NotNull
	@Column(name = "POURCENTAGE", nullable = false)
	private Double pourcentage;

	public RepartitionCalorique() {
		super();
	}

	public RepartitionCalorique(Integer id, @NotNull String typeRepasLibelle, TypeRepas typeRepas, @NotNull Double pourcentage) {
		super();
		this.id = id;
		this.typeRepasLibelle = typeRepasLibelle;
		this.typeRepas = typeRepas;
		this.pourcentage = pourcentage;
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
	 * @return the pourcentage
	 */
	public Double getPourcentage() {
		return pourcentage;
	}

	/**
	 * @param pourcentage the pourcentage to set
	 */
	public void setPourcentage(Double pourcentage) {
		this.pourcentage = pourcentage;
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
