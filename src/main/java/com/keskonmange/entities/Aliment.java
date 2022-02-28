package com.keskonmange.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ALIMENT")
public class Aliment
{
	/* FIELDS */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	@Column(name = "EAN", length = 40, nullable = false)
	private String ean;

  	@NotNull
	@NotBlank
	@Column(name = "LIBELLE", length = 200, nullable = false)
	private String libelle;
		
	@Column(name="IMAGE_URL", nullable = true)
	private String imageUrl;
  	
	@Column(name = "ENERGY_KCAL_100G")
	private Double energyKcal100g;
  	
	@Column(name = "MOTS_CLES", nullable = true)
	private String motsCles;
	
  	@NotNull
	@Column(name = "DATE_MAJ", nullable = false)
	private LocalDate dateMaj;
	
	
	/* RELATIONS */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="ALIMENT_ALLERGIE",
			joinColumns = @JoinColumn(name="ID_ALIMENT", referencedColumnName="ID"),
			inverseJoinColumns = @JoinColumn(name="ID_ALLERGIE", referencedColumnName="ID")
	)
	private Set<Allergie> alimentAllergies = new HashSet<Allergie>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="ALIMENT_SCORE",
			joinColumns = @JoinColumn(name="ID_ALIMENT", referencedColumnName="ID"),
			inverseJoinColumns = @JoinColumn(name="ID_SCORE", referencedColumnName="ID")
	)
	private Set<Score> alimentScores = new HashSet<Score>();


	/* CONSTRUCTORS */

	public Aliment() {
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
	 * @return the ean
	 */
	public String getEan() {
		return ean;
	}
	/**
	 * @param ean the ean to set
	 */
	public void setEan(String ean) {
		this.ean = ean;
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
	/**
	 * @return the imgUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the energyKcal100g
	 */
	public Double getEnergyKcal100g() {
		return energyKcal100g;
	}
	/**
	 * @param energyKcal100g the energyKcal100g to set
	 */
	public void setEnergyKcal100g(Double energyKcal100g) {
		this.energyKcal100g = energyKcal100g;
	}
	/**
	 * @return the motsCles
	 */
	public String getMotsCles() {
		return motsCles;
	}
	/**
	 * @param motsCles the motsCles to set
	 */
	public void setMotsCles(String motsCles) {
		this.motsCles = motsCles;
	}
	/**
	 * @return the dateMaj
	 */
	public LocalDate getDateMaj() {
		return dateMaj;
	}
	/**
	 * @param dateMaj the dateMaj to set
	 */
	public void setDateMaj(LocalDate dateMaj) {
		this.dateMaj = dateMaj;
	}
	/**
	 * @return the alimentAllergies
	 */
	public Set<Allergie> getAlimentAllergies() {
		return alimentAllergies;
	}
	/**
	 * @param alimentAllergies the alimentAllergies to set
	 */
	public void setAlimentAllergies(Set<Allergie> alimentAllergies) {
		this.alimentAllergies = alimentAllergies;
	}
	/**
	 * @return the alimentScores
	 */
	public Set<Score> getAlimentScores() {
		return alimentScores;
	}
	/**
	 * @param alimentScores the alimentScores to set
	 */
	public void setAlimentScores(Set<Score> alimentScores) {
		this.alimentScores = alimentScores;
	}
	

	/* PUBLIC METHODS */	
	@Override
	public String toString() {
		return "Aliment [id=" + id + ", ean=" + ean + ", libelle=" + libelle + ", imageUrl=" + imageUrl
				+ ", energyKcal100g=" + energyKcal100g + ", motsCles=" + motsCles + ", dateMaj=" + dateMaj + "]";
	}

	
	/* PERSISTENT METHODS */

	
	
}
