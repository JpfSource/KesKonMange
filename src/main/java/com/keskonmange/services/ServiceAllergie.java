package com.keskonmange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keskonmange.entities.Allergie;
import com.keskonmange.repository.JpaAllergie;

@Service
public class ServiceAllergie {

	@Autowired
	JpaAllergie jallergie;
	
	public Optional<Allergie> findById(Integer pid){
		return jallergie.findById(pid);
	}

	public Iterable<Allergie> findAll(){
		return jallergie.findAll();
	}
	
	public Allergie save(Allergie allergie){
		return jallergie.save(allergie);
	}

	public void deleteById(Integer pid){
		jallergie.deleteById(pid);
	}
}
