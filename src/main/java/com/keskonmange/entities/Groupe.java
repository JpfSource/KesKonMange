package com.keskonmange.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Classe qui définit un groupe composé de plusieurs personnes pouvant être des
 * administrateurs ou non. Exemple : dans une famille comoposée de 2 adultes et
 * 2 enfants, le groupe pourra être administré par un seul des deux parents ou
 * par les deux. Les enfants quand � eux seront iditifiés comme simple
 * "personnes" dans le groupe.
 * 
 * @author Christian Ingold, Jean-Philippe Francisco, Steeve Dombald, Antonin Guillon.
 *
 */

@Entity
@Table(name = "GROUPE")
public class Groupe {
	
	/* FIELDS */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	@Column(name = "NOM", length = 50, nullable = false)
	private String nom;

	@Column(name = "URL_PHOTO")
	private String urlPhoto;
	
	@Transient
	private Integer besoinCalorique;

	
	/* RELATIONS */

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="GROUPE_PERSONNE",
			joinColumns = @JoinColumn(name="ID_GROUPE", referencedColumnName="ID"),
			inverseJoinColumns = @JoinColumn(name="ID_PERSONNE", referencedColumnName="ID")
	)
	private Set<Personne> groupePersonnes = new HashSet<Personne>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="GROUPE_SCORE",
			joinColumns = @JoinColumn(name="ID_GROUPE", referencedColumnName="ID"),
			inverseJoinColumns = @JoinColumn(name="ID_SCORE", referencedColumnName="ID")
	)
	private Set<Score> groupeScores = new HashSet<Score>();

	/* CONSTRUCTORS */

	public Groupe() {
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
	 * @return the besoinCalorique
	 */
	public Integer getBesoinCalorique() {
		return besoinCalorique;
	}

	/**
	 * @param besoinCalorique the besoinCalorique to set
	 */
	public void setBesoinCalorique(Integer besoinCalorique) {
		this.besoinCalorique = besoinCalorique;
	}

	/**
	 * @return the groupePersonnes
	 */
	public Set<Personne> getGroupePersonnes() {
		return groupePersonnes;
	}

	/**
	 * @param groupePersonnes the groupePersonnes to set
	 */
	public void setGroupePersonnes(Set<Personne> groupePersonnes) {
		this.groupePersonnes = groupePersonnes;
	}

	/**
	 * @return the groupeScores
	 */
	public Set<Score> getGroupeScores() {
		return groupeScores;
	}

	/**
	 * @param groupeScores the groupeScores to set
	 */
	public void setGroupeScores(Set<Score> groupeScores) {
		this.groupeScores = groupeScores;
	}

	
	/* PUBLIC METHODS */	
	
	@Override
	public String toString() {
		return "Groupe [id=" + id + ", nom=" + nom + ", urlPhoto=" + urlPhoto + ", besoinCalorique=" + besoinCalorique
				+ "]";
	}
	
	/* PERSISTENT METHODS */
	
}
