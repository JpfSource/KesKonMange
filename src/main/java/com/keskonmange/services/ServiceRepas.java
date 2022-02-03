package com.keskonmange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.keskonmange.entities.Repas;
import com.keskonmange.repository.JpaRepas;

@Controller
public class ServiceRepas
{
	@Autowired
	JpaRepas jpr;

	public Optional<Repas> findById(Integer pid)
	{
		return jpr.findById(pid);
	}

	public Iterable<Repas> findAll()
	{
		return jpr.findAll();
	}

	public Repas save(Repas repas)
	{
		return jpr.save(repas);
	}

	public void deleteById(Integer pid)
	{
		jpr.deleteById(pid);
	}
}
