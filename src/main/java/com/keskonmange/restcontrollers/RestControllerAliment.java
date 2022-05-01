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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "CRUD Rest APIs for Aliment entity")
@RestController
@CrossOrigin
@RequestMapping("api/aliments")
public class RestControllerAliment {

	private String message;
	
	@Autowired
	ServiceAliment sa;
	
	@Autowired
    private MessageSource messageSource;	

	private void verifAliment(Integer pid) throws ErreurAliment {
		if(sa.findById(pid).isEmpty()){
			throw new ErreurAliment(messageSource.getMessage("erreur.aliment.notfound", new Object[]{pid}, Locale.getDefault()));
		}
	}
	
	@ApiOperation(value = "Get all aliment", notes = "Returns a collection of Aliment")
	@GetMapping
	public Iterable<Aliment> getAll(){
		return sa.findAll();
	}

	@ApiOperation(value = "Get aliment by id", notes = "Returns an aliment as per the id")
	@GetMapping("{id}")
	public Optional<Aliment> getOne(@PathVariable("id") @ApiParam(name = "id", value = "Aliment id", example ="1") Integer pid) throws ErreurAliment{
		verifAliment(pid);
		return sa.findById(pid);
	}
	
	@ApiOperation(value = "Create an aliment", notes = "Returns a created aliment")
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

	@ApiOperation(value = "Update an aliment", notes = "Returns an aliment updated")
	@PutMapping("{id}")
	public Aliment update(@RequestBody Aliment aliment, @PathVariable ("id") @ApiParam(name = "id", value = "Aliment id", example ="1") Integer pid) throws ErreurAliment{
		verifAliment(pid);
		if(pid != aliment.getId()) {
			throw new ErreurAliment(messageSource.getMessage("erreur.aliment.notequals", new Object[]{pid, aliment.getId()}, Locale.getDefault()));
		}
		return sa.save(aliment);
	}

	@ApiOperation(value = "Delete aliment")
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") @ApiParam(name = "id", value = "Aliment id", example ="1") Integer pid) throws ErreurAliment{
		verifAliment(pid);
		sa.deleteById(pid);
	}	
}
