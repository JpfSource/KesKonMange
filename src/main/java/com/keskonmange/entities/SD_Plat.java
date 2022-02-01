package com.keskonmange.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PLAT")
public class SD_Plat
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@NotBlank
	@Column(name = "libelle", length = 150, nullable = false, unique = false)
	private String libellePlat;

	public SD_Plat()
	{
	}

	public SD_Plat(int id,@NotNull @NotBlank
	String libellePlat)
	{
		super();
		this.id = id;
		this.libellePlat = libellePlat;
	}

	public int getId()
	{
		return id;
	}

	public String getLibellePlat()
	{
		return libellePlat;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setLibellePlat(String libellePlat)
	{
		this.libellePlat = libellePlat;
	}
}
