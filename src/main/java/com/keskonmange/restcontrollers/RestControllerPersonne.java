package com.keskonmange.restcontrollers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keskonmange.entities.Personne;
import com.keskonmange.exceptions.ErreurPersonne;
import com.keskonmange.security.payload.LoginRequest;
import com.keskonmange.services.ServicePersonne;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "CRUD Rest APIs for Personne entity")
@RestController
@CrossOrigin
@RequestMapping("api/personnes")
public class RestControllerPersonne
{	
	@Autowired
	ServicePersonne sp;

	@ApiOperation(value = "Get all person", notes = "Returns a collection of Personne")
	@GetMapping("all")
	public Iterable<Personne> getAll() {
		return sp.findAll();
	}

	@ApiOperation(value = "Get person by id", notes = "Returns a person as per the id")
	@GetMapping("{id}")
	public Optional<Personne> getOne(@PathVariable("id") @ApiParam(name = "id", value = "Person id", example ="1") Integer pid) throws ErreurPersonne {
		return sp.findById(pid);
	}
	
	@ApiOperation(value = "Get all persons created by an user", notes = "Returns a collection of Personne created by user per id")
	@GetMapping("{id}/all-personnes")
	public Iterable<Personne> getAllPersonsCreatedByUser(@PathVariable("id") @ApiParam(name = "id", value = "Person id", example ="1") Integer pid) {
		return sp.getAllPersonsCreatedByUser(pid);
	}
	
	@ApiOperation(value = "Check if a person is connected", notes = "Returns a boolean of checking connexion")
	@GetMapping("/connected")
	public boolean isJwtValid(@RequestHeader(value = "Authorization") String token) {
		return sp.isJwtValid(token);
	}

	@ApiOperation(value = "Create a person after user was connected", notes = "Returns a person created by a user")
	@PostMapping("{id}/create")
	public Personne create(@Valid @RequestBody
	Personne personne, @PathVariable("id") @ApiParam(name = "id", value = "Person id", example ="1") Integer idCreateur, BindingResult result) throws ErreurPersonne {
		return sp.savePersonCreatedByUser(personne, idCreateur, result);
	}

	@ApiOperation(value = "Register a person as an user", notes = "Returns a person registed")
	@PostMapping("/signin")
	public Personne registerUser(@Valid @RequestBody Personne user) throws ErreurPersonne {
		return sp.registerUser(user);
	}
	
	@ApiOperation(value = "Log a user on the app", notes = "Returns a JWT of logged person (user)")
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest user) throws ErreurPersonne {
		return sp.authenticateUser(user);
	}
	
	@ApiOperation(value = "Update an user's password", notes = "Returns person updated")
	@PutMapping("/resetPassword")
	public Personne updatePassword(@RequestBody Personne user) throws ErreurPersonne {
		return sp.updatePassword(user);
	}
	
	@ApiOperation(value = "Update a data's person", notes = "Returns person updated")
	@PutMapping("{id}")
	public Personne update(@RequestBody Personne personne, @PathVariable("id") @ApiParam(name = "id", value = "Person id", example = "1") Integer pid) throws ErreurPersonne {
		return sp.updateDataPersonne(personne,pid);
	}
	
	@ApiOperation(value = "Calculate a person's calorie requirement ", notes = "Returns a calorie requirement as an integer")
	@PutMapping("/recalcul")
	public Integer recalcul(@RequestBody Personne personne) throws ErreurPersonne {
		return ServicePersonne.calculBesoinsCaloriques(personne);
	}

	@ApiOperation(value = "Delete a person")
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") @ApiParam(name = "id", value = "Person id", example = "1") Integer pid) throws ErreurPersonne {
		sp.deleteById(pid);
	}
}
