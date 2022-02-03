package com.keskonmange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import com.keskonmange.entities.Groupe;
import com.keskonmange.entities.Personne;
import com.keskonmange.exceptions.ErreurGroupe;
import com.keskonmange.restcontrollers.RestControllerGroupe;
import com.keskonmange.services.ServiceGroupe;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestGroupe {

	/* STATIC */

	// STATIC.DECLARATIONS
	private static final Integer PID = 1;
	private static final Integer NB_MAX_GROUPES = 2;
	private static List<Groupe> groupesStatic = TestGroupe.getFewGroupes(NB_MAX_GROUPES);

//	// STATIC.METHODES
	private static Groupe getOneGroupe() {
		return TestGroupe.getFewGroupes(1).get(0);
	}

	private static List<Groupe> getFewGroupes(Integer nbGroupes) {
		List<Groupe> groupes = new ArrayList<Groupe>();
		if (nbGroupes >= 1) {
			groupes.add(new Groupe(1, "MON GROUPE 1", "google.fr"));
		}
		if (nbGroupes >= 2) {
			groupes.add(new Groupe(2, "MON GROUPE 2", "yahoo.fr"));
		}
		return groupes;
	}
	

	// NON STATIC.DECLARATIONS
	@Autowired
	@InjectMocks
	RestControllerGroupe rcg;


	@Autowired
	@Mock
	ServiceGroupe gp;
	
	@Autowired
	private ServiceGroupe sg;

	// NON STATIC.METHODES.TESTS
	@Test
	public void testGetAll() {
		// given

		when(gp.findAll()).thenReturn(TestGroupe.getFewGroupes(NB_MAX_GROUPES));

		// when
		List<Groupe> result = (List<Groupe>) rcg.getAll();

		// then
		assertThat(result.size()).isEqualTo(NB_MAX_GROUPES);

		assertThat(result.get(0).getNom()).isEqualTo(groupesStatic.get(0).getNom());
		assertThat(result.get(1).getNom()).isEqualTo(groupesStatic.get(1).getNom());

	}

	@Test
	public void testCreate() {

		when(gp.save(any(Groupe.class))).thenReturn(TestGroupe.getOneGroupe());

		try {
			Groupe groupe = new Groupe();
			groupe.setNom(TestGroupe.getOneGroupe().getNom());
			groupe.setUrlPhoto(TestGroupe.getOneGroupe().getUrlPhoto());

			BindingResult result = mock(BindingResult.class);
			Groupe g2 = rcg.create(groupe, result);
			
			assertThatNoException();

			assertThat(g2).isNotNull();

			assertThat(g2.getNom()).isEqualTo(TestGroupe.getOneGroupe().getNom());

			assertThat(g2.getUrlPhoto()).isEqualTo(TestGroupe.getOneGroupe().getUrlPhoto());
			


		} catch (ErreurGroupe e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetOne() {
		when(gp.findById(any(Integer.class))).thenReturn(Optional.of(TestGroupe.getOneGroupe()));

		try {
			Groupe groupe = rcg.getOne(PID).get();

			assertThatNoException();

			assertThat(groupe).isNotNull();

			assertThat(groupe.getNom()).isEqualTo(TestGroupe.getOneGroupe().getNom());

			assertThat(groupe.getUrlPhoto()).isEqualTo(TestGroupe.getOneGroupe().getUrlPhoto());

		} catch (ErreurGroupe e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate() {
		when(gp.findById(any(Integer.class))).thenReturn(Optional.of(TestGroupe.getOneGroupe()));
		try {
			Groupe groupe = rcg.getOne(PID).get();
			groupe.setNom(TestGroupe.getFewGroupes(NB_MAX_GROUPES).get(1).getNom());
			groupe.setUrlPhoto(TestGroupe.getFewGroupes(NB_MAX_GROUPES).get(1).getUrlPhoto());
			when(gp.save(any(Groupe.class))).thenReturn(groupe);
			Groupe g2 = rcg.update(groupe, PID);

			assertThatNoException();

			assertThat(g2).isNotNull();

			assertThat(g2.getNom()).isEqualTo(TestGroupe.getFewGroupes(NB_MAX_GROUPES).get(1).getNom());

			assertThat(g2.getUrlPhoto()).isEqualTo(TestGroupe.getFewGroupes(NB_MAX_GROUPES).get(1).getUrlPhoto());

		} catch (ErreurGroupe e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete() {
		when(gp.findById(any(Integer.class))).thenReturn(Optional.of(TestGroupe.getOneGroupe()));

		try {
			rcg.delete(PID);

			assertThatNoException();
		} catch (ErreurGroupe e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetBesoinCaloriqueGroupe() {
		//given
		Personne p1 = new Personne("Jean", "1", 1500);
		Personne p2 = new Personne("Vier", "2", 2500);
		
		Set<Personne> personnes = new HashSet<Personne>();
		personnes.add(p1);
		personnes.add(p2);
		
		Groupe gpe = new Groupe("Groupe Test", null);
		
		gpe.setGroupePersonnes(personnes);

		sg.getBesoinCaloriqueGroupe(gpe);
		
		assertThat(gpe.getBesoinCalorique() == 4000);
		
	}

}
