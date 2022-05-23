package com.keskonmange.restcontrollers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	ServicePlat sp;
	
	@ApiOperation(value = "Get all plats", notes = "Returns a collection of plat")
	@GetMapping
	public Iterable<Plat> getAll() {
		return sp.findAll();
	}

	@ApiOperation(value = "Get plat by id", notes = "Returns a plat as per the id")
	@GetMapping("{id}")
	public Optional<Plat> getOne(@PathVariable("id") @ApiParam(name = "id", value = "Plat id", example = "1") Integer pid) throws ErreurPlat {		
		return sp.findById(pid);
	}
	
	@ApiOperation(value = "Get all persons created by an user", notes = "Returns a collection of Personne created by user per id")
	@GetMapping("{id}/all-plats")
	public Iterable<Plat> getAllPlatsCreatedByUser(@PathVariable("id") @ApiParam(name = "id", value = "Person id", example ="1") Integer pid) {
		return sp.getAllPlatsCreatedByUser(pid);
	}

	@ApiOperation(value = "Create a plat", notes = "Returns a plat created")
	@PostMapping
	public Plat create(@Valid @RequestBody Plat plat, BindingResult result) throws ErreurPlat {
		return sp.createPlat(plat, result);
	}

	@ApiOperation(value = "Update a plat by id", notes = "Returns a plat updated")
	@PutMapping("{id}")
	public Plat update(@RequestBody Plat plat, @PathVariable("id") @ApiParam(name = "id", value = "Plat id", example = "1") Integer pid) throws ErreurPlat {
		return sp.updatePlat(plat, pid);
	}

	@ApiOperation(value = "Delete a plat")
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") @ApiParam(name = "id", value = "Plat id", example = "1") Integer pid) throws ErreurPlat {
		sp.deleteById(pid);
	}
}
