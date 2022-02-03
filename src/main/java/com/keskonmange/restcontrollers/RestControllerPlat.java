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

import com.keskonmange.entities.Plat;
import com.keskonmange.exceptions.ErreurPlat;
import com.keskonmange.services.ServicePlat;

@RestController
@CrossOrigin
@RequestMapping("api/plat")
public class RestControllerPlat
{
	private String message;
	@Autowired
	ServicePlat spl;
	@Autowired
	private MessageSource messageSource;

	private void verifPlat(Integer pid) throws ErreurPlat
	{
		if(spl.findById(pid).isEmpty())
		{
			throw new ErreurPlat(messageSource.getMessage("erreur.plat.notfound", new Object[]
			{pid}, Locale.getDefault()));
		}
	}

	@GetMapping
	public Iterable<Plat> getAll()
	{
		return spl.findAll();
	}

	@GetMapping("{id}")
	public Optional<Plat> getOne(@PathVariable("id")
	Integer pid) throws ErreurPlat
	{
		verifPlat(pid);
		return spl.findById(pid);
	}

	@PostMapping
	public Plat create(@Valid @RequestBody
	Plat plat, BindingResult result) throws ErreurPlat
	{
		if(result.hasErrors())
		{
			message = "";
			result.getFieldErrors().forEach(e ->
			{
				message += messageSource.getMessage("erreur.datalib", new Object[]
				{e.getField(), e.getDefaultMessage()}, Locale.getDefault());
			});
			throw new ErreurPlat(message);
		}
		return spl.save(plat);
	}

	@PutMapping("{id}")
	public Plat update(@RequestBody
	Plat plat, @PathVariable("id")
	Integer pid) throws ErreurPlat
	{
		verifPlat(pid);
		if(pid != plat.getId())
		{
			throw new ErreurPlat(messageSource.getMessage("erreur.personne.notequals", new Object[]
			{pid, plat.getId()}, Locale.getDefault()));
		}
		return spl.save(plat);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id")
	Integer pid) throws ErreurPlat
	{
		verifPlat(pid);
		// TODO : Vérifier les suppressions des tables relationnelles
		spl.deleteById(pid);
	}
}
