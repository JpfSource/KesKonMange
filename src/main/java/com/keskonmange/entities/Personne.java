package com.keskonmange.entities;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.keskonmange.enums.Activite;
import com.keskonmange.enums.Genre;

@Entity
@Table(name="PERSONNE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Personne {

	/* FIELDS */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = false)
	private Integer id;

	// Données de l'écran Identité
	@NotNull
	@NotBlank
	@Column(name = "NOM", length = 50, nullable = false, unique = false)
	private String nom;

    @NotNull
	@NotBlank
	@Column(name = "PRENOM", length = 50, nullable = false, unique = false)
	private String prenom;

	@Basic
	@Column(name = "GENRE", length = 20, nullable = true, unique = false)
	private String genreLibelle;

	@Transient
	private Genre genre;

	@Column(name="DATE_NAISSANCE", nullable = true, unique = false)
	private LocalDate dateNaissance;

	@Column(name="TAILLE", nullable = true, unique = false)
	private Integer taille;

	@Column(name="POIDS", nullable = true, unique = false)
	private Integer poids;

	@Column(name="OBJECTIF_CALORIQUE", nullable = true, unique = false, columnDefinition = "integer default 100")
	private Integer objectifCalorique;

	@Column(name = "URL_PHOTO", nullable = true, unique = false)
	private String urlPhoto;
	
	@Basic
	@Column(name = "ACTIVITE", length = 20, nullable = true, unique = false)
	private String activiteLibelle;

	@Transient
	private Activite activite;
	
	@Column(name = "DESCRIPTION", nullable = true, unique = false)
	private String description;

	@Transient
	private Integer besoinsCaloriques;

	
	/* RELATIONS */
	@ManyToOne
	private Utilisateur utilisateurCreateur;
	
	@ManyToMany
	@JoinTable(name="PERSONNE_ALLERGIE",
			joinColumns = @JoinColumn(name="ID_PERSONNE", referencedColumnName="ID"),
			inverseJoinColumns = @JoinColumn(name="ID_ALLERGIE", referencedColumnName="ID")
	)
	private Set<Allergie> personneAllergies;
	

	/* CONSTRUCTORS */
	public Personne() {}

	public Personne(Integer id, @NotNull @NotBlank String nom, @NotNull @NotBlank String prenom, String genreLibelle,
			Genre genre, LocalDate dateNaissance, Integer taille, Integer poids, Integer objectifCalorique,
			String urlPhoto, String activiteLibelle, Activite activite, String description, Integer besoinsCaloriques,
			Utilisateur utilisateurCreateur, Set<Allergie> personneAllergies) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.genreLibelle = genreLibelle;
		this.genre = genre;
		this.dateNaissance = dateNaissance;
		this.taille = taille;
		this.poids = poids;
		this.objectifCalorique = objectifCalorique;
		this.urlPhoto = urlPhoto;
		this.activiteLibelle = activiteLibelle;
		this.activite = activite;
		this.description = description;
		this.besoinsCaloriques = besoinsCaloriques;
		this.utilisateurCreateur = utilisateurCreateur;
		this.personneAllergies = personneAllergies;
	}


	public Personne(@NotNull @NotBlank String nom, @NotNull @NotBlank String prenom, String genreLibelle, Genre genre,
			LocalDate dateNaissance, Integer taille, Integer poids, Integer objectifCalorique, String urlPhoto,
			String activiteLibelle, Activite activite, String description, Integer besoinsCaloriques,
			Utilisateur utilisateurCreateur, Set<Allergie> personneAllergies) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.genreLibelle = genreLibelle;
		this.genre = genre;
		this.dateNaissance = dateNaissance;
		this.taille = taille;
		this.poids = poids;
		this.objectifCalorique = objectifCalorique;
		this.urlPhoto = urlPhoto;
		this.activiteLibelle = activiteLibelle;
		this.activite = activite;
		this.description = description;
		this.besoinsCaloriques = besoinsCaloriques;
		this.utilisateurCreateur = utilisateurCreateur;
		this.personneAllergies = personneAllergies;
	}

	public Personne(@NotNull @NotBlank String nom, @NotNull @NotBlank String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
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
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dateNaissance
	 */
	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	/**
	 * @param dateNaissance the dateNaissance to set
	 */
	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	/**
	 * @return the urlPhoto
	 */
	public String getUrlPhoto() {
		return urlPhoto;
	}

	/**
	 * @param urlPhoto the urlPhoto to set
	 */
	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}

	/**
	 * @return the genreLibelle
	 */
	public String getGenreLibelle() {
		return genreLibelle;
	}

	/**
	 * @param genreLibelle the genreLibelle to set
	 */
	public void setGenreLibelle(String genreLibelle) {
		this.genreLibelle = genreLibelle;
	}

	/**
	 * @return the genre
	 */
	public Genre getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	/**
	 * @return the taille
	 */
	public Integer getTaille() {
		return taille;
	}

	/**
	 * @param taille the taille to set
	 */
	public void setTaille(Integer taille) {
		this.taille = taille;
	}

	/**
	 * @return the poids
	 */
	public Integer getPoids() {
		return poids;
	}

	/**
	 * @param poids the poids to set
	 */
	public void setPoids(Integer poids) {
		this.poids = poids;
	}

	/**
	 * @return the objectifCalorique
	 */
	public Integer getObjectifCalorique() {
		return objectifCalorique;
	}

	/**
	 * @param objectifCalorique the objectifCalorique to set
	 */
	public void setObjectifCalorique(Integer objectifCalorique) {
		this.objectifCalorique = objectifCalorique;
	}

	/**
	 * @return the activiteLibelle
	 */
	public String getActiviteLibelle() {
		return activiteLibelle;
	}

	/**
	 * @param activiteLibelle the activiteLibelle to set
	 */
	public void setActiviteLibelle(String activiteLibelle) {
		this.activiteLibelle = activiteLibelle;
	}

	/**
	 * @return the activite
	 */
	public Activite getActivite() {
		return activite;
	}

	/**
	 * @param activite the activite to set
	 */
	public void setActivite(Activite activite) {
		this.activite = activite;
	}

	/**
	 * @return the besoinsCaloriques
	 */
	public Integer getBesoinsCaloriques() {
		return besoinsCaloriques;
	}

	/**
	 * @param besoinsCaloriques the besoinsCaloriques to set
	 */
	public void setBesoinsCaloriques(Integer besoinsCaloriques) {
		this.besoinsCaloriques = besoinsCaloriques;
	}

	@Override
	public String toString()
	{
		return "Personne [id=" + id + ", nom=" + nom + ", prenom=" + prenom + "]";
	}

	/**
	 * @return the utilisateurCreateur
	 */
	public Utilisateur getUtilisateurCreateur() {
		return utilisateurCreateur;
	}


	/**
	 * @param utilisateurCreateur the utilisateurCreateur to set
	 */
	public void setUtilisateurCreateur(Utilisateur utilisateurCreateur) {
		this.utilisateurCreateur = utilisateurCreateur;
	}


	/**
	 * @return the personneAllergies
	 */
	public Set<Allergie> getPersonneAllergies() {
		return personneAllergies;
	}


	/**
	 * @param personneAllergies the personneAllergies to set
	 */
	public void setPersonneAllergies(Set<Allergie> personneAllergies) {
		this.personneAllergies = personneAllergies;
	}
	
	
	
	
	
	

	/* PUBLIC METHODS */
	
	/* PERSISTENT METHODS */
	
	@PostLoad
	void fillTransient() {
		if (!genreLibelle.isEmpty()) {
			this.genre = Genre.of(genreLibelle);
		}
		if (!activiteLibelle.isEmpty()) {
			this.activite = Activite.of(activiteLibelle);
		}
	}

	@PrePersist
	void fillPersistent() {
		if (genre != null) {
			this.genreLibelle = this.genre.getLibelle();
		}
		if (activite != null) {
			this.activiteLibelle = this.activite.getLibelle();
		}

	}
}
