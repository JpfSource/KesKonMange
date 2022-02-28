package com.keskonmange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keskonmange.entities.Groupe;
import com.keskonmange.repository.JpaGroupe;
import com.keskonmange.repository.JpaUtilisateur;

/**
 * Classe Service groupe qui fait tous les traitements nécessaires et persiste les données du groupe.
 *
 */
@Service
public class ServiceGroupe {



	@Autowired
	JpaGroupe jpaGpe;

	@Autowired
	JpaUtilisateur jpaUser;

	@Autowired
	ServicePersonne sp;

	/**
	 * Méthode qui retourne des groupes de type Iterable dont la somme des besoins caloriques de chaque groupe a été valorisée.
	 * 
	 * Elle est utlisée par les méthodes findAll(), findByUtilisateurId(Integer pid).
	 * 
	 * @param groupes as Iterable<Groupe>
	 * @return groupes as Iterable<Groupe>
	 */
	public Iterable<Groupe> getBesoinCaloriqueGroupe(Iterable<Groupe> groupes) {
		for (Groupe groupe : groupes) {
			getBesoinCaloriqueGroupe(groupe);
		}
		return groupes;
	}

	/**
	 * Méthode qui retourne un groupe de type Optional dont la somme des besoins caloriques a été valorisée.
	 * 
	 * Elle est utlisée par les méthodes findAll(), findByUtilisateurId(Integer pid).
	 * 
	 * @param groupe as Optional<Groupe>
	 * @return Optional<Groupe>
	 */
	public Optional<Groupe> getBesoinCaloriqueGroupe(Optional<Groupe> groupe) {
		if(groupe != null && groupe.isPresent()) {
			Groupe g = groupe.get();
			getBesoinCaloriqueGroupe(g);
		}
		return groupe;
	}


	/**
	 * Méthode qui retourne pour un groupe de type Groupe la somme des besoins caloriques valorisée.
	 * 
	 * @param groupe as Groupe
	 * @return groupe as Groupe
	 */
	public Groupe getBesoinCaloriqueGroupe(Groupe groupe) {
/*		Integer besoinCal = 0 ;
		if(groupe != null) {
			if(groupe.getGroupePersonnes() != null) {
				for(Personne pers : groupe.getGroupePersonnes()) {
					Optional<Personne> op = sp.findById(pers.getId());
					if(op.isPresent()) {
						Personne p = op.get();
						if(p.getBesoinsCaloriques() != null) {
							besoinCal += p.getBesoinsCaloriques();		
						}
					}
				}
			}
			groupe.setBesoinCalorique(besoinCal);
		}
*/
		return groupe;
	}


	/**
	 * Méthode qui retourne tous les groupes (Iterable<Groupe>) de la base de données.
	 * (Les besoins caloriques sont valorisés dans le return).
	 * 
	 * @return Iterable<Groupe>
	 */
	public Iterable<Groupe> findAll(){	
		return getBesoinCaloriqueGroupe(jpaGpe.findAll());
	}

	/**
	 * Méthode qui retourne tous les groupes pour un utilisateur dont l'id est donné en paramètre.
	 * (Les besoins caloriques sont valorisés dans le return).
	 * 
	 * @param pid (id de l'utilisateur)
	 * @return Iterable<Groupe>
	 */
/*
	public Iterable<Groupe> findByUtilisateurId(Integer pid){
		return getBesoinCaloriqueGroupe(jpaGpe.findByUtilisateurId(jpaUser.findById(pid).get()));
	}
 */

	/**
	 * Méthode qui retourne le groupe dont le nom est passé en paramètre.
	 * 
	 * @param nom du groupe
	 * @return Optional<Groupe>
	 */
	public Optional<Groupe> findGroupeByNom(String nom) {
		//		return jpaGpe.findGroupeByNom(nom);
		return getBesoinCaloriqueGroupe(jpaGpe.findGroupeByNom(nom));

	}

	/**
	 * Méthode qui retourne le groupe dont l'id est passé en paramètre.
	 * (Les besoins caloriques sont valorisés dans le return).
	 * 
	 * @param pid (id du groupe)
	 * @return Optional<Groupe>
	 */
	public Optional<Groupe> findById(Integer pid) {
		return getBesoinCaloriqueGroupe(jpaGpe.findById(pid));
	}

	/**
	 * Méthode qui sauvegarde le groupe passé en pramètre en base de données.
	 * (Les besoins caloriques sont valorisés dans le return).
	 * 
	 * @param groupe
	 * @return 
	 */
	public Groupe save(Groupe groupe) {
		return getBesoinCaloriqueGroupe(jpaGpe.save(groupe));

	}

	/**
	 * Méthode qui supprimer le groupe dont l'id est passé en paramètre.
	 * @param pid (id du groupe)
	 */
	public void deleteById(Integer pid) {

		Groupe gpeToDelete = jpaGpe.findById(pid).get(); 
/*
		gpeToDelete.getAdministrateurs().clear();
		gpeToDelete.getGroupePersonnes().clear();
*/
		jpaGpe.save(gpeToDelete);

		jpaGpe.deleteById(pid);
	}

}
