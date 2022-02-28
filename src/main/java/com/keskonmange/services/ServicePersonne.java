package com.keskonmange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keskonmange.entities.Personne;
import com.keskonmange.enums.BesoinEnergetiqueMineur;
import com.keskonmange.repository.JpaPersonne;
import com.keskonmange.utils.UtilDate;

@Service
public class ServicePersonne {

	@Autowired
	JpaPersonne jp;
	
	private static Boolean isPersonneOkForCalcul(Personne personne) {
		return personne != null
				&& personne.getDateNaissance() != null
				&& personne.getPoids() != null
				&& personne.getGenre() != null
				&& personne.getTaille() != null
				&& personne.getActivite() != null
				&& personne.getObjectifCalorique() != null;
	}

	public static Integer calculBesoinsCaloriques(Personne personne){
		if (isPersonneOkForCalcul(personne)) {
			Integer age = UtilDate.calculAge(personne.getDateNaissance());

			Double resultatBrut;
			if(age < 18) {
				resultatBrut = Double.valueOf(personne.getPoids() * BesoinEnergetiqueMineur.of(personne.getGenre(), age).getBesoinEnergetique());
			}
			else {
				resultatBrut = Double.valueOf((personne.getPoids() * personne.getGenre().getCoeffPoids())
						+ (personne.getTaille() * personne.getGenre().getCoeffTaille())
						- (age * personne.getGenre().getCoeffAge())
						+ personne.getGenre().getComplement());
			}
			Double resultatNet = resultatBrut * personne.getActivite().getCoefficient();
			
			Double ratio = (double)personne.getObjectifCalorique();
			ratio = ratio / 100;
			Double besoinsCaloriques = resultatNet * ratio;
			Long r2 = Long.valueOf(Math.round(besoinsCaloriques));
			Integer result = Integer.valueOf(r2.toString()); 
					
			return result;
		}
		else {
			return null;
		}
	}

	private static Iterable<Personne> calculBesoinsCaloriques(Iterable<Personne> personnes){
		for(Personne personne : personnes) {
			personne.setBesoinsCaloriques(calculBesoinsCaloriques(personne));
		}
		return personnes;
	}
	private static Optional<Personne> calculBesoinsCaloriques(Optional<Personne> personne){
		if(personne != null && personne.isPresent()) {
			personne.get().setBesoinsCaloriques(calculBesoinsCaloriques(personne.get()));
		}
		return personne;
	}

	public Optional<Personne> findById(Integer pid){
		return calculBesoinsCaloriques(jp.findById(pid));
	}

	public Iterable<Personne> findAll(){
		Iterable<Personne> personnes = jp.findAll();
		return calculBesoinsCaloriques(personnes);
	}

	public Personne save(Personne personne){
		return jp.save(personne);
	}

	public void deleteById(Integer pid)
	{
		jp.deleteById(pid);
	}
}
