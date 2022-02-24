package com.keskonmange.entities;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.keskonmange.enums.Activite;
import com.keskonmange.enums.Genre;
import com.keskonmange.enums.TypeScore;

@Entity
@Table(name = "SCORE")
public class Score {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Basic
	@NotNull
	@NotBlank
	@Column(name = "TYPE_SCORE", length = 20, nullable = false, unique = false)
	private String typeScoreLibelle;

	@Transient
	private TypeScore typeScore;

	@NotNull
	@Column(name = "CODE", length = 5, nullable = false)
	private String code;

	@NotNull
	@Column(name = "LIBELLE", length = 80, nullable = false)
	private String libelle;

	@Column(name = "URL_PHOTO", nullable = true)
	private String urlPhoto;

	
	public Score() {
		super();
	}

	public Score(Integer id, @NotNull @NotBlank String typeScoreLibelle, TypeScore typeScore, @NotNull String code,
			@NotNull String libelle, String urlPhoto) {
		super();
		this.id = id;
		this.typeScoreLibelle = typeScoreLibelle;
		this.typeScore = typeScore;
		this.code = code;
		this.libelle = libelle;
		this.urlPhoto = urlPhoto;
	}

	
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
	 * @return the typeScoreLibelle
	 */
	public String getTypeScoreLibelle() {
		return typeScoreLibelle;
	}

	/**
	 * @param typeScoreLibelle the typeScoreLibelle to set
	 */
	public void setTypeScoreLibelle(String typeScoreLibelle) {
		this.typeScoreLibelle = typeScoreLibelle;
	}

	/**
	 * @return the typeScore
	 */
	public TypeScore getTypeScore() {
		return typeScore;
	}

	/**
	 * @param typeScore the typeScore to set
	 */
	public void setTypeScore(TypeScore typeScore) {
		this.typeScore = typeScore;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

	
	/* PERSISTENT METHODS */
	@PostLoad
	void fillTransient() {
		if (!typeScoreLibelle.isEmpty()) {
			this.typeScore = TypeScore.of(typeScoreLibelle);
		}
	}

	@PrePersist
	void fillPersistent() {
		if (typeScore != null) {
			this.typeScoreLibelle = this.typeScore.getLibelle();
		}

	}	
	
}
