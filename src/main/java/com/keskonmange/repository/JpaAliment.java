package com.keskonmange.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Aliment;
import com.keskonmange.entities.Plat;

public interface JpaAliment extends CrudRepository<Aliment, Integer>{

	@Query("select pl from Plat as pl where pl.platAliments.getId() = :id")
	public Iterable<Plat> getPlats(Integer id);
}

