package com.keskonmange.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.keskonmange.enums.TypePlat;

@Entity
@Table(name = "PLAT")
public class Plat
{
	/* FIELDS */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@NotBlank
	@Column(name = "LIBELLE", length = 150, nullable = false, unique = false)
	private String libelle;
	
	@Basic
	@NotNull
	@NotBlank
	@Column(name = "TYPE_PLAT", length = 20, nullable = false, unique = false)
	private TypePlat typePlat;
	
	@Transient
	private String typePlatLibelle;

	
	/* RELATIONS */
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Personne createur;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="PLAT_ALIMENT",
			joinColumns = @JoinColumn(name="ID_PLAT", referencedColumnName="ID"),
			inverseJoinColumns = @JoinColumn(name="ID_ALIMENT", referencedColumnName="ID")
	)
	private Set<Aliment> platAliments = new HashSet<Aliment>();
	
	
	/* CONSTRUCTORS */

	public Plat() {
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
	 * @return the libellePlat
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libellePlat the libellePlat to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
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

	/**
	 * @return the createur
	 */
	public Personne getCreateur() {
		return createur;
	}

	/**
	 * @param createur the createur to set
	 */
	public void setCreateur(Personne createur) {
		this.createur = createur;
	}

	/**
	 * @return the platAliments
	 */
	public Set<Aliment> getPlatAliments() {
		return platAliments;
	}

	/**
	 * @param platAliments the platAliments to set
	 */
	public void setPlatAliments(Set<Aliment> platAliments) {
		this.platAliments = platAliments;
	}

	
	/* PUBLIC METHODS */	
	
	@Override
	public String toString() {
		return "Plat [id=" + id + ", libelle=" + libelle + ", typePlatLibelle=" + typePlatLibelle
				+ ", typePlat=" + typePlat + ", createur=" + createur + "]";
	}

	/* PERSISTENT METHODS */

	@PostLoad
	void fillTransient() {
		if (typePlat != null) {
			this.typePlatLibelle = this.typePlat.getLibelle();
		}
	}

	@PrePersist
	void fillPersistent() {
		if (typePlatLibelle != null) {
			this.typePlat = TypePlat.of(typePlatLibelle);
		}
	}

	
}
