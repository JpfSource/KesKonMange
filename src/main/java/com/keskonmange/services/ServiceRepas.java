package com.keskonmange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keskonmange.entities.Repas;
import com.keskonmange.repository.JpaRepas;

@Service
public class ServiceRepas
{
	@Autowired
	JpaRepas jr;

	public Optional<Repas> findById(Integer pid)
	{
		return jr.findById(pid);
	}

	public Iterable<Repas> findAll()
	{
		return jr.findAll();
	}

	public Repas save(Repas repas)
	{
		return jr.save(repas);
	}

	public void deleteById(Integer pid)
	{
		// TODO
		// => inclus Groupe
		// => inclus Plat		
		jr.deleteById(pid);
	}
}
