package com.keskonmange.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.keskonmange.enums.TypeScore;

@Entity
@Table(name = "SCORE")
public class Score {

	/* FIELDS */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Basic
	@NotNull
	@NotBlank
	@Column(name = "TYPE_SCORE", length = 20, nullable = false, unique = false)
	private TypeScore typeScore;

	@Transient
	private String typeScoreLibelle;

	@NotNull
	@Column(name = "CODE", length = 5, nullable = false)
	private String code;

	@NotNull
	@Column(name = "LIBELLE", length = 80, nullable = false)
	private String libelle;

	@Column(name = "URL_PHOTO", nullable = true)
	private String urlPhoto;

	/* RELATIONS */

	
	/* CONSTRUCTORS */
	
	public Score() {
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
	
	/* PUBLIC METHODS */	
	@Override
	public String toString() {
		return "Score [id=" + id + ", typeScoreLibelle=" + typeScoreLibelle + ", typeScore=" + typeScore + ", code="
				+ code + ", libelle=" + libelle + ", urlPhoto=" + urlPhoto + "]";
	}

	
	/* PERSISTENT METHODS */
	@PostLoad
	void fillTransient() {
		if (typeScore != null) {
			this.typeScoreLibelle = this.typeScore.getLibelle();
		}
	}

	@PrePersist
	void fillPersistent() {
		if (typeScoreLibelle != null) {
			this.typeScore = TypeScore.of(typeScoreLibelle);
		}
	}	
}
