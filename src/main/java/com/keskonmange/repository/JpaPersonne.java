package com.keskonmange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.keskonmange.entities.Personne;

public interface JpaPersonne extends CrudRepository<Personne, Integer> {
	
	@Query("SELECT p FROM Personne p WHERE p.email =:email ")
	public Optional<Personne> findByEmail(String email);
	
	Boolean existsByEmail(String email);	
}
