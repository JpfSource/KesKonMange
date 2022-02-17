package com.keskonmange.entities;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keskonmange.enums.TypePlat;

@Entity
@Table(name = "PLAT")
public class Plat
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@NotBlank
	@Column(name = "LIBELLE", length = 150, nullable = false, unique = false)
	private String libellePlat;
	
	@ManyToMany
	@JoinTable(name = "ALIMENT_PLAT", joinColumns = @JoinColumn(name = "ID_PLAT", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_ALIMENT", referencedColumnName = "ID"))
	private Set<Aliment> aliment_plat;
	
	@Basic
	@Column(name = "TYPE_PLAT", length = 20, nullable = true, unique = false)
	private String typePlatLibelle;
	
	@Transient
	private TypePlat typePlat;
	
	@JsonIgnore
	@ManyToOne
	private Repas repas;

	
	public Plat()
	{
		this(null, null, null);
	}

	public Plat(@NotNull @NotBlank
	String libellePlat,String typePlatLibelle)
	{
		super();
		this.libellePlat = libellePlat;
		this.typePlatLibelle = typePlatLibelle;
	}

	public Plat(Integer id,@NotNull @NotBlank
	String libellePlat,String typePlatLibelle)
	{
		super();
		this.id = id;
		this.libellePlat = libellePlat;
		this.typePlatLibelle = typePlatLibelle;
	}

	public Integer getId()
	{
		return id;
	}

	public String getLibellePlat()
	{
		return libellePlat;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public void setLibellePlat(String libellePlat)
	{
		this.libellePlat = libellePlat;
	}

	public String getTypePlatLibelle()
	{
		return typePlatLibelle;
	}

	public TypePlat getTypePlat()
	{
		return typePlat;
	}

	public void setTypePlatLibelle(String typePlatLibelle)
	{
		this.typePlatLibelle = typePlatLibelle;
	}

	public void setTypePlat(TypePlat typePlat)
	{
		this.typePlat = typePlat;
	}
	
	/* PERSISTENT METHODS */
	@PostLoad
	void fillTransient() {
		if (!typePlatLibelle.isEmpty()) {
			this.typePlat = TypePlat.of(typePlatLibelle);
		}
	}

	@PrePersist
	void fillPersistent() {
		if (typePlat != null) {
			this.typePlatLibelle = this.typePlat.getLibelle();
		}
	}
}
