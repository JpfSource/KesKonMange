package com.keskonmange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Utilisateur;

public interface JpaUtilisateur extends CrudRepository<Utilisateur, Integer> {

	@Query("SELECT u FROM Utilisateur u WHERE u.email =:email ")
	public Optional<Utilisateur> findUtilisateurByEmail(String email);
}
