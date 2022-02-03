package com.keskonmange.enums;

import java.util.stream.Stream;

public enum TypeRepas
{
	PETIT_DEJEUNER("Petit Déjeuner"), DEJEUNER("Déjeuner"), DINER("Diner");

	private String libelle;

	private TypeRepas(String libelle)
	{
		this.libelle = libelle;
	}

	public String getLibelle()
	{
		return libelle;
	}

	public static TypeRepas of(String libelle)
	{
		return Stream.of(TypeRepas.values()).filter(p -> p.getLibelle().equals(libelle)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
