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
		
	@Query("select p from Personne p where p.createur.id = :id ")
	public Iterable<Personne> getAllPersonsCreatedByUser(Integer id);

	@Query("select ab from Absence ab where ab.personne.id = :id")
	public Iterable<Absence> getAbsences(Integer id);

	@Query("select gr from Groupe gr join gr.groupePersonnes as gp where gp.id = :id")
	public Iterable<Groupe> getGroupesByPersonne(Integer id);

	@Query("select pl from Plat pl where pl.createur.id = :id")
	public Iterable<Plat> getPlats(Integer id);
	
}
