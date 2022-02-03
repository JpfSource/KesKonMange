package com.keskonmange.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import com.keskonmange.enums.TypeRepas;

@Entity
@Table(name = "REPAS")
public class Repas
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_REPAS")
	private Date Date_Repas;
	@Basic
	@Column(name = "TypeRepas", length = 20, nullable = true, unique = false)
	private String typeRepasLibelle;
	@Transient
	private TypeRepas typeRepas;

	public Repas()
	{
		this(null, null, null);
	}

	public Repas(Date date_Repas,String typeRepasLibelle)
	{
		super();
		this.Date_Repas = date_Repas;
		this.typeRepasLibelle = typeRepasLibelle;
	}

	public Repas(Integer id,Date date_Repas,String typeRepasLibelle)
	{
		super();
		this.id = id;
		this.Date_Repas = date_Repas;
		this.typeRepasLibelle = typeRepasLibelle;
	}

	public Integer getId()
	{
		return id;
	}

	public Date getDate_Repas()
	{
		return Date_Repas;
	}

	public String getTypeRepasLibelle()
	{
		return typeRepasLibelle;
	}

	public TypeRepas getTypeRepas()
	{
		return typeRepas;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public void setDate_Repas(Date date_Repas)
	{
		Date_Repas = date_Repas;
	}

	public void setTypeRepasLibelle(String typeRepasLibelle)
	{
		this.typeRepasLibelle = typeRepasLibelle;
	}

	public void setTypeRepas(TypeRepas typeRepas)
	{
		this.typeRepas = typeRepas;
	}
	
	
	/* PERSISTENT METHODS */
	@PostLoad
	void fillTransient() {
		if (!typeRepasLibelle.isEmpty()) {
			this.typeRepas = TypeRepas.of(typeRepasLibelle);
		}
	}

	@PrePersist
	void fillPersistent() {
		if (typeRepas != null) {
			this.typeRepasLibelle = this.typeRepas.getLibelle();
		}
	}

}
