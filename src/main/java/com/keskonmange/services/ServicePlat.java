package com.keskonmange.services;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.keskonmange.entities.Plat;
import com.keskonmange.entities.Repas;
import com.keskonmange.exceptions.ErreurPlat;
import com.keskonmange.repository.JpaPlat;

@Service
public class ServicePlat
{
	private String message;
	
	@Autowired
	JpaPlat jp;
	
	@Autowired
	private MessageSource messageSource;
	
	private void verifPlat(Integer pid) throws ErreurPlat {
		if(jp.findById(pid).isEmpty()) {
			throw new ErreurPlat(messageSource.getMessage("erreur.plat.notfound", new Object[]{pid}, Locale.getDefault()));
		}
	}

	public Optional<Plat> findById(Integer pid) throws ErreurPlat {
		verifPlat(pid);
		return jp.findById(pid);
	}

	public Iterable<Plat> findAll(){
		return jp.findAll();
	}
	
	public Iterable<Plat> getAllPlatsCreatedByUser(Integer id){
		return jp.getAllPlatsCreatedByUser(id);
	}

	public Plat createPlat(Plat plat, BindingResult result) throws ErreurPlat{
		if(result.hasErrors())
		{
			message = "";
			result.getFieldErrors().forEach(e -> {
				message += messageSource.getMessage("erreur.datalib", new Object[]
				{e.getField(), e.getDefaultMessage()}, Locale.getDefault());
			});
			throw new ErreurPlat(message);
		}
		return jp.save(plat);
	}
	

	public void deleteById(Integer pid) throws ErreurPlat{
		// TODO
		// => inclus Personne
		// => inclus Aliment
		// => * Repas -> Repas.plat
		verifPlat(pid);
		jp.deleteById(pid);
	}
	
	public Iterable<Repas> getRepas(Integer id){
		return jp.getRepas(id);
	}

	public Plat updatePlat(Plat plat, Integer pid) throws ErreurPlat {
		verifPlat(pid);
		if(pid != plat.getId()) {
			throw new ErreurPlat(messageSource.getMessage("erreur.personne.notequals", new Object[]
			{pid, plat.getId()}, Locale.getDefault()));
		}
		return jp.save(plat);
	}
}
