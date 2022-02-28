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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keskonmange.entities.Personne;
import com.keskonmange.enums.Activite;
import com.keskonmange.enums.Genre;
import com.keskonmange.exceptions.ErreurPersonne;
import com.keskonmange.services.ServicePersonne;

@RestController
@CrossOrigin
@RequestMapping("api/personnes")
public class RestControllerPersonne
{
	private String message;

	@Autowired
	ServicePersonne sp;

	@Autowired
	private MessageSource messageSource;	

	private void verifPersonne(Integer pid) throws ErreurPersonne
	{
		if(sp.findById(pid).isEmpty())
		{
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.notfound", new Object[]
			{pid}, Locale.getDefault()));
		}
	}

	@GetMapping("all")
	public Iterable<Personne> getAll()
	{
		return sp.findAll();
	}

	@GetMapping("{id}")
	public Optional<Personne> getOne(@PathVariable("id")
	Integer pid) throws ErreurPersonne
	{
		verifPersonne(pid);
		return sp.findById(pid);
	}

	@PostMapping
	public Personne create(@Valid @RequestBody
	Personne personne, BindingResult result) throws ErreurPersonne
	{
		if(result.hasErrors())
		{
			message = "";
			result.getFieldErrors().forEach(e ->
			{
				message += messageSource.getMessage("erreur.datalib", new Object[]
				{e.getField(), e.getDefaultMessage()}, Locale.getDefault());
			});
			throw new ErreurPersonne(message);
		}
		return sp.save(personne);
	}

	
	@PutMapping("{id}")
	public Personne update(@RequestBody
	Personne personne, @PathVariable("id")
	Integer pid) throws ErreurPersonne
	{
		System.out.println("Id :" + personne.getId());		
		System.out.println("On est entré avec "+ pid +" = "+ personne.toString());
		verifPersonne(pid);
		if(pid != personne.getId())
		{
			throw new ErreurPersonne(messageSource.getMessage("erreur.personne.notequals", new Object[]
			{pid, personne.getId()}, Locale.getDefault()));
		}
		sp.save(personne);
		return sp.findById(personne.getId()).get();
				
	}
	
	@PutMapping("/recalcul")
	public Integer recalcul(@RequestBody Personne personne) throws ErreurPersonne
	{
		return ServicePersonne.calculBesoinsCaloriques(personne);
	}

	@PatchMapping("identite/{id}")
    public Personne updateIdentite(@RequestBody Personne personne, @PathVariable("id") Integer pid) throws ErreurPersonne
    {
//        verifPersonne(pid);
//        if(pid != personne.getId())
//        {
//            throw new ErreurPersonne(messageSource.getMessage("erreur.personne.notequals", new Object[]
//            {pid, personne.getId()}, Locale.getDefault()));
//        }

        Personne pers = sp.findById(pid).get();

		pers.setNom(personne.getNom() ==  null ? pers.getNom() : personne.getNom());
		pers.setPrenom(personne.getPrenom() ==  null ? pers.getPrenom() : personne.getPrenom());
		pers.setDescription(personne.getDescription() ==  null ? pers.getNom() : personne.getDescription());
		pers.setDateNaissance(personne.getDateNaissance() ==  null ? pers.getDateNaissance() : personne.getDateNaissance());
		pers.setUrlPhoto(personne.getUrlPhoto()  ==  null ? pers.getUrlPhoto()  : personne.getUrlPhoto());

        return sp.save(pers);
    }

    @PatchMapping("morphologie/{id}")
    public Personne updateMorphologie(@RequestBody Personne personne, @PathVariable("id") Integer pid) throws ErreurPersonne
    {
//        verifPersonne(pid);
//        if(pid != personne.getId())
//        {
//            throw new ErreurPersonne(messageSource.getMessage("erreur.personne.notequals", new Object[]
//            {pid, personne.getId()}, Locale.getDefault()));
//        }

        Personne pers = sp.findById(pid).get();
        
		pers.setGenreLibelle(personne.getGenreLibelle() ==  null ? pers.getGenreLibelle() : personne.getGenreLibelle());
        pers.setGenre(Genre.of(personne.getGenreLibelle()));
        
		pers.setTaille(personne.getTaille() ==  null ? pers.getTaille() : personne.getTaille());
		pers.setPoids(personne.getPoids() ==  null ? pers.getPoids() : personne.getPoids());
		
		pers.setActiviteLibelle(personne.getActiviteLibelle() ==  null ? pers.getActiviteLibelle() : personne.getActiviteLibelle());
        pers.setActivite(Activite.of(personne.getActiviteLibelle()));
        
        pers.setBesoinsCaloriques(personne.getBesoinsCaloriques());
		pers.setObjectifCalorique(personne.getObjectifCalorique() ==  null ? pers.getObjectifCalorique() : personne.getObjectifCalorique());

        return sp.save(pers);
    }
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id")
	Integer pid) throws ErreurPersonne
	{
		verifPersonne(pid);
		sp.deleteById(pid);
	}
}
