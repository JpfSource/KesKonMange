package com.keskonmange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keskonmange.entities.Utilisateur;
import com.keskonmange.repository.JpaUtilisateur;

@Service
public class ServiceUtilisateur {

	@Autowired
	JpaUtilisateur ju;



	public Optional<Utilisateur> findById(Integer pid){
		return ju.findById(pid);
	}

	public Optional<Utilisateur> findByEmail(String email){
		return ju.findByEmail(email);
	}

	
	public Iterable<Utilisateur> findAll(){
		return ju.findAll();
	}

	public Utilisateur save(Utilisateur utilisateur){
		return ju.save(utilisateur);
	}

	public void deleteById(Integer pid){
		ju.deleteById(pid);
	}
	
	


}
