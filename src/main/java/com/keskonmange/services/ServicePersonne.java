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

	/**
	 * Rnvoie un boolean pour s'assurer que le calcul sur les données d'une personne est possible.
	 * @param personne
	 * @return
	 */
	private static Boolean isPersonneOkForCalcul(Personne personne) {
		return personne != null
				&& personne.getDateNaissance() != null
				&& personne.getPoids() != null
				&& personne.getGenre() != null
				&& personne.getTaille() != null
				&& personne.getActivite() != null
				&& personne.getObjectifCalorique() != null;
	}

	/**
	 * Renvoie le besoin calorique d'une personne en fonction des données saisies.
	 * @param personne
	 * @return
	 */
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

	/**
	 * SET de l'attribut BesoinsCaloriques d'une personne.
	 * @param personne
	 */
	public static void setBesoinsCaloriques(Personne personne){
		personne.setBesoinsCaloriques(calculBesoinsCaloriques(personne));
	}

	/**
	 * SET de l'attribut BesoinsCaloriques d'une collection de personnes.
	 * @param personnes
	 * @return
	 */
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
	
	/**
	 * Renvoie la personne dont l'identifiant est passé en paramètre.
	 * @param pid
	 * @return
	 */
	public Optional<Personne> findById(Integer pid){
		return setBesoinsCaloriques(jp.findById(pid));
	}
	
	/**
	 * Renvoie toutes les personnes créées par un utilisateur dont l'identifiant est passé en paramètre.
	 * @param id
	 * @return
	 */
	public Iterable<Personne> getAllPersonsCreatedByUser(Integer id){
		return setBesoinsCaloriques(jp.getAllPersonsCreatedByUser(id));
	}

	/**
	 * Renvoie toutes les personnes de la BDD.
	 * @return
	 */
	public Iterable<Personne> findAll(){
		return setBesoinsCaloriques(jp.findAll());
	}

	/**
	 * Sauvegarde un utilisateur en base de données.
	 * @param personne
	 * @return
	 */
	public Personne save(Personne personne){
		if(personne.getObjectifCalorique() == null) {personne.setObjectifCalorique(100);}
		if(personne.getUrlPhoto() == null) {personne.setUrlPhoto("https://icon-library.com/images/no-profile-picture-icon/no-profile-picture-icon-18.jpg");}
		personne = jp.save(personne);
		setBesoinsCaloriques(personne);
		return personne;
	}
	
	/**
	 * Sauvegarde une personne créée par un utilisateur dont l'identifiant est passé en paramètre.
	 * @param personne
	 * @param idCreateur
	 * @return
	 */
	public Personne savePersonCreatedByUser(Personne personne, Integer idCreateur){
		Optional<Personne> creator = jp.findById(idCreateur);
		if(personne.getUrlPhoto() == null) {personne.setUrlPhoto("https://icon-library.com/images/no-profile-picture-icon/no-profile-picture-icon-18.jpg");}
		personne.setCreateur(creator.get());
		personne = jp.save(personne);
		setBesoinsCaloriques(personne);
		return personne;
	}

	/**
	 * Supprime de la BDD, la personne dont l'identifiant est passé en paramètre.
	 * @param pid
	 */
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
	
	/**
	 * Renvoie la personne dont l'email est passé en paramètre.
	 * @param email
	 * @return
	 */
	public Optional<Personne> getPersonneByEmail(String email){
		return jp.getPersonneByEmail(email);
	}
	
	/**
	 * Renvoie la liste des absences de la personne dont l'identifiant est passé en paramètre.
	 * @param id
	 * @return
	 */
	public Iterable<Absence> getAbsences(Integer id){
		return jp.getAbsences(id);
	}

	/**
	 * Renvoie la liste des groupes de la personne dont l'identifiant est passé en paramètre.
	 * @param id
	 * @return
	 */
	public Iterable<Groupe> getGroupesByPersonne(Integer id){
		return jp.getGroupesByPersonne(id);
	}

	/**
	 * Renvoie la liste des plats de la personne dont l'identifiant est passé en paramètre.
	 * @param id
	 * @return
	 */
	public Iterable<Plat> getPlats(Integer id){
		return jp.getPlats(id);
	}
	


}
