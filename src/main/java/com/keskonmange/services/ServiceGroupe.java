package com.keskonmange.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import com.keskonmange.entities.Groupe;
import com.keskonmange.entities.Utilisateur;
import com.keskonmange.exceptions.ErreurGroupe;
import com.keskonmange.repository.JpaGroupe;
import com.keskonmange.repository.JpaUtilisateur;

@Controller
public class ServiceGroupe {
	
	@Autowired
	JpaGroupe jpaGpe;
	
	@Autowired
	JpaUtilisateur jpaUser;
	
	public Iterable<Groupe> findAll(){
		//TODO : lier les resultats à tous les groupes de l'utilisateur
		
		return jpaGpe.findAll();
	}
	
	public Iterable<Groupe> findByUtilisateurId(Integer pid){
		//TODO : lier les resultats à tous les groupes de l'utilisateur
		Optional<Utilisateur> user = jpaUser.findById(pid);

		return jpaGpe.findByUtilisateurId(user.get());
	}
	
	public Groupe save(Groupe groupe) {
		//TODO: prendre en compte l'id de l'utilisateur
		return jpaGpe.save(groupe);
	}

	public Optional<Groupe> findGroupeByNom(String nom) {

		return jpaGpe.findGroupeByNom(nom);
	}

	public Optional<Groupe> findById(Integer pid) {
		// TODO Auto-generated method stub
		return jpaGpe.findById(pid);
	}

	public void deleteById(Integer pid) {
		
		Groupe gpeToDelete = jpaGpe.findById(pid).get(); 
		gpeToDelete.getAdministrateurs().clear();
		gpeToDelete.getGroupePersonnes().clear();
		jpaGpe.save(gpeToDelete);
		
		jpaGpe.deleteById(pid);
	}

}
