package com.keskonmange;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import com.keskonmange.entities.Personne;
import com.keskonmange.exceptions.ErreurPersonne;
import com.keskonmange.restcontrollers.RestControllerPersonne;
import com.keskonmange.services.ServicePersonne;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestPersonne {

	/* STATIC */

	// STATIC.DECLARATIONS
	private static final Integer PID = 1;
	private static final Integer NB_MAX_PERSONNES = 4;
	private static List<Personne> personnesStatic = TestPersonne.getFewPersonnes(4);
	
	// STATIC.METHODES
	private static Personne getOneGroupe() {
		return TestPersonne.getFewPersonnes(1).get(0);
	}
	private static List<Personne> getFewPersonnes(Integer nbPersonnes) {
		List<Personne> personnes = new ArrayList<Personne>();
		if(nbPersonnes >= 1) { personnes.add(new Personne(1, "FRANCISCO", "Jean-Philippe"));}
		if(nbPersonnes >= 2) { personnes.add(new Personne(2, "DOMBALD", "Steeve"));}
		if(nbPersonnes >= 3) { personnes.add(new Personne(3, "GUILLON", "Antonin"));}
		if(nbPersonnes >= 4) { personnes.add(new Personne(4, "INGOLD", "Christian"));}
		return personnes;
	}

	
	/* NON STATIC */
	
	// NON STATIC.DECLARATIONS
	@Autowired
	@InjectMocks
	RestControllerPersonne rcp;
	
	@Autowired
	@Mock
    ServicePersonne sp;
	
	// NON STATIC.METHODES.TESTS
	@Test
	public void testGetAll() {
	
		when(sp.findAll()).thenReturn(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES));
		
		List<Personne> personnes = (List<Personne>) rcp.getAll();
		
		assertThat(personnes.size()).isEqualTo(NB_MAX_PERSONNES);
        
        assertThat(personnes.get(0).getNom()).isEqualTo(personnesStatic.get(0).getNom());
        assertThat(personnes.get(1).getNom()).isEqualTo(personnesStatic.get(1).getNom());
        assertThat(personnes.get(2).getNom()).isEqualTo(personnesStatic.get(2).getNom());
        assertThat(personnes.get(3).getNom()).isEqualTo(personnesStatic.get(3).getNom());
		
        assertThat(personnes.get(0).getPrenom()).isEqualTo(personnesStatic.get(0).getPrenom());
        assertThat(personnes.get(1).getPrenom()).isEqualTo(personnesStatic.get(1).getPrenom());
        assertThat(personnes.get(2).getPrenom()).isEqualTo(personnesStatic.get(2).getPrenom());
        assertThat(personnes.get(3).getPrenom()).isEqualTo(personnesStatic.get(3).getPrenom());
	}

	@Test
	public void testGetOne() {
		when(sp.findById(any(Integer.class))).thenReturn(Optional.of(TestPersonne.getOneGroupe()));
		
		try {
			Personne personne = rcp.getOne(PID).get();

			assertThatNoException();
			
			assertThat(personne).isNotNull();
	        
	        assertThat(personne.getNom()).isEqualTo(TestPersonne.getOneGroupe().getNom());
			
	        assertThat(personne.getPrenom()).isEqualTo(TestPersonne.getOneGroupe().getPrenom());
	        
		} catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreate() {
		when(sp.save(any(Personne.class))).thenReturn(TestPersonne.getOneGroupe());
		
		try {
			Personne personne = new Personne();
			personne.setNom(TestPersonne.getOneGroupe().getNom());
			personne.setPrenom(TestPersonne.getOneGroupe().getPrenom());
			BindingResult result = mock(BindingResult.class);
			Personne p2 = rcp.create(personne, result);
			
			assertThatNoException();
			
			assertThat(p2).isNotNull();
	        
	        assertThat(p2.getNom()).isEqualTo(TestPersonne.getOneGroupe().getNom());
			
	        assertThat(p2.getPrenom()).isEqualTo(TestPersonne.getOneGroupe().getPrenom());
	        
		} catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate() {
		when(sp.findById(any(Integer.class))).thenReturn(Optional.of(TestPersonne.getOneGroupe()));
		try {
			Personne personne  = rcp.getOne(PID).get();
			personne.setNom(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getNom());
			personne.setPrenom(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPrenom());
			when(sp.save(any(Personne.class))).thenReturn(personne);
			Personne p2 = rcp.update(personne, PID);

			assertThatNoException();
			
			assertThat(p2).isNotNull();
	        
	        assertThat(p2.getNom()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getNom());
			
	        assertThat(p2.getPrenom()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPrenom());
	        
		} catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete() {
		when(sp.findById(any(Integer.class))).thenReturn(Optional.of(TestPersonne.getOneGroupe()));

		try {
			rcp.delete(PID);

			assertThatNoException();
		} catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	}
}	
	
	
