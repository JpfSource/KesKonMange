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

import com.keskonmange.entities.Personne;
import com.keskonmange.exceptions.ErreurPersonne;
import com.keskonmange.services.ServicePersonne;

@RestController
@CrossOrigin
@RequestMapping("api/personnes")
public class RestControllerPersonne
{
	private String message;

	@Autowired
	ServicePersonne sp;

	@Autowired
	private MessageSource messageSource;	

	private void verifPersonne(Integer pid) throws ErreurPersonne {
		if(sp.findById(pid).isEmpty()){
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.notfound", new Object[]
			{pid}, Locale.getDefault()));
		}
	}

	@GetMapping("all")
	public Iterable<Personne> getAll() {
		return sp.findAll();
	}

	@GetMapping("{id}")
	public Optional<Personne> getOne(@PathVariable("id") Integer pid) throws ErreurPersonne {
		verifPersonne(pid);
		return sp.findById(pid);
	}

	@PostMapping
	public Personne create(@Valid @RequestBody
	Personne personne, BindingResult result) throws ErreurPersonne {
		if(result.hasErrors())
		{
			message = "";
			result.getFieldErrors().forEach(e ->
			{
				message += messageSource.getMessage("erreur.datalib", new Object[]
				{e.getField(), e.getDefaultMessage()}, Locale.getDefault());
			});
			throw new ErreurPersonne(message);
		}
		return sp.save(personne);
	}

	
	@PutMapping("{id}")
	public Personne update(@RequestBody Personne personne, @PathVariable("id") Integer pid) throws ErreurPersonne {
		verifPersonne(pid);
		if(pid != personne.getId()){
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.notequals", new Object[]{pid, personne.getId()}, Locale.getDefault()));
		}
		return sp.save(personne);
	}
	
	@PutMapping("/recalcul")
	public Integer recalcul(@RequestBody Personne personne) throws ErreurPersonne {
		return ServicePersonne.calculBesoinsCaloriques(personne);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer pid) throws ErreurPersonne {
		verifPersonne(pid);
		sp.deleteById(pid);
	}
}
