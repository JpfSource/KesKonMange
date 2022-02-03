package com.keskonmange.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Aliment;
import com.keskonmange.entities.Allergie;

public interface JpaAliment extends CrudRepository<Aliment, Integer>{

	@Query("select al from Allergie as al where al.aliments.getid() = :id")
	public Iterable<Allergie> getAllergieByAliment(Integer id);
}

