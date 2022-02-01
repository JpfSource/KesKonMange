package com.keskonmange;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.keskonmange.entities.Personne;
import com.keskonmange.exceptions.ErreurPersonne;
import com.keskonmange.restcontrollers.RestControllerPersonne;
import com.keskonmange.services.ServicePersonne;
import java.util.Optional;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestPersonne {

	@Autowired
	@InjectMocks
	RestControllerPersonne rcp;
	
	@Autowired
	@Mock
    ServicePersonne sp;
	
	@Test
	public void testGetAll() {
		List<Personne> mockPersonnes = new ArrayList<Personne>();
		Personne p1 = new Personne(1, "FRANCISCO", "Jean-Philippe");
		Personne p2 = new Personne(2, "DOMBALD", "Steeve");
		Personne p3 = new Personne(3, "GUILLON", "Antonin");
		Personne p4 = new Personne(4, "INGOLD", "Christian");
		mockPersonnes.add(p1);
		mockPersonnes.add(p2);
		mockPersonnes.add(p3);
		mockPersonnes.add(p4);
		
		when(sp.findAll()).thenReturn(mockPersonnes);
		
		// when
		List<Personne> personnes = (List<Personne>) rcp.getAll();
		
		// then
		// --> Contrôle du nombre de lignes récupérées
		assertThat(personnes.size()).isEqualTo(4);
        
		// --> Contrôle sur les noms
        assertThat(personnes.get(0).getNom()).isEqualTo(p1.getNom());
        assertThat(personnes.get(1).getNom()).isEqualTo(p2.getNom());
        assertThat(personnes.get(2).getNom()).isEqualTo(p3.getNom());
        assertThat(personnes.get(3).getNom()).isEqualTo(p4.getNom());
		
		// --> Contrôle sur les prénoms
        assertThat(personnes.get(0).getPrenom()).isEqualTo(p1.getPrenom());
        assertThat(personnes.get(1).getPrenom()).isEqualTo(p2.getPrenom());
        assertThat(personnes.get(2).getPrenom()).isEqualTo(p3.getPrenom());
        assertThat(personnes.get(3).getPrenom()).isEqualTo(p4.getPrenom());
	}

	@Test
	public void testGetOne() {
		Personne p1 = new Personne(1, "FRANCISCO", "Jean-Philippe");
		when(sp.findById(any(Integer.class))).thenReturn(Optional.of(p1));
		
		// when
		try {
			Personne personne = null;
			Optional<Personne> op = rcp.getOne(1);
			if (op != null) {
				personne = op.get();
			}

			// then
			// --> Contrôle du nombre de lignes récupérées
			assertThat(personne).isNotNull();
	        
			// --> Contrôle sur les noms
	        assertThat(personne.getNom()).isEqualTo(p1.getNom());
			
			// --> Contrôle sur les prénoms
	        assertThat(personne.getPrenom()).isEqualTo(p1.getPrenom());
	        
		} catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	}
	
	
}
