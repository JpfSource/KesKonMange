package com.keskonmange.enums;

import java.util.stream.Stream;

public enum Role {
	ADMIN("Administrateur"), USER("Utilisateur");

	private String libelle;

	private Role(String libelle)
	{
		this.libelle = libelle;
	}

	public String getLibelle()
	{
		return libelle;
	}

	public static Role of(String libelle)
	{
		return Stream.of(Role.values()).filter(p -> p.getLibelle().equals(libelle)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}

