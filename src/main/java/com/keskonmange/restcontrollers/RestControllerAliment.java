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

import com.keskonmange.entities.Aliment;
import com.keskonmange.exceptions.ErreurAliment;
import com.keskonmange.services.ServiceAliment;
import com.keskonmange.services.ServiceAllergie;

@RestController
@CrossOrigin
@RequestMapping("api/Aliment")
public class RestControllerAliment {

	private String message;
	
	@Autowired
	ServiceAliment sa;
	
	@Autowired
	ServiceAllergie sal;

	
	@Autowired
    private MessageSource messageSource;	

	private void verifAliment(Integer pid) throws ErreurAliment {
		if(sa.findById(pid).isEmpty()){
			throw new ErreurAliment(messageSource.getMessage("erreur.aliment.notfound", new Object[]{pid}, Locale.getDefault()));
		}
	}
		
	@GetMapping
	public Iterable<Aliment> getAll(){
		return sa.findAll();
	}

	@GetMapping("{id}")
	public Optional<Aliment> getOne(@PathVariable("id") Integer pid) throws ErreurAliment{
		verifAliment(pid);
		return sa.findById(pid);
	}
	
	@PostMapping
	public Aliment create(@Valid @RequestBody Aliment aliment, BindingResult result) throws ErreurAliment{
		if(result.hasErrors()) {
			message = "";
			result.getFieldErrors().forEach(e -> {
				message += messageSource.getMessage("erreur.datalib", new Object[]{e.getField(), e.getDefaultMessage()}, Locale.getDefault());
			});
			throw new ErreurAliment(message);
		}
		return sa.save(aliment);
	}

	@PutMapping("{id}")
	public Aliment update(@RequestBody Aliment aliment, @PathVariable ("id") Integer pid) throws ErreurAliment{
		verifAliment(pid);
		if(pid != aliment.getId()) {
			throw new ErreurAliment(messageSource.getMessage("erreur.aliment.notequals", new Object[]{pid, aliment.getId()}, Locale.getDefault()));
		}
		return sa.save(aliment);
	}

	//@SuppressWarnings("unlikely-arg-type")
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer pid) throws ErreurAliment{
		verifAliment(pid);
		
		Aliment aliment = sa.findById(pid).get();
		sa.getAllergieByAliment(pid).forEach(al->{
			al.getAliments().remove(aliment);
			sal.save(al);
		});
	
		
		sa.deleteById(pid);
	}	
}
