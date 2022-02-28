package com.keskonmange.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.keskonmange.enums.TypeRepas;


@Entity
@Table(name = "REPARTITION_CALORIQUE")
public class RepartitionCalorique {

	/* FIELDS */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Basic
	@NotNull
	@Column(name = "TYPE_REPAS", length = 20, nullable = false, unique = false)
	private TypeRepas typeRepas;

	@Transient
	private String typeRepasLibelle;

	@NotNull
	@Column(name = "POURCENTAGE", nullable = false)
	private Double pourcentage;

	
	/* RELATIONS */
	@ManyToOne(fetch = FetchType.LAZY)
	private Groupe groupe;
	
	
	/* CONSTRUCTORS */
	
	public RepartitionCalorique() {
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
		return "RepartitionCalorique [id=" + id + ", typeRepasLibelle=" + typeRepasLibelle + ", typeRepas=" + typeRepas
				+ ", pourcentage=" + pourcentage + ", groupe=" + groupe + "]";
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
