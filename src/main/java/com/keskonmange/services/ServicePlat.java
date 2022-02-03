package com.keskonmange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keskonmange.entities.Plat;
import com.keskonmange.repository.JpaPlat;

@Service
public class ServicePlat
{
	@Autowired
	JpaPlat jpl;

	public Optional<Plat> findById(Integer pid)
	{
		return jpl.findById(pid);
	}

	public Iterable<Plat> findAll()
	{
		return jpl.findAll();
	}

	public Plat save(Plat plat)
	{
		return jpl.save(plat);
	}

	public void deleteById(Integer pid)
	{
		jpl.deleteById(pid);
	}
}
