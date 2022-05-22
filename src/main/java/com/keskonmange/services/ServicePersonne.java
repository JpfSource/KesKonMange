package com.keskonmange.services;

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
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.keskonmange.entities.Absence;
import com.keskonmange.entities.Groupe;
import com.keskonmange.entities.Personne;
import com.keskonmange.entities.Plat;
import com.keskonmange.enums.BesoinEnergetiqueMineur;
import com.keskonmange.enums.Role;
import com.keskonmange.exceptions.ErreurPersonne;
import com.keskonmange.repository.JpaPersonne;
import com.keskonmange.security.jwt.JwtUtils;
import com.keskonmange.security.payload.LoginRequest;
import com.keskonmange.security.response.JwtResponse;
import com.keskonmange.security.services.UserDetailsImpl;
import com.keskonmange.utils.UtilDate;

@Service
public class ServicePersonne {

	private String message;
	
	@Autowired
	JpaPersonne jp;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	PasswordEncoder encoder;

	/**
	 * Renvoie un boolean pour s'assurer que le calcul sur les données d'une personne est possible.
	 * @param personne
	 * @return
	 */
	private static Boolean isPersonneOkForCalcul(Personne personne) {
		return personne != null
				&& personne.getDateNaissance() != null
				&& personne.getPoids() != null
				&& personne.getGenre() != null
				&& personne.getTaille() != null
				&& personne.getActivite() != null
				&& personne.getObjectifCalorique() != null;
	}
	
	/**
	 * Vérifie si la personne dont l'identifiant est passé en paramètre existe en BDD.
	 * @param pid
	 * @throws ErreurPersonne
	 */
	private void verifPersonne(Integer pid) throws ErreurPersonne {
		if(jp.findById(pid).isEmpty()){
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.notfound", new Object[]
			{pid}, Locale.getDefault()));
		}
	}
	
	/**
	 * Vérifie si l'email passé en paramètre existe déjà en BDD.
	 * @param email
	 * @throws ErreurPersonne
	 */
	private void verifEmail(String email) throws ErreurPersonne {
		if (!jp.getPersonneByEmail(email).isEmpty()) {
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.email.found",
					new Object[] { email }, Locale.getDefault()));
		}
	}

	/**
	 * Renvoie le besoin calorique d'une personne en fonction des données saisies.
	 * @param personne
	 * @return
	 */
	public static Integer calculBesoinsCaloriques(Personne personne){
		if (isPersonneOkForCalcul(personne)) {
			Integer age = UtilDate.calculAge(personne.getDateNaissance());

			Double resultatBrut;
			if(age < 18) {
				resultatBrut = Double.valueOf(personne.getPoids() * BesoinEnergetiqueMineur.of(personne.getGenre(), age).getBesoinEnergetique());
			}
			else {
				resultatBrut = Double.valueOf((personne.getPoids() * personne.getGenre().getCoeffPoids())
						+ (personne.getTaille() * personne.getGenre().getCoeffTaille())
						- (age * personne.getGenre().getCoeffAge())
						+ personne.getGenre().getComplement());
			}
			Double resultatNet = resultatBrut * personne.getActivite().getCoefficient();
			
			Double ratio = (double)personne.getObjectifCalorique();
			ratio = ratio / 100;
			Double besoinsCaloriques = resultatNet * ratio;
			Long r2 = Long.valueOf(Math.round(besoinsCaloriques));
			Integer result = Integer.valueOf(r2.toString()); 
					
			return result;
		}
		else {
			return 0;
		}
	}

	/**
	 * SET de l'attribut BesoinsCaloriques d'une personne.
	 * @param personne
	 */
	public static void setBesoinsCaloriques(Personne personne){
		personne.setBesoinsCaloriques(calculBesoinsCaloriques(personne));
	}

	/**
	 * SET de l'attribut BesoinsCaloriques d'une collection de personnes.
	 * @param personnes
	 * @return
	 */
	private static Iterable<Personne> setBesoinsCaloriques(Iterable<Personne> personnes){
		for(Personne personne : personnes) {
			setBesoinsCaloriques(personne);
		}
		return personnes;
	}
	
	private static Optional<Personne> setBesoinsCaloriques(Optional<Personne> personne){
		if(personne != null && personne.isPresent()) {
			setBesoinsCaloriques(personne.get());
		}
		return personne;
	}
	
	/**
	 * Renvoie la personne dont l'identifiant est passé en paramètre.
	 * @param pid
	 * @return
	 */
	public Optional<Personne> findById(Integer pid) throws ErreurPersonne {
		verifPersonne(pid);
		return setBesoinsCaloriques(jp.findById(pid));
	}
	
	/**
	 * Renvoie toutes les personnes créées par un utilisateur dont l'identifiant est passé en paramètre.
	 * @param id
	 * @return
	 */
	public Iterable<Personne> getAllPersonsCreatedByUser(Integer id){
		return setBesoinsCaloriques(jp.getAllPersonsCreatedByUser(id));
	}

	/**
	 * Renvoie toutes les personnes de la BDD.
	 * @return
	 */
	public Iterable<Personne> findAll(){
		return setBesoinsCaloriques(jp.findAll());
	}

	/**
	 * Sauvegarde un utilisateur en base de données.
	 * @param personne
	 * @return
	 */
	public Personne save(Personne personne){
		if(personne.getObjectifCalorique() == null) {personne.setObjectifCalorique(100);}
		if(personne.getUrlPhoto() == null) {personne.setUrlPhoto("https://icon-library.com/images/no-profile-picture-icon/no-profile-picture-icon-18.jpg");}
		personne = jp.save(personne);
		setBesoinsCaloriques(personne);
		return personne;
	}
	
	/**
	 * Sauvegarde une personne créée par un utilisateur dont l'identifiant est passé en paramètre.
	 * @param personne
	 * @param idCreateur
	 * @param result 
	 * @return
	 * @throws ErreurPersonne 
	 */
	public Personne savePersonCreatedByUser(Personne personne, Integer idCreateur, BindingResult result) throws ErreurPersonne{
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
		Optional<Personne> creator = jp.findById(idCreateur);
		if(personne.getUrlPhoto() == null) {personne.setUrlPhoto("https://icon-library.com/images/no-profile-picture-icon/no-profile-picture-icon-18.jpg");}
		personne.setCreateur(creator.get());
		personne = jp.save(personne);
		setBesoinsCaloriques(personne);
		return personne;
	}

	/**
	 * Supprime de la BDD, la personne dont l'identifiant est passé en paramètre.
	 * @param pid
	 * @throws ErreurPersonne 
	 */
	public void deleteById(Integer pid) throws ErreurPersonne
	{
		verifPersonne(pid);
		// TODO
		// => inclus createur
		// => inclus Allergie
		// => * Absence -> @Query
		// => * Groupe -> Groupe.groupePersonnes
		// => * Plat -> Plat.createur
		jp.deleteById(pid);
	}
	
	/**
	 * Renvoie la personne dont l'email est passé en paramètre.
	 * @param email
	 * @return
	 */
	public Optional<Personne> getPersonneByEmail(String email){
		return jp.getPersonneByEmail(email);
	}
	
	/**
	 * Renvoie la liste des absences de la personne dont l'identifiant est passé en paramètre.
	 * @param id
	 * @return
	 */
	public Iterable<Absence> getAbsences(Integer id){
		return jp.getAbsences(id);
	}

	/**
	 * Renvoie la liste des groupes de la personne dont l'identifiant est passé en paramètre.
	 * @param id
	 * @return
	 */
	public Iterable<Groupe> getGroupesByPersonne(Integer id){
		return jp.getGroupesByPersonne(id);
	}

	/**
	 * Renvoie la liste des plats de la personne dont l'identifiant est passé en paramètre.
	 * @param id
	 * @return
	 */
	public Iterable<Plat> getPlats(Integer id){
		return jp.getPlats(id);
	}

	/**
	 * Renvoie le JWT ainsi que l'identifiant, le nom d'utilisateur et les rôles lors de la connexion d'un utlisateur.
	 * @param user
	 * @return
	 * @throws ErreurPersonne
	 */
	public ResponseEntity<?> authenticateUser(LoginRequest user) throws ErreurPersonne {
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

	/**
	 * Renvoie un boolean sur la validité du token.
	 * @param token
	 * @return
	 */
	public boolean isJwtValid(String token) {
		if (token.startsWith("Bearer ")) {
			String tokenCut = token.substring(7, token.length());
			return jwtUtils.validateJwtToken(tokenCut);
		}
		return false;
	}

	/**
	 * Renvoie la personne nouvellement enregistrée en BDD.
	 * @param user
	 * @return
	 * @throws ErreurPersonne
	 */
	public Personne registerUser(@Valid Personne user) throws ErreurPersonne {
		verifEmail(user.getEmail());
		user.setRole(Role.USER);
		user.setPwd(encoder.encode(user.getPwd()));
		return save(user);
	}

	public Personne updatePassword(Personne user) throws ErreurPersonne  {
		Optional<Personne> personToUpdate = jp.getPersonneByEmail(user.getEmail());
		if(personToUpdate.isPresent()) {
			personToUpdate.get().setPwd(encoder.encode(user.getPwd()));
			return save(personToUpdate.get());
		}
		else {
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.connectKO", null, Locale.getDefault()));
		}
	}

	/**
	 * Renvoie la personne avec ses données actualisées.
	 * @param personne
	 * @param pid
	 * @return
	 * @throws ErreurPersonne
	 */
	public Personne updateDataPersonne(Personne personne, Integer pid) throws ErreurPersonne {
		verifPersonne(pid);
		if(pid != personne.getId()){
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.notequals", new Object[]{pid, personne.getId()}, Locale.getDefault()));
		}
		return save(personne);
	}
	
	public Iterable<Personne> getAllPersonsCreatedByUser(Integer id){
		return jp.getAllPersonsCreatedByUser(id);
	}

}
