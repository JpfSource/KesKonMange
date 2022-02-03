package com.keskonmange.restcontrollers;

import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keskonmange.entities.Allergie;
import com.keskonmange.exceptions.ErreurAllergie;
import com.keskonmange.services.ServiceAllergie;

@RestController
@CrossOrigin
@RequestMapping("api/Allergie")
public class RestControllerAllergie {
	private String message;
	
	@Autowired
	ServiceAllergie sall;
	
	@Autowired
    private MessageSource messageSource;	

	private void verifAllergie(Integer pid) throws ErreurAllergie {
		if(sall.findById(pid).isEmpty()){
			throw new ErreurAllergie(messageSource.getMessage("erreur.allergie.notfound", new Object[]{pid}, Locale.getDefault()));
		}
	}
		
	@GetMapping
	public Iterable<Allergie> getAll(){
		return sall.findAll();
	}

	@GetMapping("{id}")
	public Optional<Allergie> getOne(@PathVariable("id") Integer pid) throws ErreurAllergie{
		verifAllergie(pid);
		return sall.findById(pid);
	}
	
	@PostMapping
	public Allergie create(@Valid @RequestBody Allergie allergie, BindingResult result) throws ErreurAllergie{
		if(result.hasErrors()) {
			message = "";
			result.getFieldErrors().forEach(e -> {
				message += messageSource.getMessage("erreur.datalib", new Object[]{e.getField(), e.getDefaultMessage()}, Locale.getDefault());
			});
			throw new ErreurAllergie(message);
		}
		return sall.save(allergie);
	}

	@PutMapping("{id}")
	public Allergie update(@RequestBody Allergie allergie, @PathVariable ("id") Integer pid) throws ErreurAllergie{
		verifAllergie(pid);
		if(pid != allergie.getId()) {
			throw new ErreurAllergie(messageSource.getMessage("erreur.allergie.notequals", new Object[]{pid, allergie.getId()}, Locale.getDefault()));
		}
		return sall.save(allergie);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer pid) throws ErreurAllergie{
		verifAllergie(pid);
		// TODO : Vérifier les suppressions des tables relationnelles
		sall.deleteById(pid);
	}	
}
