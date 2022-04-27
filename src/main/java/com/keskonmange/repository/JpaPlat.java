package com.keskonmange.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Plat;
import com.keskonmange.entities.Repas;

public interface JpaPlat extends CrudRepository<Plat, Integer>{
	
	@Query("select re from Repas as re where re.plat.getId() = :id")
	public Iterable<Repas> getRepas(Integer id);
}
