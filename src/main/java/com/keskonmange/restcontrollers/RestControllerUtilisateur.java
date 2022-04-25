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
import com.keskonmange.exceptions.ErreurUtilisateur;
import com.keskonmange.repository.JpaPersonne;
import com.keskonmange.security.jwt.JwtUtils;
import com.keskonmange.security.payload.LoginRequest;
import com.keskonmange.security.response.JwtResponse;
import com.keskonmange.security.services.UserDetailsImpl;
import com.keskonmange.services.ServiceUtilisateur;

@RestController
@CrossOrigin
@RequestMapping("api/utilisateurs")
public class RestControllerUtilisateur {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JpaPersonne userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	ServiceUtilisateur su;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Méthode qui permet de vérifier si l'utilisateur existe en base de données.
	 * 
	 * @param pid
	 * @throws ErreurUtilisateur
	 */
	private void verifUtilisateur(Integer pid) throws ErreurUtilisateur {
		if (su.findById(pid).isEmpty()) {
			throw new ErreurUtilisateur(
					messageSource.getMessage("erreur.utilisateur.notfound", new Object[] { pid }, Locale.getDefault()));
		}
	}

	/**
	 * Méthode qui permet de vérifier si l'email existe en base de données.
	 * 
	 * @param email
	 * @throws ErreurUtilisateur
	 */
	private void verifEmail(String email) throws ErreurUtilisateur {
		if (!su.findByEmail(email).isEmpty()) {
			throw new ErreurUtilisateur(messageSource.getMessage("erreur.utilisateur.email.found",
					new Object[] { email }, Locale.getDefault()));
		}
	}

	@GetMapping("/all")
	public Iterable<Personne> getAll() {
		return su.findAll();
	}

	@GetMapping("{id}")
	public Optional<Personne> getOne(@PathVariable("id") Integer pid) throws ErreurUtilisateur {
		verifUtilisateur(pid);
		return su.findById(pid);
	}

	/**
	 * Path pour l'enregistrement d'un utilisateur.
	 * 
	 * @param user
	 * @return
	 * @throws ErreurUtilisateur
	 */
	@PostMapping("/signin")
	public Personne registerUser(@Valid @RequestBody Personne user) throws ErreurUtilisateur {
		verifEmail(user.getEmail());
		user.setRole(Role.USER);
		user.setPwd(encoder.encode(user.getPwd()));
		return su.save(user);
	}

	/**
	 * Path pour le login et la génération du JWT.
	 * 
	 * @param user
	 * @return
	 * @throws ErreurUtilisateur
	 * @throws Exception
	 */
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest user) throws ErreurUtilisateur {
		Authentication authentication = null;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPwd()));

		} catch (Exception e) {
			throw new ErreurUtilisateur(
					messageSource.getMessage("erreur.utilisateur.connectKO", null, Locale.getDefault()));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));

	}

	/**
	 * Request qui permet de tester si le token est toujours valide.
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/connected")
	public boolean isJwtValid(@RequestHeader(value = "Authorization") String token) {
		if (token.startsWith("Bearer ")) {
			String tokenCut = token.substring(7, token.length());
//		String userNameToken = jwtUtils.getUserNameFromJwtToken(tokenCut);
			Boolean respToken = jwtUtils.validateJwtToken(tokenCut);
			return respToken;
		}
		return false;

	}

	@PutMapping("{id}")
	public Personne update(@RequestBody Personne utilisateur, @PathVariable("id") Integer pid)
			throws ErreurUtilisateur {
		verifUtilisateur(pid);
		if (pid != utilisateur.getId()) {
			throw new ErreurUtilisateur(messageSource.getMessage("erreur.utilisateur.notequals",
					new Object[] { pid, utilisateur.getId() }, Locale.getDefault()));
		}
		return su.save(utilisateur);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer pid) throws ErreurUtilisateur {
		verifUtilisateur(pid);
		// TODO : Vérifier les suppressions des tables relationnelles
		su.deleteById(pid);
	}
}
