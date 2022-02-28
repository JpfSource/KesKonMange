package com.keskonmange.enums;

import java.util.stream.Stream;

public enum TypeScore
{
	ECO_SCORE("EcoScore"),
	NOVA_GROUP("NovaGroupe"),
	NUTRI_SCORE("NutriScore");

	private String libelle;

	private TypeScore(String libelle)
	{
		this.libelle = libelle;
	}

	public String getLibelle()
	{
		return libelle;
	}

	public static TypeScore of(String libelle)
	{
		return Stream.of(TypeScore.values())
				.filter(p -> p.getLibelle().equals(libelle))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
