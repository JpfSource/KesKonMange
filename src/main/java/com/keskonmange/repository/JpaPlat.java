package com.keskonmange.repository;

import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Plat;

public interface JpaPlat extends CrudRepository<Plat, Integer>
{
//	@Query("SELECT plat from PLAT plat WHERE plat.aliment_plat.id = :id")
//	public Iterable<Plat> getEmpruntByPlat(Integer id);
}
