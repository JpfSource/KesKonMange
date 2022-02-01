package com.keskonmange;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.keskonmange.entities.Personne;
import com.keskonmange.repository.JpaPersonne;

@SpringBootTest
class TestPersonne {
	
	@Autowired
	JpaPersonne jp;

	@Test
	public void testFindall() {
		Personne p = new Personne();
		p.setNom("FRANCISCO");
		p.setPrenom("JP");
		jp.save(p);

		Personne p2 = new Personne();
		p2.setNom("DOMBALD");
		p2.setPrenom("Steeve");
		jp.save(p2);
		
		List<Personne> personnes = (List<Personne>) jp.findAll();
		assertNotNull(personnes);
	}
}
