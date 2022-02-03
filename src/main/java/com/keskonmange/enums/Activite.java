package com.keskonmange.enums;

import java.util.stream.Stream;

public enum Activite {

	SEDENTAIRE("Sédentaire", 1.375),
	PEU_ACTIF("Peu actif", 1.56),
	ACTIF("Actif", 1.64),
	TRES_ACTIF("Très actif", 1.82);	

	private String libelle;
	private Double coefficient;

	private Activite(String libelle, Double coefficient) {
		this.libelle = libelle;
		this.coefficient = coefficient;
	}

	public String getLibelle() {
		return libelle;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public static Activite of(String libelle) {
		return Stream.of(Activite.values())
				.filter(p -> p.getLibelle().equals(libelle))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}	

}
