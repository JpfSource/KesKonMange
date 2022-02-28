package com.keskonmange.restcontrollers;

import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keskonmange.entities.Groupe;
import com.keskonmange.exceptions.ErreurGroupe;
import com.keskonmange.services.ServiceGroupe;

@RestController
@CrossOrigin
@RequestMapping("api/groupes")
public class RestControllerGroupe {

	private String message; 
	
	@Autowired
	ServiceGroupe serviceGroupe;
	
	@Autowired
    private MessageSource messageSource;
	
	/**
	 * Méthode qui vérifie si l'id du groupe existe.
	 * @param pid
	 * @throws ErreurGroupe
	 */
	private void verifGroupe(Integer pid) throws ErreurGroupe {
		if(serviceGroupe.findById(pid).isEmpty()){
			throw new ErreurGroupe(messageSource.getMessage("erreur.groupe.notfound", new Object[]{pid}, Locale.getDefault()));
		}
	}
	
	/**
	 * Méthode qui vérifie si le nom de groupe est déjà présent en BdD.
	 * @param groupe
	 * @throws ErreurGroupe
	 */
	private void verifNomGroupe(Groupe groupe) throws ErreurGroupe {	
	Optional<Groupe> groupeExistant = serviceGroupe.findGroupeByNom(groupe.getNom());
	if(groupeExistant.isPresent()) {
		throw new ErreurGroupe(messageSource.getMessage("erreur.groupe.namealreadyexist", new Object[]{groupe.getNom()}, Locale.getDefault()));
	}
	}
	
	
	/**
	 * Renvoie le JSON de la liste de tous les groupes.
	 * @return
	 */
	@GetMapping
	public Iterable<Groupe> getAll(){
		return serviceGroupe.findAll();
	}
	
	// api/groupes/utilisateur/1
	/**
	 * Renvoie le JSON de tous les groupes dont l'id de l'utilisateur est passé paramètre de l'URL.
	 * 
	 * @param pid
	 * @return
	 */
/*
	@GetMapping({"/utilisateur/{id}"})
	public Iterable<Groupe> getAllGroupeByUtilisateur(@PathVariable("id") Integer pid){
		return serviceGroupe.findByUtilisateurId(pid);
	}
*/	

	/**
	 * Renvoie le JSON du groupe correspondant à l'id de l'URL.
	 * 
	 * @param pid
	 * @return
	 * @throws ErreurGroupe
	 */
	@GetMapping("{id}")
	public Optional<Groupe> getOne(@PathVariable("id") Integer pid) throws ErreurGroupe{
		verifGroupe(pid);
		return serviceGroupe.findById(pid);
	}
	
	/**
	 * Envoie les données du groupe du @RequestBody pour persistance en base de données.
	 * 
	 * @param groupe
	 * @param result
	 * @return
	 * @throws ErreurGroupe
	 */
	@PostMapping
	public Groupe create(@Valid @RequestBody Groupe groupe, BindingResult result) throws ErreurGroupe{
		if(result.hasErrors()) {
			message = "";
			
			result.getFieldErrors().forEach(e -> {
				message += messageSource.getMessage("erreur.datalib", new Object[]{e.getField(), e.getDefaultMessage()}, Locale.getDefault());
			});
			throw new ErreurGroupe(message);
		}
		verifNomGroupe(groupe);
		return serviceGroupe.save(groupe);
	}

	/**
	 * Envoie les données du groupe du @RequestBody pour persistance en base de données.
	 * Actualise les données.
	 * 
	 * @param groupe
	 * @param pid
	 * @return
	 * @throws ErreurGroupe
	 */
	@PutMapping("{id}")
	public Groupe update(@RequestBody Groupe groupe, @PathVariable ("id") Integer pid) throws ErreurGroupe {
		verifGroupe(pid);
		if(pid != groupe.getId()) {
			throw new ErreurGroupe(messageSource.getMessage("erreur.groupe.notequals", new Object[]{pid, groupe.getId()}, Locale.getDefault()));
		}
		return serviceGroupe.save(groupe);
	}

	/**
	 * Supprime le groupe correspondant à l'id de l'URL.
	 * @param pid
	 * @throws ErreurGroupe
	 */
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer pid) throws ErreurGroupe{
		verifGroupe(pid);
		// TODO : Vérifier les suppressions des tables relationnelles
		serviceGroupe.deleteById(pid);
	}
	
	
}
