package com.keskonmange.entities;

import java.time.LocalDate;
import java.util.Date;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Column(name = "ean", length = 40, nullable = false)
	private String ean;

  	@NotNull
	@NotBlank
	@Column(name = "libelle", nullable = false)
	private String libelle;
		
	@Column(name="image_url")
	private String imgUrl;
  	
	@Column(name = "energy_kcal_100g", nullable = false)
	private Double energyKcal100g;
  	
	@Column(name = "mots_cles")
	private String tags;
	
	@Column(name = "date_maj")
	private LocalDate dateMaj;
	
	/* RELATIONS */
	@ManyToMany
	@JoinTable(name="ALIMENT_ALLERGIE",
			joinColumns = @JoinColumn(name="ID_ALIMENT", referencedColumnName="ID"),
			inverseJoinColumns = @JoinColumn(name="ID_ALLERGIE", referencedColumnName="ID")
	)
	private Set<Allergie> alimentAllergies;
	

	/* CONSTRUCTORS */
	public Aliment() {
		this(0, null, null, null, 0.0, null, null);
	}

	public Aliment(Integer id, @NotNull @NotBlank String ean, @NotNull @NotBlank String libelle,
			String imgUrl, Double energyKcal100g, String tags, LocalDate dateMaj) {
		super();
		this.id = id;
		this.ean = ean;
		this.libelle = libelle;
		//this.allergies = new HashSet<>();
		this.imgUrl = imgUrl;
		this.energyKcal100g = energyKcal100g;
		this.tags = tags;
		this.dateMaj = dateMaj;
	}

	
	/* GETTERS & SETTERS */
	
	public int getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEan() {
		return this.ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}
	
	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
/*
	public Set<Allergie> getAllergies() {
		return this.allergies;
	}

	public void setAllergies(Set<Allergie> allergies) {
		this.allergies = allergies;
	}
*/
	

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public LocalDate getDateMaj() {
		return dateMaj;
	}

	public void setDateMaj(LocalDate dateMaj) {
		this.dateMaj = dateMaj;
	}

	public double getEnergyKcal100g() {
		return this.energyKcal100g;
	}

	public void setEnergyKcal100g(Double energyKcal100g) {
		this.energyKcal100g = energyKcal100g;
	}

	/* PUBLIC METHODS */
	@Override
	public String toString() {
		/*return "Aliment [id=" + id + ", ean=" + ean + ", libelle=" + libelle + ", allergies=" + allergies + ", imgUrl="
				+ imgUrl + ", kcal_100g=" + kcal_100g + ", tags=" + tags + ", dateMaj=" + dateMaj + "]";*/
		return "Aliment [id=" + id + ", ean=" + ean + ", libelle=" + libelle + ", imgUrl="
		+ imgUrl + ", energy_kcal_100g=" + energyKcal100g + ", tags=" + tags + ", dateMaj=" + dateMaj + "]";
	}

	
}
