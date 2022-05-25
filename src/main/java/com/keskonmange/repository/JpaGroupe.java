package com.keskonmange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Absence;
import com.keskonmange.entities.Groupe;
import com.keskonmange.entities.RepartitionCalorique;
import com.keskonmange.entities.Repas;

public interface JpaGroupe extends CrudRepository<Groupe, Integer> {

	@Query("select g from Groupe g where g.nom =:nom ")
	public Optional<Groupe> getGroupeByNom(String nom);
	
	@Query("select ab from Absence ab where ab.groupe.id = :id")
	public Iterable<Absence> getAbsences(Integer id);

	@Query("select rc from RepartitionCalorique as rc where rc.groupe.id = :id")
	public Iterable<RepartitionCalorique> getRepartitionsCaloriques(Integer id);

	@Query("select re from Repas re where re.groupe.id = :id")
	public Iterable<Repas> getRepas(Integer id);

}
