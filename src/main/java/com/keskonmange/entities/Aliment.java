package com.keskonmange.entities;

import java.util.HashSet;
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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@NotBlank
	@Column(name = "ean", nullable = false, unique = true)
	private long ean;
	@NotNull
	@NotBlank
	@Column(name = "libelle", length = 30, nullable = false)
	private String libelle;
	@ManyToMany
	@JoinTable(name = "ALLERGIE_ALIMENT", joinColumns = @JoinColumn(name = "ID_ALIMENT", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_ALLERGIE", referencedColumnName = "ID"))
	private Set<Allergie> allergies;
	@ManyToMany(mappedBy = "aliment_plat")
	private Set<Plat> plats;
	@Column(name = "nutriscore")
	private int nutriscore;
	@Column(name = "ecoscore")
	private int ecoscore;
	@Column(name = "novagroupe")
	private int novagroupe;
	@Column(name = "kcal_100g")
	private int kcal_100g;

	public Aliment(int id,@NotNull @NotBlank
	long ean,@NotNull @NotBlank
	String libelle,int nutriscore,int ecoscore,int novagroupe,int kcal_100g)
	{
		super();
		this.id = id;
		this.ean = ean;
		this.libelle = libelle;
		this.allergies = new HashSet<>();
		this.nutriscore = nutriscore;
		this.ecoscore = ecoscore;
		this.novagroupe = novagroupe;
		this.kcal_100g = kcal_100g;
	}

	public Aliment()
	{
		super();
		this.allergies = new HashSet<>();
	}

	public int getId()
	{
		return this.id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public long getEan()
	{
		return this.ean;
	}

	public void setEan(long ean)
	{
		this.ean = ean;
	}

	public String getLibelle()
	{
		return this.libelle;
	}

	public void setLibelle(String libelle)
	{
		this.libelle = libelle;
	}

	public Set<Allergie> getAllergies()
	{
		return this.allergies;
	}

	public void setAllergies(Set<Allergie> allergies)
	{
		this.allergies = allergies;
	}

	public int getNutriscore()
	{
		return this.nutriscore;
	}

	public void setNutriscore(int nutriscore)
	{
		this.nutriscore = nutriscore;
	}

	public int getEcoscore()
	{
		return this.ecoscore;
	}

	public void setEcoscore(int ecoscore)
	{
		this.ecoscore = ecoscore;
	}

	public int getNovagroupe()
	{
		return this.novagroupe;
	}

	public void setNovagroupe(int novagroupe)
	{
		this.novagroupe = novagroupe;
	}

	public int getKcal_100g()
	{
		return this.kcal_100g;
	}

	public void setKcal_100g(int kcal_100g)
	{
		this.kcal_100g = kcal_100g;
	}

	@Override
	public String toString()
	{
		return "Aliment [id=" + id + ", ean=" + ean + ", libelle=" + libelle + ", allergies=" + allergies + ", plats="
				+ plats + ", nutriscore=" + nutriscore + ", ecoscore=" + ecoscore + ", novagroupe=" + novagroupe
				+ ", kcal_100g=" + kcal_100g + "]";
	}
}
