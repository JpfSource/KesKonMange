package com.keskonmange.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ALIMENT")
public class Aliment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@NotBlank
	@Column(name = "ean", nullable = false, unique = true)
	private long ean;
	
	@NotNull
	@NotBlank
	@Column(name = "libelle", length = 30, nullable = false)
	private String libelle;
		
	@Column(name = "allergie")
	private List<String> allergie;
	
	@Column(name = "nutriscore")
	private int nutriscore;
	
	@Column(name = "ecoscore")
	private int ecoscore;
	
	@Column(name = "novagroupe")
	private int novagroupe;
	
	@Column(name = "kcal_100g")
	private int kcal_100g;

	public Aliment(int id, @NotNull @NotBlank long ean, @NotNull @NotBlank String libelle, List<String> allergie,
			int nutriscore, int ecoscore, int novagroupe, int kcal_100g) {
		super();
		this.id = id;
		this.ean = ean;
		this.libelle = libelle;
		this.allergie = allergie;
		this.nutriscore = nutriscore;
		this.ecoscore = ecoscore;
		this.novagroupe = novagroupe;
		this.kcal_100g = kcal_100g;
	}
	
	public Aliment() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getEan() {
		return this.ean;
	}

	public void setEan(long ean) {
		this.ean = ean;
	}
	
	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<String> getAllergie() {
		return this.allergie;
	}

	public void setAllergie(List<String> allergie) {
		this.allergie = allergie;
	}

	public int getNutriscore() {
		return this.nutriscore;
	}

	public void setNutriscore(int nutriscore) {
		this.nutriscore = nutriscore;
	}

	public int getEcoscore() {
		return this.ecoscore;
	}

	public void setEcoscore(int ecoscore) {
		this.ecoscore = ecoscore;
	}

	public int getNovagroupe() {
		return this.novagroupe;
	}

	public void setNovagroupe(int novagroupe) {
		this.novagroupe = novagroupe;
	}

	public int getKcal_100g() {
		return this.kcal_100g;
	}

	public void setKcal_100g(int kcal_100g) {
		this.kcal_100g = kcal_100g;
	}
	
	@Override
	public String toString() {
		return "Ingredient [id=" + this.id + ", ean=" + this.ean + ", libelle=" + this.libelle + ", allergie=" + this.allergie + ", nutriscore=" + this.nutriscore + 
				", ecoscore=" + this.ecoscore + ", novagroupe=" + this.novagroupe + ", kcal_100g=" + this.kcal_100g + "]";
	}
}
