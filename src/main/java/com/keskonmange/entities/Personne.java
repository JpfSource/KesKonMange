package com.keskonmange.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Entity
@Table(name="PERSONNE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Personne {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@NotBlank
	@Column(name = "nom", length = 50, nullable = false, unique = false)
	private String nom;
		
	@NotNull
	@NotBlank
	@Column(name = "prenom", length = 50, nullable = false, unique = false)
	private String prenom;
	
//	@ManyToMany(mappedBy="groupePersonnes")
//	private Set<Groupe> groupes;	
	
	public Personne() {
		super();
	}
	
	public Personne(Integer id, @NotNull @NotBlank String nom, @NotNull @NotBlank String prenom) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

		
//	public Set<Groupe> getGroupes() {
//		return groupes;
//	}
//
//	public void setGroupes(Set<Groupe> groupes) {
//		this.groupes = groupes;
//	}

	@Override
	public String toString() {
		return "Personne [id=" + id + ", nom=" + nom + ", prenom=" + prenom + "]";
	}
}
