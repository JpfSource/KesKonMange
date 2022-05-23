package com.keskonmange.restcontrollers;

import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "CRUD Rest APIs for Groupe entity")
@RestController
@CrossOrigin
@RequestMapping("api/groupes")
public class RestControllerGroupe {

	private String message;
	
	@Autowired
	ServiceGroupe sg;
	
	@Autowired
    private MessageSource messageSource;
	
	/**
	 * Méthode qui vérifie si l'id du groupe existe.
	 * @param pid
	 * @throws ErreurGroupe
	 */
	private void verifGroupe(Integer pid) throws ErreurGroupe {
		if(sg.findById(pid).isEmpty()){
			throw new ErreurGroupe(messageSource.getMessage("erreur.groupe.notfound", new Object[]{pid}, Locale.getDefault()));
		}
	}
	

	
	
	/**
	 * Renvoie le JSON de la liste de tous les groupes.
	 * @return
	 */
	@ApiOperation(value = "Get all groups", notes = "Returns a collection of group")
	@GetMapping
	public Iterable<Groupe> getAll(){
		return sg.findAll();
	}
	
	/**
	 * Renvoie le JSON du groupe correspondant à l'id de l'URL.
	 * 
	 * @param pid
	 * @return
	 * @throws ErreurGroupe
	 */
	@ApiOperation(value = "Get group by id", notes = "Returns a group as per the id")
	@GetMapping("{id}")
	public Optional<Groupe> getOne(@PathVariable("id") @ApiParam(name = "id", value = "Group id", example = "1") Integer pid) throws ErreurGroupe{
		return sg.findById(pid);
	}
	
	/**
	 * Envoie les données du groupe du @RequestBody pour persistance en base de données.
	 * 
	 * @param groupe
	 * @param result
	 * @return
	 * @throws ErreurGroupe
	 */
	@ApiOperation(value = "Create a group", notes = "Returns a group created")
	@PostMapping
	public Groupe create(@Valid @RequestBody Groupe groupe, BindingResult result) throws ErreurGroupe{
		return sg.createGroupe(groupe, result);
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
	@ApiOperation(value = "Update group by id", notes = "Returns a group updated")
	@PutMapping("{id}")
	public Groupe update(@RequestBody Groupe groupe, @PathVariable ("id") @ApiParam(name = "id", value = "Group id", example = "1") Integer pid) throws ErreurGroupe {

		return sg.updateGroupe(groupe, pid);
	}

	/**
	 * Supprime le groupe correspondant à l'id de l'URL.
	 * @param pid
	 * @throws ErreurGroupe
	 */
	@ApiOperation(value = "Delete a group")
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") @ApiParam(name = "id", value = "Group id", example = "1") Integer pid) throws ErreurGroupe{
		sg.deleteById(pid);
	}
	
	
}
