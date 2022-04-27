package com.keskonmange.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keskonmange.entities.Absence;
import com.keskonmange.entities.Groupe;
import com.keskonmange.entities.RepartitionCalorique;
import com.keskonmange.entities.Repas;
import com.keskonmange.repository.JpaGroupe;

/**
 * Classe Service groupe qui fait tous les traitements nécessaires et persiste les données du groupe.
 *
 */
@Service
public class ServiceGroupe {

	@Autowired
	JpaGroupe jg;

	@Autowired
	ServicePersonne sp;

	private static void setBesoinsCaloriques(Groupe groupe) {
		groupe.setBesoinCalorique(0);
		groupe.getGroupePersonnes().forEach(p -> {
			ServicePersonne.setBesoinsCaloriques(p);
			groupe.setBesoinCalorique(groupe.getBesoinCalorique() + p.getBesoinsCaloriques());
		});
	}
	
	/**
	 * Méthode qui retourne tous les groupes (Iterable<Groupe>) de la base de données.
	 * (Les besoins caloriques sont valorisés dans le return).
	 * 
	 * @return Iterable<Groupe>
	 */
	public Iterable<Groupe> findAll(){
		Iterable<Groupe> groupes = jg.findAll();
		groupes.forEach(g -> setBesoinsCaloriques(g));
		return groupes;
	}

	/**
	 * Méthode qui retourne le groupe dont l'id est passé en paramètre.
	 * (Les besoins caloriques sont valorisés dans le return).
	 * 
	 * @param pid (id du groupe)
	 * @return Optional<Groupe>
	 */
	public Optional<Groupe> findById(Integer pid) {
		Optional<Groupe> groupe = jg.findById(pid);
		setBesoinsCaloriques(groupe.get());
		return groupe;
	}

	/**
	 * Méthode qui sauvegarde le groupe passé en pramètre en base de données.
	 * (Les besoins caloriques sont valorisés dans le return).
	 * 
	 * @param groupe
	 * @return 
	 */
	public Groupe save(Groupe groupe) {
		Groupe groupeSaved = jg.save(groupe);
		setBesoinsCaloriques(groupeSaved);
		return groupeSaved;
	}

	/**
	 * Méthode qui supprimer le groupe dont l'id est passé en paramètre.
	 * @param pid (id du groupe)
	 */
	public void deleteById(Integer pid) {

		// TODO
		// => inclus Personnes
		// => inclus Score
		// => * Absence -> @Query
		// => * Répartition calorique -> RepartitionCalorique.groupe
		// => * Repas -> Repas.groupe
		jg.deleteById(pid);
	}

	/**
	 * Méthode qui retourne le groupe dont le nom est passé en paramètre.
	 * 
	 * @param nom du groupe
	 * @return Optional<Groupe>
	 */
	public Optional<Groupe> findGroupeByNom(String nom) {
		Optional<Groupe> groupe = jg.getGroupeByNom(nom);
		setBesoinsCaloriques(groupe.get());
		return groupe;
	}

	public Iterable<Absence> getAbsences(Integer id){
		return jg.getAbsences(id);
	}
	
	public Iterable<RepartitionCalorique> getRepartitionsCaloriques(Integer id){
		return jg.getRepartitionsCaloriques(id);
	}
	
	public Iterable<Repas> getRepas(Integer id){
		return jg.getRepas(id);
	}

}
