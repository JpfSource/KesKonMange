package com.keskonmange.enums;

import java.util.stream.Stream;

public enum TypePlat
{
	ENTREE("Entrée"), PLAT_PRINCIPAL("Plat principal"), DESSERT("Dessert"), BOISSON("Boisson"), CEREALES("Céréales"),
	LAITAGE("Laitage");

	private String libelle;

	private TypePlat(String libelle)
	{
		this.libelle = libelle;
	}

	public String getLibelle()
	{
		return libelle;
	}

	public static TypePlat of(String libelle)
	{
		return Stream.of(TypePlat.values()).filter(p -> p.getLibelle().equals(libelle)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
