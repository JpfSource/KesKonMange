package com.keskonmange.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

import com.keskonmange.entities.Groupe;

public class JpaGroupeTest {

	@Autowired 
	private JpaGroupe jpaGpe;
	
	@Test
	public void testFindGroupeByNom() {
		//given
		Groupe gpe = new Groupe();
		gpe.setNom("Test-groupe");
		jpaGpe.save(gpe);
		//when
		Optional<Groupe> gpeResp =jpaGpe.findGroupeByNom("Test-groupe");
		boolean expected = gpeResp.isPresent();
		//then
		assertTrue(expected);
	}

}
