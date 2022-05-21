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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "CRUD Rest APIs for Plat entity")
@RestController
@CrossOrigin
@RequestMapping("api/plats")
public class RestControllerPlat
{
	private String message; 
	
	@Autowired
	ServicePlat sp;
	
	@Autowired
	private MessageSource messageSource;

	private void verifPlat(Integer pid) throws ErreurPlat {
		if(sp.findById(pid).isEmpty()) {
			throw new ErreurPlat(messageSource.getMessage("erreur.plat.notfound", new Object[]{pid}, Locale.getDefault()));
		}
	}

	@ApiOperation(value = "Get all plats", notes = "Returns a collection of plat")
	@GetMapping
	public Iterable<Plat> getAll() {
		return sp.findAll();
	}

	@ApiOperation(value = "Get plat by id", notes = "Returns a plat as per the id")
	@GetMapping("{id}")
	public Optional<Plat> getOne(@PathVariable("id") @ApiParam(name = "id", value = "Plat id", example = "1") Integer pid) throws ErreurPlat {
		verifPlat(pid);
		return sp.findById(pid);
	}
	
	@ApiOperation(value = "Get all persons created by an user", notes = "Returns a collection of Personne created by user per id")
	@GetMapping("{id}/all-plats")
	public Iterable<Plat> getAllPersonsCreatedByUser(@PathVariable("id") @ApiParam(name = "id", value = "Person id", example ="1") Integer pid) {
		return sp.getAllPlatsCreatedByUser(pid);
	}

	@ApiOperation(value = "Create a plat", notes = "Returns a plat created")
	@PostMapping
	public Plat create(@Valid @RequestBody Plat plat, BindingResult result) throws ErreurPlat {
		if(result.hasErrors())
		{
			message = "";
			result.getFieldErrors().forEach(e -> {
				message += messageSource.getMessage("erreur.datalib", new Object[]
				{e.getField(), e.getDefaultMessage()}, Locale.getDefault());
			});
			throw new ErreurPlat(message);
		}
		return sp.save(plat);
	}

	@ApiOperation(value = "Update a plat by id", notes = "Returns a plat updated")
	@PutMapping("{id}")
	public Plat update(@RequestBody Plat plat, @PathVariable("id") @ApiParam(name = "id", value = "Plat id", example = "1") Integer pid) throws ErreurPlat {
		verifPlat(pid);
		if(pid != plat.getId()) {
			throw new ErreurPlat(messageSource.getMessage("erreur.personne.notequals", new Object[]
			{pid, plat.getId()}, Locale.getDefault()));
		}
		return sp.save(plat);
	}

	@ApiOperation(value = "Delete a plat")
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") @ApiParam(name = "id", value = "Plat id", example = "1") Integer pid) throws ErreurPlat {
		verifPlat(pid);
		sp.deleteById(pid);
	}
}
