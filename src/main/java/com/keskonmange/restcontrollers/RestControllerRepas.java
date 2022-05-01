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

import com.keskonmange.entities.Repas;
import com.keskonmange.exceptions.ErreurRepas;
import com.keskonmange.services.ServiceRepas;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "CRUD Rest APIs for Repas entity")
@RestController
@CrossOrigin
@RequestMapping("api/repas")
public class RestControllerRepas
{
	private String message;
	
	@Autowired
	ServiceRepas sr;
	
	@Autowired
	private MessageSource messageSource;

	private void verifRepas(Integer pid) throws ErreurRepas {
		if(sr.findById(pid).isEmpty()) {
			throw new ErreurRepas(messageSource.getMessage("erreur.repas.notfound", new Object[]
			{pid}, Locale.getDefault()));
		}
	}

	@ApiOperation(value = "Get all repas", notes = "Returns a collection of Repas")
	@GetMapping
	public Iterable<Repas> getAll() {
		return sr.findAll();
	}

	@ApiOperation(value = "Get a repas by id", notes = "Returns a Repas as per the id")
	@GetMapping("{id}")
	public Optional<Repas> getOne(@PathVariable("id") @ApiParam(name = "id", value = "Repas id", example ="1") Integer pid) throws ErreurRepas {
		verifRepas(pid);
		return sr.findById(pid);
	}

	@ApiOperation(value = "Create a repas", notes = "Returns a Repas created")
	@PostMapping
	public Repas create(@Valid @RequestBody Repas repas, BindingResult result) throws ErreurRepas {
		if(result.hasErrors()) {
			message = "";
			result.getFieldErrors().forEach(e -> {
				message += messageSource.getMessage("erreur.datalib", new Object[]
				{e.getField(), e.getDefaultMessage()}, Locale.getDefault());
			});
			throw new ErreurRepas(message);
		}
		return sr.save(repas);
	}

	@ApiOperation(value = "Update repas by id", notes = "Returns a Repas updated")
	@PutMapping("{id}")
	public Repas update(@RequestBody Repas repas, @PathVariable("id") @ApiParam(name = "id", value = "Repas id", example ="1") Integer pid) throws ErreurRepas {
		verifRepas(pid);
		if(pid != repas.getId()) {
			throw new ErreurRepas(messageSource.getMessage("erreur.personne.notequals", new Object[]
			{pid, repas.getId()}, Locale.getDefault()));
		}
		return sr.save(repas);
	}

	@ApiOperation(value = "Delete a repas")
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") @ApiParam(name = "id", value = "Repas id", example ="1") Integer pid) throws ErreurRepas {
		verifRepas(pid);
		sr.deleteById(pid);
	}
}
