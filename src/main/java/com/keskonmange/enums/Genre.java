package com.keskonmange.enums;

import java.util.stream.Stream;

public enum Genre {

	FEMININ("Féminin", 9.740, 1.729, 4.737, 667.051),
	MASCULIN("Masculin", 13.707, 4.923, 6.673, 77.607);	

	private String libelle;
	private Double coeffPoids;
	private Double coeffTaille;
	private Double coeffAge;
	private Double complement;

	private Genre(String libelle, Double coeffPoids, Double coeffTaille, Double coeffAge, Double complement) {
		this.libelle = libelle;
		this.coeffPoids = coeffPoids;
		this.coeffTaille = coeffTaille;
		this.coeffAge = coeffAge;
		this.complement = complement;
	}

	public String getLibelle() {
		return libelle;
	}

	public Double getCoeffPoids() {
		return coeffPoids;
	}

	public Double getCoeffTaille() {
		return coeffTaille;
	}

	public Double getCoeffAge() {
		return coeffAge;
	}

	public Double getComplement() {
		return complement;
	}

	public static Genre of(String libelle) {
		return Stream.of(Genre.values())
				.filter(p -> p.getLibelle().equals(libelle))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}	
}
