package com.keskonmange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Groupe;

public interface JpaGroupe extends CrudRepository<Groupe, Integer> {

	@Query("SELECT g FROM Groupe g WHERE g.nom =:nom ")
	public Optional<Groupe> findGroupeByNom(String nom);
	
/*	@Query("SELECT g FROM Groupe g  WHERE :user MEMBER OF g.administrateurs ")
	public Iterable<Groupe> findByUtilisateurId(Utilisateur user);
*/	
}
