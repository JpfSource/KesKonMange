package com.keskonmange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.keskonmange.entities.Absence;
import com.keskonmange.entities.Groupe;
import com.keskonmange.entities.Personne;
import com.keskonmange.entities.Plat;
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
			return 0;
		}
	}

	public static void setBesoinsCaloriques(Personne personne){
		personne.setBesoinsCaloriques(calculBesoinsCaloriques(personne));
	}

	private static Iterable<Personne> setBesoinsCaloriques(Iterable<Personne> personnes){
		for(Personne personne : personnes) {
			setBesoinsCaloriques(personne);
		}
		return personnes;
	}
	
	private static Optional<Personne> setBesoinsCaloriques(Optional<Personne> personne){
		if(personne != null && personne.isPresent()) {
			setBesoinsCaloriques(personne.get());
		}
		return personne;
	}
	
	public Optional<Personne> findById(Integer pid){
		return setBesoinsCaloriques(jp.findById(pid));
	}

	public Iterable<Personne> findAll(){
		return setBesoinsCaloriques(jp.findAll());
	}

	public Personne save(Personne personne){
		if(personne.getObjectifCalorique() == null) {personne.setObjectifCalorique(100);}
		if(personne.getUrlPhoto() == null) {personne.setUrlPhoto("https://icon-library.com/images/no-profile-picture-icon/no-profile-picture-icon-18.jpg");}
		personne = jp.save(personne);
		setBesoinsCaloriques(personne);
		return personne;
	}

	public void deleteById(Integer pid)
	{
		// TODO
		// => inclus createur
		// => inclus Allergie
		// => * Absence -> @Query
		// => * Groupe -> Groupe.groupePersonnes
		// => * Plat -> Plat.createur
		jp.deleteById(pid);
	}
	
	public Optional<Personne> getPersonneByEmail(String email){
		return jp.getPersonneByEmail(email);
	}
	
	public Iterable<Absence> getAbsences(Integer id){
		return jp.getAbsences(id);
	}

	public Iterable<Groupe> getGroupes(Integer id){
		return jp.getGroupes(id);
	}

	public Iterable<Plat> getPlats(Integer id){
		return jp.getPlats(id);
	}

}
