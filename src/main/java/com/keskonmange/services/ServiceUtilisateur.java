package com.keskonmange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.keskonmange.entities.Utilisateur;
import com.keskonmange.repository.JpaUtilisateur;

public class ServiceUtilisateur {

	@Autowired
	JpaUtilisateur ju;

	public Optional<Utilisateur> findById(Integer pid){
		return ju.findById(pid);
	}

	public Iterable<Utilisateur> findAll(){
		return ju.findAll();
	}
	
	public Utilisateur save(Utilisateur utlisateur){
		return ju.save(utlisateur);
	}

	public void deleteById(Integer pid){
		ju.deleteById(pid);
	}


}
