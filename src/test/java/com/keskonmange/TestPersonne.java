package com.keskonmange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import com.keskonmange.enums.Activite;
import com.keskonmange.enums.Genre;
import com.keskonmange.enums.Role;
import com.keskonmange.exceptions.ErreurPersonne;
import com.keskonmange.restcontrollers.RestControllerPersonne;
import com.keskonmange.services.ServicePersonne;
import com.keskonmange.utils.UtilDate;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestPersonne {
	
	// STATIC 
	// STATIC.DECLARATIONS
	private static final Integer PID = 1;
	private static final Integer NB_MAX_PERSONNES = 4;
	private static List<Personne> personnesStatic = TestPersonne.getFewPersonnes(4);
	
	// STATIC.METHODES
	private static Personne getOnePersonne() {
		return TestPersonne.getFewPersonnes(1).get(0);
	}
	private static List<Personne> getFewPersonnes(Integer nbPersonnes) { 
		List<Personne> personnes = new ArrayList<Personne>();
		if(nbPersonnes >= 1) {
			personnes.add(new Personne(1, "FRANCISCO", "Jean-Philippe", Genre.MASCULIN, LocalDate.of( 1976, 11, 27), 183, 80, 100, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/David_Schwimmer_2011.jpg/640px-David_Schwimmer_2011.jpg", Activite.SEDENTAIRE, "Le motard fou !", "jpf@gmail.com", "jpf1234", Role.ADMIN));
		}
		if(nbPersonnes >= 2) {
			personnes.add(new Personne(2, "DOMBALD", "Steeve", Genre.FEMININ, LocalDate.of( 1990, 11, 21), 181, 91, 90, "https://fr.web.img6.acsta.net/pictures/19/02/21/10/42/0074317.jpg", Activite.ACTIF, "Le basketteur fou !", "sd@gmail.com", "sd1234", Role.ADMIN));
		}
		if(nbPersonnes >= 3) {
			personnes.add(new Personne(3, "INGOLD", "Christian", Genre.MASCULIN, LocalDate.of( 1997, 04, 28), 176, 75, 95, "https://fr.web.img6.acsta.net/c_310_420/pictures/20/01/16/09/48/3201727.jpg", Activite.TRES_ACTIF, "Beau Gosse !", "ci@gmail.com", "ci1324", Role.USER));
		}
		if(nbPersonnes >= 4) {
			personnes.add(new Personne(4, "GUILLON", "Antonin", Genre.FEMININ, LocalDate.of( 1986, 12, 07), 181, 80, 90, "https://fr.web.img6.acsta.net/c_162_216/pictures/20/01/31/14/13/5661728.jpg", Activite.PEU_ACTIF, "Le cuisinier fou !", "ag@gmail.com", "ag1234", Role.USER));
		}
		return personnes;
	} 
	
	// NON STATIC 
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
	 
		assertThat(personnes.get(0).getActivite()).isEqualTo(personnesStatic.get(0).getActivite());
		assertThat(personnes.get(0).getDateNaissance()).isEqualTo(personnesStatic.get(0).getDateNaissance());
		assertThat(personnes.get(0).getDescription()).isEqualTo(personnesStatic.get(0).getDescription());
		assertThat(personnes.get(0).getEmail()).isEqualTo(personnesStatic.get(0).getEmail());
		assertThat(personnes.get(0).getGenre()).isEqualTo(personnesStatic.get(0).getGenre());
		assertThat(personnes.get(0).getId()).isEqualTo(personnesStatic.get(0).getId());
		assertThat(personnes.get(0).getNom()).isEqualTo(personnesStatic.get(0).getNom());
		assertThat(personnes.get(0).getObjectifCalorique()).isEqualTo(personnesStatic.get(0).getObjectifCalorique());
		assertThat(personnes.get(0).getPoids()).isEqualTo(personnesStatic.get(0).getPoids());
		assertThat(personnes.get(0).getPrenom()).isEqualTo(personnesStatic.get(0).getPrenom());
		assertThat(personnes.get(0).getPwd()).isEqualTo(personnesStatic.get(0).getPwd());
		assertThat(personnes.get(0).getRole()).isEqualTo(personnesStatic.get(0).getRole());
		assertThat(personnes.get(0).getTaille()).isEqualTo(personnesStatic.get(0).getTaille());
		assertThat(personnes.get(0).getUrlPhoto()).isEqualTo(personnesStatic.get(0).getUrlPhoto());

		assertThat(personnes.get(1).getActivite()).isEqualTo(personnesStatic.get(1).getActivite());
		assertThat(personnes.get(1).getDateNaissance()).isEqualTo(personnesStatic.get(1).getDateNaissance());
		assertThat(personnes.get(1).getDescription()).isEqualTo(personnesStatic.get(1).getDescription());
		assertThat(personnes.get(1).getEmail()).isEqualTo(personnesStatic.get(1).getEmail());
		assertThat(personnes.get(1).getGenre()).isEqualTo(personnesStatic.get(1).getGenre());
		assertThat(personnes.get(1).getId()).isEqualTo(personnesStatic.get(1).getId());
		assertThat(personnes.get(1).getNom()).isEqualTo(personnesStatic.get(1).getNom());
		assertThat(personnes.get(1).getObjectifCalorique()).isEqualTo(personnesStatic.get(1).getObjectifCalorique());
		assertThat(personnes.get(1).getPoids()).isEqualTo(personnesStatic.get(1).getPoids());
		assertThat(personnes.get(1).getPrenom()).isEqualTo(personnesStatic.get(1).getPrenom());
		assertThat(personnes.get(1).getPwd()).isEqualTo(personnesStatic.get(1).getPwd());
		assertThat(personnes.get(1).getRole()).isEqualTo(personnesStatic.get(1).getRole());
		assertThat(personnes.get(1).getTaille()).isEqualTo(personnesStatic.get(1).getTaille());
		assertThat(personnes.get(1).getUrlPhoto()).isEqualTo(personnesStatic.get(1).getUrlPhoto());

		assertThat(personnes.get(2).getActivite()).isEqualTo(personnesStatic.get(2).getActivite());
		assertThat(personnes.get(2).getDateNaissance()).isEqualTo(personnesStatic.get(2).getDateNaissance());
		assertThat(personnes.get(2).getDescription()).isEqualTo(personnesStatic.get(2).getDescription());
		assertThat(personnes.get(2).getEmail()).isEqualTo(personnesStatic.get(2).getEmail());
		assertThat(personnes.get(2).getGenre()).isEqualTo(personnesStatic.get(2).getGenre());
		assertThat(personnes.get(2).getId()).isEqualTo(personnesStatic.get(2).getId());
		assertThat(personnes.get(2).getNom()).isEqualTo(personnesStatic.get(2).getNom());
		assertThat(personnes.get(2).getObjectifCalorique()).isEqualTo(personnesStatic.get(2).getObjectifCalorique());
		assertThat(personnes.get(2).getPoids()).isEqualTo(personnesStatic.get(2).getPoids());
		assertThat(personnes.get(2).getPrenom()).isEqualTo(personnesStatic.get(2).getPrenom());
		assertThat(personnes.get(2).getPwd()).isEqualTo(personnesStatic.get(2).getPwd());
		assertThat(personnes.get(2).getRole()).isEqualTo(personnesStatic.get(2).getRole());
		assertThat(personnes.get(2).getTaille()).isEqualTo(personnesStatic.get(2).getTaille());
		assertThat(personnes.get(2).getUrlPhoto()).isEqualTo(personnesStatic.get(2).getUrlPhoto());

		assertThat(personnes.get(3).getActivite()).isEqualTo(personnesStatic.get(3).getActivite());
		assertThat(personnes.get(3).getDateNaissance()).isEqualTo(personnesStatic.get(3).getDateNaissance());
		assertThat(personnes.get(3).getDescription()).isEqualTo(personnesStatic.get(3).getDescription());
		assertThat(personnes.get(3).getEmail()).isEqualTo(personnesStatic.get(3).getEmail());
		assertThat(personnes.get(3).getGenre()).isEqualTo(personnesStatic.get(3).getGenre());
		assertThat(personnes.get(3).getId()).isEqualTo(personnesStatic.get(3).getId());
		assertThat(personnes.get(3).getNom()).isEqualTo(personnesStatic.get(3).getNom());
		assertThat(personnes.get(3).getObjectifCalorique()).isEqualTo(personnesStatic.get(3).getObjectifCalorique());
		assertThat(personnes.get(3).getPoids()).isEqualTo(personnesStatic.get(3).getPoids());
		assertThat(personnes.get(3).getPrenom()).isEqualTo(personnesStatic.get(3).getPrenom());
		assertThat(personnes.get(3).getPwd()).isEqualTo(personnesStatic.get(3).getPwd());
		assertThat(personnes.get(3).getRole()).isEqualTo(personnesStatic.get(3).getRole());
		assertThat(personnes.get(3).getTaille()).isEqualTo(personnesStatic.get(3).getTaille());
		assertThat(personnes.get(3).getUrlPhoto()).isEqualTo(personnesStatic.get(3).getUrlPhoto());
	}
	
	@Test
	public void testGetOne() throws ErreurPersonne {
		when(sp.findById(any(Integer.class))).thenReturn(Optional.of(TestPersonne.getOnePersonne()));
		try {
			Personne personne = rcp.getOne(PID).get();
			
			assertThatNoException();
			assertThat(personne).isNotNull();
			
			assertThat(personne.getActivite()).isEqualTo(TestPersonne.getOnePersonne().getActivite());
			assertThat(personne.getDateNaissance()).isEqualTo(TestPersonne.getOnePersonne().getDateNaissance());
			assertThat(personne.getDescription()).isEqualTo(TestPersonne.getOnePersonne().getDescription());
			assertThat(personne.getEmail()).isEqualTo(TestPersonne.getOnePersonne().getEmail());
			assertThat(personne.getGenre()).isEqualTo(TestPersonne.getOnePersonne().getGenre());
			assertThat(personne.getId()).isEqualTo(TestPersonne.getOnePersonne().getId());
			assertThat(personne.getNom()).isEqualTo(TestPersonne.getOnePersonne().getNom());
			assertThat(personne.getObjectifCalorique()).isEqualTo(TestPersonne.getOnePersonne().getObjectifCalorique());
			assertThat(personne.getPoids()).isEqualTo(TestPersonne.getOnePersonne().getPoids());
			assertThat(personne.getPrenom()).isEqualTo(TestPersonne.getOnePersonne().getPrenom());
			assertThat(personne.getPwd()).isEqualTo(TestPersonne.getOnePersonne().getPwd());
			assertThat(personne.getRole()).isEqualTo(TestPersonne.getOnePersonne().getRole());
			assertThat(personne.getTaille()).isEqualTo(TestPersonne.getOnePersonne().getTaille());
			assertThat(personne.getUrlPhoto()).isEqualTo(TestPersonne.getOnePersonne().getUrlPhoto());
		}
		catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreate() {
		when(sp.savePersonCreatedByUser(any(Personne.class))).thenReturn(TestPersonne.getOnePersonne());
	 
		try {
			Personne personne = new Personne();
			
			personne.setId(TestPersonne.getOnePersonne().getId());
			personne.setNom(TestPersonne.getOnePersonne().getNom());
			personne.setPrenom(TestPersonne.getOnePersonne().getPrenom());
			personne.setGenre(TestPersonne.getOnePersonne().getGenre());
			personne.setDateNaissance(TestPersonne.getOnePersonne().getDateNaissance());
			personne.setTaille(TestPersonne.getOnePersonne().getTaille());
			personne.setPoids(TestPersonne.getOnePersonne().getPoids());
			personne.setObjectifCalorique(TestPersonne.getOnePersonne().getObjectifCalorique());
			personne.setUrlPhoto(TestPersonne.getOnePersonne().getUrlPhoto());
			personne.setActivite(TestPersonne.getOnePersonne().getActivite());
			personne.setDescription(TestPersonne.getOnePersonne().getDescription());
			personne.setEmail(TestPersonne.getOnePersonne().getEmail());
			personne.setPwd(TestPersonne.getOnePersonne().getPwd());
			personne.setRole(TestPersonne.getOnePersonne().getRole());
			
			BindingResult result = mock(BindingResult.class);
			
			Personne p2 = rcp.create(personne, result);
	 
			assertThatNoException();
			assertThat(p2).isNotNull();
			
			assertThat(p2.getId()).isEqualTo(TestPersonne.getOnePersonne().getId());
			assertThat(p2.getNom()).isEqualTo(TestPersonne.getOnePersonne().getNom());
			assertThat(p2.getPrenom()).isEqualTo(TestPersonne.getOnePersonne().getPrenom());
			assertThat(p2.getGenre()).isEqualTo(TestPersonne.getOnePersonne().getGenre());
			assertThat(p2.getDateNaissance()).isEqualTo(TestPersonne.getOnePersonne().getDateNaissance());
			assertThat(p2.getTaille()).isEqualTo(TestPersonne.getOnePersonne().getTaille());
			assertThat(p2.getPoids()).isEqualTo(TestPersonne.getOnePersonne().getPoids());
			assertThat(p2.getObjectifCalorique()).isEqualTo(TestPersonne.getOnePersonne().getObjectifCalorique());
			assertThat(p2.getUrlPhoto()).isEqualTo(TestPersonne.getOnePersonne().getUrlPhoto());
			assertThat(p2.getActivite()).isEqualTo(TestPersonne.getOnePersonne().getActivite());
			assertThat(p2.getDescription()).isEqualTo(TestPersonne.getOnePersonne().getDescription());
			assertThat(p2.getEmail()).isEqualTo(TestPersonne.getOnePersonne().getEmail());
			assertThat(p2.getPwd()).isEqualTo(TestPersonne.getOnePersonne().getPwd());
			assertThat(p2.getRole()).isEqualTo(TestPersonne.getOnePersonne().getRole());
		}
		catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	} 
		
	@Test
	public void testUpdate() {
		when(sp.findById(any(Integer.class))).thenReturn(Optional.of(TestPersonne.getOnePersonne()));
		
		try {
			Personne personne = rcp.getOne(PID).get();
			
			personne.setNom(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getNom());
			personne.setPrenom(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPrenom());
			personne.setGenre(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getGenre());
			personne.setDateNaissance(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getDateNaissance());
			personne.setTaille(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getTaille());
			personne.setPoids(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPoids());
			personne.setObjectifCalorique(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getObjectifCalorique());
			personne.setUrlPhoto(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getUrlPhoto());
			personne.setActivite(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getActivite());
			personne.setDescription(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getDescription());
			personne.setEmail(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getEmail());
			personne.setPwd(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPwd());
			personne.setRole(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getRole());
			
			when(sp.save(any(Personne.class))).thenReturn(personne);
			
			Personne p2 = rcp.update(personne, PID);
			
			assertThatNoException();
			assertThat(p2).isNotNull();

			assertThat(p2.getNom()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getNom());
			assertThat(p2.getPrenom()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPrenom());
			assertThat(p2.getGenre()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getGenre());
			assertThat(p2.getDateNaissance()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getDateNaissance());
			assertThat(p2.getTaille()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getTaille());
			assertThat(p2.getPoids()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPoids());
			assertThat(p2.getObjectifCalorique()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getObjectifCalorique());
			assertThat(p2.getUrlPhoto()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getUrlPhoto());
			assertThat(p2.getActivite()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getActivite());
			assertThat(p2.getDescription()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getDescription());
			assertThat(p2.getEmail()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getEmail());
			assertThat(p2.getPwd()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPwd());
			assertThat(p2.getRole()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getRole());
			}
		catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	} 
	 
	@Test
	public void testDelete() throws ErreurPersonne {
		when(sp.findById(any(Integer.class))).thenReturn(Optional.of(TestPersonne.getOnePersonne()));
		
		try {
			rcp.delete(PID);
			assertThatNoException();
		}
		catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCalculBesoinsCaloriques() {
		Personne p = null;

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 6509);
	          assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.PEU_ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 6191);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.SEDENTAIRE, 100);
		try { assertThat(rcp.recalcul(p) == 4961);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.SEDENTAIRE, 90);
		try { assertThat(rcp.recalcul(p) == 4465);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.SEDENTAIRE, 110);
		try { assertThat(rcp.recalcul(p) == 5457);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.TRES_ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 7223);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 2857);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.PEU_ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 2717);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.SEDENTAIRE, 100);
		try { assertThat(rcp.recalcul(p) == 2177);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.SEDENTAIRE, 90);
		try { assertThat(rcp.recalcul(p) == 1960);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.SEDENTAIRE, 110);
		try { assertThat(rcp.recalcul(p) == 2395);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.FEMININ, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.TRES_ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 3170);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 7692);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.PEU_ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 7317);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.SEDENTAIRE, 100);
		try { assertThat(rcp.recalcul(p) == 5863);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.SEDENTAIRE, 90);
		try { assertThat(rcp.recalcul(p) == 5277);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.SEDENTAIRE, 110);
		try { assertThat(rcp.recalcul(p) == 6449);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(16), Activite.TRES_ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 8537);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 3242);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.PEU_ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 3084);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.SEDENTAIRE, 100);
		try { assertThat(rcp.recalcul(p) == 2471);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.SEDENTAIRE, 90);
		try { assertThat(rcp.recalcul(p) == 2224);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.SEDENTAIRE, 110);
		try { assertThat(rcp.recalcul(p) == 2718);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }

		p = new Personne(Genre.MASCULIN, 82, 182, UtilDate.getNaissanceFromAge(45), Activite.TRES_ACTIF, 110);
		try { assertThat(rcp.recalcul(p) == 3598);
		      assertThatNoException();
		} catch (ErreurPersonne e) { e.printStackTrace(); }
		
		
	}

}
