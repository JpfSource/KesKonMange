package com.keskonmange.entities;

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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	@Column(name = "NOM", length = 50, nullable = false)
	private String nom;

	@Column(name = "URL_PHOTO", nullable = true)
	private String urlPhoto;
	
	@Transient
	private Integer besoinCalorique;
	
/*
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ADMINISTRATEUR", joinColumns = @JoinColumn(name = "groupe_id", referencedColumnName = "id"), 
							inverseJoinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "id"))
	private Set<Utilisateur> administrateurs;


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "GROUPE_PERSONNE", joinColumns = @JoinColumn(name = "groupe_id", referencedColumnName = "id"), 
								inverseJoinColumns = @JoinColumn(name = "personne_id", referencedColumnName = "id"))
	private Set<Personne> groupePersonnes;

	@OneToMany(mappedBy = "groupe")
	private Set<Repas> repas;
*/	
	public Groupe() {
		this(null, null, null);
	}

	public Groupe(@NotNull @NotBlank String nom, String urlPhoto) {
		this(null, nom, urlPhoto);
	}

	
	
	public Groupe(Integer id, @NotNull @NotBlank String nom, String urlPhoto) {
		super();
		this.id = id;
		this.nom = nom;
		this.urlPhoto = urlPhoto;
	}

	public Integer getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

/*
	public Set<Utilisateur> getAdministrateurs() {
		return administrateurs;
	}


	public Set<Personne> getGroupePersonnes() {
		return groupePersonnes;
	}
	
	public Set<Repas> getRepas() {
		return repas;
	}
*/

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}
/*
	public void setAdministrateurs(Set<Utilisateur> administrateurs) {
		this.administrateurs = administrateurs;
	}

	public void setGroupePersonnes(Set<Personne> groupePersonnes) {
		this.groupePersonnes = groupePersonnes;
	}
*/

	public Integer getBesoinCalorique() {
		return besoinCalorique;
	}

	public void setBesoinCalorique(Integer besoinCalorique) {
		this.besoinCalorique = besoinCalorique;
	}
/*	
	public void setRepas(Set<Repas> repas) {
		this.repas = repas;
	}
*/
}
