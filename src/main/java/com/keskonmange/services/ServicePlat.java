package com.keskonmange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keskonmange.entities.Plat;
import com.keskonmange.entities.Repas;
import com.keskonmange.repository.JpaPlat;

@Service
public class ServicePlat
{
	@Autowired
	JpaPlat jp;

	public Optional<Plat> findById(Integer pid){
		return jp.findById(pid);
	}

	public Iterable<Plat> findAll(){
		return jp.findAll();
	}
	
	public Iterable<Plat> getAllPlatsCreatedByUser(Integer id){
		return jp.getAllPlatsCreatedByUser(id);
	}

	public Plat save(Plat plat){
		return jp.save(plat);
	}

	public void deleteById(Integer pid){
		// TODO
		// => inclus Personne
		// => inclus Aliment
		// => * Repas -> Repas.plat
		jp.deleteById(pid);
	}
	
	public Iterable<Repas> getRepas(Integer id){
		return jp.getRepas(id);
	}
}
