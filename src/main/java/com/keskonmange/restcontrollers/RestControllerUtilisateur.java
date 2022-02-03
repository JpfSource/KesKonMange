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

import com.keskonmange.entities.Utilisateur;
import com.keskonmange.exceptions.ErreurUtilisateur;
import com.keskonmange.services.ServiceUtilisateur;

@RestController
@CrossOrigin
@RequestMapping("api/utilisateurs")
public class RestControllerUtilisateur {

	private String message;
	
	@Autowired
	ServiceUtilisateur su;
	
	@Autowired
    private MessageSource messageSource;	

	private void verifUtilisateur(Integer pid) throws ErreurUtilisateur {
		if(su.findById(pid).isEmpty()){
			throw new ErreurUtilisateur(messageSource.getMessage("erreur.utilisateur.notfound", new Object[]{pid}, Locale.getDefault()));
		}
	}
		
	@GetMapping
	public Iterable<Utilisateur> getAll(){
		return su.findAll();
	}

	@GetMapping("{id}")
	public Optional<Utilisateur> getOne(@PathVariable("id") Integer pid) throws ErreurUtilisateur{
		verifUtilisateur(pid);
		return su.findById(pid);
	}
	
	@PostMapping
	public Utilisateur create(@Valid @RequestBody Utilisateur utilisateur, BindingResult result) throws ErreurUtilisateur{
		if(result.hasErrors()) {
			message = "";
			result.getFieldErrors().forEach(e -> {
				message += messageSource.getMessage("erreur.datalib", new Object[]{e.getField(), e.getDefaultMessage()}, Locale.getDefault());
			});
			throw new ErreurUtilisateur(message);
		}
		return su.save(utilisateur);
	}

	@PutMapping("{id}")
	public Utilisateur update(@RequestBody Utilisateur utilisateur, @PathVariable ("id") Integer pid) throws ErreurUtilisateur{
		verifUtilisateur(pid);
		if(pid != utilisateur.getId()) {
			throw new ErreurUtilisateur(messageSource.getMessage("erreur.utilisateur.notequals", new Object[]{pid, utilisateur.getId()}, Locale.getDefault()));
		}
		return su.save(utilisateur);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer pid) throws ErreurUtilisateur{
		verifUtilisateur(pid);
		// TODO : Vérifier les suppressions des tables relationnelles
		su.deleteById(pid);
	}	
}
