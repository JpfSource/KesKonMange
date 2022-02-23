package com.keskonmange.restcontrollers;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.keskonmange.enums.Role;
import com.keskonmange.exceptions.ErreurUtilisateur;
import com.keskonmange.repository.JpaUtilisateur;
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
	JpaUtilisateur userRepository;

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
	 * @param pid
	 * @throws ErreurUtilisateur
	 */
	private void verifUtilisateur(Integer pid) throws ErreurUtilisateur {
		if (su.findById(pid).isEmpty()) {
			throw new ErreurUtilisateur(
					messageSource.getMessage("erreur.utilisateur.notfound", new Object[] { pid }, Locale.getDefault()));
		}
	}

	private void verifEmail(String email) throws ErreurUtilisateur {
		if (!su.findByEmail(email).isEmpty()) {
			throw new ErreurUtilisateur(messageSource.getMessage("erreur.utilisateur.email.found",
					new Object[] { email }, Locale.getDefault()));
		}
	}

	@GetMapping
	public Iterable<Utilisateur> getAll() {
		return su.findAll();
	}

	@GetMapping("{id}")
	public Optional<Utilisateur> getOne(@PathVariable("id") Integer pid) throws ErreurUtilisateur {
		verifUtilisateur(pid);
		return su.findById(pid);
	}


	/**
	 * @param user
	 * @return
	 * @throws ErreurUtilisateur
	 */
	@PostMapping("/signin")
	public Utilisateur registerUser(@Valid @RequestBody Utilisateur user) throws ErreurUtilisateur {
		verifEmail(user.getEmail());
//		user.setRole(Role.USER);
		user.setPwd(encoder.encode(user.getPwd()));
		return su.save(user);
	}

	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest user) {
		System.out.println(user.getEmail() +" " +"pwd: " + user.getPwd());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPwd()));
		


		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateJwtToken(authentication);
		System.out.println();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 roles));

	}
	
//	@PostMapping("/login")
//  public Utilisateur loginUser(@RequestBody Utilisateur user) throws ErreurUtilisateur {
//		Utilisateur userReturn = null;
//		
//		if (su.findByEmail(user.getEmail()).isPresent()) {
//			Utilisateur userDb = su.findByEmail(user.getEmail()).get();
//			
//			if(encoder.matches(user.getPwd(), userDb.getPwd())) {
//				userReturn = userDb;
//			}
//			else {
//				throw new ErreurUtilisateur(messageSource.getMessage("erreur.utilisateur.pwdko", new Object[]{user.getEmail()}, Locale.getDefault()));
//
//			}
//		}
//		else {
//			throw new ErreurUtilisateur(messageSource.getMessage("erreur.utilisateur.email.notfound", new Object[]{user.getEmail()}, Locale.getDefault()));
//		}
//		return userReturn;
//  }	
	

	@PutMapping("{id}")
	public Utilisateur update(@RequestBody Utilisateur utilisateur, @PathVariable("id") Integer pid)
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
