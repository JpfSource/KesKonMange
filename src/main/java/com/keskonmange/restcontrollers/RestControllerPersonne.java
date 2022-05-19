package com.keskonmange.restcontrollers;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.keskonmange.enums.Role;
import com.keskonmange.exceptions.ErreurPersonne;
import com.keskonmange.security.jwt.JwtUtils;
import com.keskonmange.security.payload.LoginRequest;
import com.keskonmange.security.response.JwtResponse;
import com.keskonmange.security.services.UserDetailsImpl;
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
	private String message;

	@Autowired
	ServicePersonne sp;

	@Autowired
	private MessageSource messageSource;	
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;

	private void verifPersonne(Integer pid) throws ErreurPersonne {
		if(sp.findById(pid).isEmpty()){
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.notfound", new Object[]
			{pid}, Locale.getDefault()));
		}
	}
	
	private void verifEmail(String email) throws ErreurPersonne {
		if (!sp.getPersonneByEmail(email).isEmpty()) {
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.email.found",
					new Object[] { email }, Locale.getDefault()));
		}
	}

	@ApiOperation(value = "Get all person", notes = "Returns a collection of Personne")
	@GetMapping("all")
	public Iterable<Personne> getAll() {
		return sp.findAll();
	}

	@ApiOperation(value = "Get person by id", notes = "Returns a person as per the id")
	@GetMapping("{id}")
	public Optional<Personne> getOne(@PathVariable("id") @ApiParam(name = "id", value = "Person id", example ="1") Integer pid) throws ErreurPersonne {
		verifPersonne(pid);
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
		if (token.startsWith("Bearer ")) {
			String tokenCut = token.substring(7, token.length());
			return jwtUtils.validateJwtToken(tokenCut);
		}
		return false;
	}

	@ApiOperation(value = "Create a person after user was connected", notes = "Returns a person created by a user")
	@PostMapping("{id}/create")
	public Personne create(@Valid @RequestBody
	Personne personne, @PathVariable("id") @ApiParam(name = "id", value = "Person id", example ="1") Integer idCreateur, BindingResult result) throws ErreurPersonne {
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
		return sp.savePersonCreatedByUser(personne, idCreateur);
	}

	@ApiOperation(value = "Register a person as an user", notes = "Returns a person registed")
	@PostMapping("/signin")
	public Personne registerUser(@Valid @RequestBody Personne user) throws ErreurPersonne {
		verifEmail(user.getEmail());
		user.setRole(Role.USER);
		user.setPwd(encoder.encode(user.getPwd()));
		return sp.save(user);
	}
	
	@ApiOperation(value = "Log a user on the app", notes = "Returns a JWT of logged person (user)")
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest user) throws ErreurPersonne {
		Authentication authentication = null;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPwd()));

		} catch (Exception e) {
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.connectKO", null, Locale.getDefault()));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
	}
	
	@ApiOperation(value = "Update an user's password", notes = "Returns person updated")
	@PutMapping("/resetPassword")
	public Personne updatePassword(@RequestBody Personne user) throws ErreurPersonne {
		Optional<Personne> personToUpdate = sp.getPersonneByEmail(user.getEmail());
		if(personToUpdate.isPresent()) {
			personToUpdate.get().setPwd(encoder.encode(user.getPwd()));
			return sp.save(personToUpdate.get());
		}
		else {
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.connectKO", null, Locale.getDefault()));
		}

	}
	
	@ApiOperation(value = "Update a data's person", notes = "Returns person updated")
	@PutMapping("{id}")
	public Personne update(@RequestBody Personne personne, @PathVariable("id") @ApiParam(name = "id", value = "Person id", example = "1") Integer pid) throws ErreurPersonne {
		verifPersonne(pid);
		if(pid != personne.getId()){
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.notequals", new Object[]{pid, personne.getId()}, Locale.getDefault()));
		}
		return sp.save(personne);
	}
	
	@ApiOperation(value = "Calculate a person's calorie requirement ", notes = "Returns a calorie requirement as an integer")
	@PutMapping("/recalcul")
	public Integer recalcul(@RequestBody Personne personne) throws ErreurPersonne {
		return ServicePersonne.calculBesoinsCaloriques(personne);
	}

	@ApiOperation(value = "Delete a person")
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") @ApiParam(name = "id", value = "Person id", example = "1") Integer pid) throws ErreurPersonne {
		verifPersonne(pid);
		sp.deleteById(pid);
	}
}
