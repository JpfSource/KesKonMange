package com.keskonmange.services;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.keskonmange.entities.Absence;
import com.keskonmange.entities.Groupe;
import com.keskonmange.entities.RepartitionCalorique;
import com.keskonmange.entities.Repas;
import com.keskonmange.exceptions.ErreurGroupe;
import com.keskonmange.repository.JpaGroupe;

/**
 * Classe Service groupe qui fait tous les traitements nécessaires et persiste les données du groupe.
 *
 */
@Service
public class ServiceGroupe {

	private String message;
	
	@Autowired
	JpaGroupe jg;

	@Autowired
	ServicePersonne sp;
	
	@Autowired
    private MessageSource messageSource;

	private static void setBesoinsCaloriques(Groupe groupe) {
		groupe.setBesoinCalorique(0);
		groupe.getGroupePersonnes().forEach(p -> {
			ServicePersonne.setBesoinsCaloriques(p);
			groupe.setBesoinCalorique(groupe.getBesoinCalorique() + p.getBesoinsCaloriques());
		});
	}
	
	/**
	 * Méthode qui vérifie si l'id du groupe existe.
	 * @param groupe
	 * @param pid
	 * @throws ErreurGroupe
	 */
	private void verifGroupe(Optional<Groupe> groupe, Integer pid) throws ErreurGroupe {
		if(groupe.isEmpty()) {
			throw new ErreurGroupe(messageSource.getMessage("erreur.groupe.notfound", new Object[]{pid}, Locale.getDefault()));
		}
		
	}
	
	/**
	 * Méthode qui vérifie si le nom de groupe est déjà présent en BdD.
	 * @param groupe
	 * @throws ErreurGroupe
	 */
	private void verifNomGroupe(Groupe groupe) throws ErreurGroupe {	
	Optional<Groupe> groupeExistant = jg.getGroupeByNom(groupe.getNom());
	if(groupeExistant.isPresent()) {
		throw new ErreurGroupe(messageSource.getMessage("erreur.groupe.namealreadyexist", new Object[]{groupe.getNom()}, Locale.getDefault()));
	}
	}
	
	private Groupe save(Groupe groupe) {
		Groupe groupeSaved = jg.save(groupe);
		setBesoinsCaloriques(groupeSaved);
		return groupeSaved;
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
	 * @throws ErreurGroupe 
	 */
	public Optional<Groupe> findById(Integer pid) throws ErreurGroupe {
		Optional<Groupe> groupe = jg.findById(pid);
		verifGroupe(groupe, pid);
		setBesoinsCaloriques(groupe.get());
		return groupe;
	}

	/**
	 * Méthode qui sauvegarde le groupe passé en pramètre en base de données.
	 * (Les besoins caloriques sont valorisés dans le return).
	 * 
	 * @param groupe
	 * @return 
	 * @throws ErreurGroupe 
	 */
	public Groupe createGroupe(Groupe groupe, BindingResult result) throws ErreurGroupe {
		if(result.hasErrors()) {
			message = "";
			result.getFieldErrors().forEach(e -> {
				message += messageSource.getMessage("erreur.datalib", new Object[]{e.getField(), e.getDefaultMessage()}, Locale.getDefault());
			});
			throw new ErreurGroupe(message);
		}
		verifNomGroupe(groupe);
		return save(groupe);
	}

	/**
	 * Méthode qui supprimer le groupe dont l'id est passé en paramètre.
	 * @param pid (id du groupe)
	 * @throws ErreurGroupe 
	 */
	public void deleteById(Integer pid) throws ErreurGroupe {

		// TODO
		// => inclus Personnes
		// => inclus Score
		// => * Absence -> @Query
		// => * Répartition calorique -> RepartitionCalorique.groupe
		// => * Repas -> Repas.groupe
		Optional<Groupe> groupeToVerify = jg.findById(pid);
		verifGroupe(groupeToVerify, pid);
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

	public Groupe updateGroupe(Groupe groupe, Integer pid) throws ErreurGroupe {
		Optional<Groupe> groupeToVerify = jg.findById(pid);
		verifGroupe(groupeToVerify, pid);
		if(pid != groupe.getId()) {
			throw new ErreurGroupe(messageSource.getMessage("erreur.groupe.notequals", new Object[]{pid, groupe.getId()}, Locale.getDefault()));
		}
		return save(groupe);
	}

}
