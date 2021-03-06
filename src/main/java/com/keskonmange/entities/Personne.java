package com.keskonmange.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.keskonmange.enums.Activite;
import com.keskonmange.enums.Genre;
import com.keskonmange.enums.Role;

@Entity
@Table(name="PERSONNE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Personne {

	/* FIELDS */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = false)
	private Integer id;

	@Nullable
	@Column(name="DATE_SYS_CREATION", nullable = true, unique = false)
	private LocalDateTime dateSysCreation;
	
	@Nullable
	@Column(name="DATE_SYS_MODIFICATION", nullable = true, unique = false)
	private LocalDateTime dateSysModification;

	@NotNull
	@NotBlank
	@Column(name = "NOM", length = 50, nullable = false, unique = false)
	private String nom;

    @NotNull
	@NotBlank
	@Column(name = "PRENOM", length = 50, nullable = false, unique = false)
	private String prenom;

	@Transient
	private String genreLibelle;

	@Basic
	@Nullable
	@Column(name = "GENRE", length = 20, nullable = true, unique = false)
	private Genre genre;

	@Nullable
	@Past
	@Column(name="DATE_NAISSANCE", nullable = true, unique = false)
	private LocalDate dateNaissance;

	@Column(name="TAILLE", nullable = true, unique = false)
	private Integer taille;

	@Nullable
	@Column(name="POIDS", nullable = true, unique = false)
	private Integer poids;

	@Nullable
	@Column(name="OBJECTIF_CALORIQUE", nullable = true, unique = false)
	private Integer objectifCalorique;

	@Column(name = "URL_PHOTO", nullable = true, unique = false)
	private String urlPhoto;
	
	@Transient
	private String activiteLibelle;

	@Basic
	@Nullable
	@Column(name = "ACTIVITE", length = 20, nullable = true, unique = false)
	private Activite activite;
	
	@Column(name = "DESCRIPTION", nullable = true, unique = false)
	private String description;

	@Transient
	private Integer besoinsCaloriques;

	@Column(name = "EMAIL", length = 150, nullable = true)
	private String email;
	
	@Column(name = "PWD", length = 150, nullable = true)
	private String pwd;

	@Basic
	@Column(name = "ROLE", length = 20, nullable = true, unique = false)
	private Role role;

	@Transient
	private String roleLibelle;
	
	
    /* RELATIONS */
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_createur")
    private Personne createur;
     
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "createur", cascade = CascadeType.ALL)
	private Set<Personne> personnesCreees = new HashSet<Personne>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="PERSONNE_ALLERGIE",
			joinColumns = @JoinColumn(name="ID_PERSONNE", referencedColumnName="ID"),
			inverseJoinColumns = @JoinColumn(name="ID_ALLERGIE", referencedColumnName="ID")
	)
	private Set<Allergie> personneAllergies = new HashSet<Allergie>();

	
	/* CONSTRUCTORS */

	public Personne() {
		super();
	}
	
	public Personne(Integer id, String nom, String prenom, Genre genre, LocalDate dateNaissance, Integer taille, Integer poids, Integer objectifCalorique, String urlPhoto, Activite activite, String description, String email, String pwd, Role role) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.genre = genre;
		this.dateNaissance = dateNaissance;
		this.taille = taille;
		this.poids = poids;
		this.objectifCalorique = objectifCalorique;
		this.urlPhoto = urlPhoto;
		this.activite = activite;
		this.description = description;
		this.email = email;
		this.pwd = pwd;
		this.role = role;
	}
	
	public Personne(Genre genre, Integer poids, Integer taille, LocalDate dateNaissance, Activite activite, Integer objectifCalorique) {
		super();
		this.genre = genre;
		this.dateNaissance = dateNaissance;
		this.taille = taille;
		this.poids = poids;
		this.objectifCalorique = objectifCalorique;
		this.activite = activite;
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
	

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the roleLibelle
	 */
	public String getRoleLibelle() {
		return roleLibelle;
	}

	/**
	 * @param roleLibelle the roleLibelle to set
	 */
	public void setRoleLibelle(String roleLibelle) {
		this.roleLibelle = roleLibelle;
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
	 * @return the personnesCreees
	 */
	public Set<Personne> getPersonnesCreees() {
		return personnesCreees;
	}

	/**
	 * @param personnesCreees the personnesCreees to set
	 */
	public void setPersonnesCreees(Set<Personne> personnesCreees) {
		this.personnesCreees = personnesCreees;
	}	
	
	/**
	 * @return the dateSysCreation
	 */
	public LocalDateTime getDateSysCreation() {
		return dateSysCreation;
	}

	/**
	 * @param dateSysCreation the dateSysCreation to set
	 */
	public void setDateSysCreation(LocalDateTime dateSysCreation) throws Exception {
		throw new Exception("Method not implemented : it's forbidden to set a system date");
	}

	/**
	 * @return the dateSysModification
	 */
	public LocalDateTime getDateSysModification() {
		return dateSysModification;
	}

	/**
	 * @param dateSysModification the dateSysModification to set
	 */
	public void setDateSysModification(LocalDateTime dateSysModification) throws Exception {
		throw new Exception("Method not implemented : it's forbidden to set a system date");
	}

	
	/* PUBLIC METHODS */	
	@Override
	public String toString() {
		return "Personne [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", genreLibelle=" + genreLibelle
				+ ", genre=" + genre + ", dateNaissance=" + dateNaissance + ", taille=" + taille + ", poids=" + poids
				+ ", objectifCalorique=" + objectifCalorique + ", urlPhoto=" + urlPhoto + ", activiteLibelle="
				+ activiteLibelle + ", activite=" + activite + ", description=" + description + ", besoinsCaloriques="
				+ besoinsCaloriques + "]";
	}

	
	/* PERSISTENT METHODS */
	
	@PostLoad
	void fillTransient() {
		if (genre != null) {
			this.genreLibelle = this.genre.getLibelle();
		}
		if (activite != null) {
			this.activiteLibelle = this.activite.getLibelle();
		}
		if (role != null) {
			this.roleLibelle = this.role.getLibelle();
		}
	}

	@PrePersist
	void fillPersistent() {
		if (genreLibelle != null) {
			this.genre = Genre.of(genreLibelle);
		}
		if (activiteLibelle != null) {
			this.activite = Activite.of(activiteLibelle);
		}
		if (roleLibelle != null) {
			this.role = Role.of(roleLibelle);
		}
	}
}
