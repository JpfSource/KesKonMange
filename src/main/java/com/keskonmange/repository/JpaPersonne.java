package com.keskonmange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Absence;
import com.keskonmange.entities.Groupe;
import com.keskonmange.entities.Personne;
import com.keskonmange.entities.Plat;

public interface JpaPersonne extends CrudRepository<Personne, Integer> {
	
	@Query("select p from Personne p where p.email =:email ")
	public Optional<Personne> getPersonneByEmail(String email);
		
	@Query("select ab from Absence as ab where ab.personne.getId() = :id")
	public Iterable<Absence> getAbsences(Integer id);

	@Query("select gr from Groupe as gr where gr.groupePersonnes.getId() = :id")
	public Iterable<Groupe> getGroupes(Integer id);

	@Query("select pl from Plat as pl where pl.createur.getId() = :id")
	public Iterable<Plat> getPlats(Integer id);
}
