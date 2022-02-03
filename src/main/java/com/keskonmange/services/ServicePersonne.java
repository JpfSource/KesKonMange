package com.keskonmange.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

import com.keskonmange.entities.Personne;
import com.keskonmange.repository.JpaPersonne;

@Service
public class ServicePersonne {

	@Autowired
	JpaPersonne jp;

	public Optional<Personne> findById(Integer pid){
		return jp.findById(pid);
	}

	public Iterable<Personne> findAll(){
		return jp.findAll();
	}
	
	public Personne save(Personne personne){
		return jp.save(personne);
	}

	public void deleteById(Integer pid){
		jp.deleteById(pid);
	}
}
