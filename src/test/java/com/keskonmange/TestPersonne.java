package com.keskonmange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.keskonmange.exceptions.ErreurPersonne;
import com.keskonmange.restcontrollers.RestControllerPersonne;
import com.keskonmange.services.ServicePersonne;
import com.keskonmange.utils.UtilDate;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestPersonne
{
	/* STATIC */
	// STATIC.DECLARATIONS
	private static final Integer PID = 1;
	private static final Integer NB_MAX_PERSONNES = 4;
	private static List<Personne> personnesStatic = TestPersonne.getFewPersonnes(4);

	// STATIC.METHODES
	private static Personne getOnePersonne()
	{
		return TestPersonne.getFewPersonnes(1).get(0);
	}
	private static List<Personne> getFewPersonnes(Integer nbPersonnes) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<Personne> personnes = new ArrayList<Personne>();
		try {
			if(nbPersonnes >= 1) { personnes.add(new Personne(1, "FRANCISCO", "Jean-Philippe", "Le motard fou !", formatter.parse("27/11/1976"), "https://www.google.com/url?sa=i&url=https%3A%2F%2Ffr.linkedin.com%2Fin%2Fjean-philippe-francisco-425880132&psig=AOvVaw11zoYdMLmEjvhCWr5rql52&ust=1643883682623000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCKCjz4rm4PUCFQAAAAAdAAAAABAK", "Masculin", Genre.MASCULIN, 183, 80, 100, "Sédentaire", Activite.SEDENTAIRE));}
			if(nbPersonnes >= 2) { personnes.add(new Personne(2, "DOMBALD", "Steeve", "Le basketteur fou !", formatter.parse("21/11/1990"), "https://www.google.com/url?sa=i&url=https%3A%2F%2Ffr.linkedin.com%2Fin%2Fsteeve-dombald&psig=AOvVaw1Wr5yz1mbzb7LI0crndPlE&ust=1643883970371000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCKDL8ZPn4PUCFQAAAAAdAAAAABAD", "Masculin", Genre.MASCULIN, 181, 91, 90, "Actif", Activite.ACTIF));}
			if(nbPersonnes >= 3) { personnes.add(new Personne(3, "GUILLON", "Antonin", "Le roi des fous !", formatter.parse("28/04/1997"), "https://www.google.com/url?sa=i&url=https%3A%2F%2Ffr.linkedin.com%2Fin%2Fantonin-guillon-230718128&psig=AOvVaw3cf4M1Kpy5kj5PNb-eXglB&ust=1643884127294000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCMDytN7n4PUCFQAAAAAdAAAAABAD", "Masculin", Genre.MASCULIN, 176, 75, 95, "Peu actif", Activite.PEU_ACTIF));}
			if(nbPersonnes >= 4) { personnes.add(new Personne(4, "INGOLD", "Christian", "Le jogger fou !", formatter.parse("07/12/1986"), "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.reflecta.ch%2Ffr%2Fa-propos%2Fequipe%2Fchristian-ingold&psig=AOvVaw12XQfKmhDWfEM-BcoH_YzD&ust=1643884354220000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPClqMro4PUCFQAAAAAdAAAAABAD", "Masculin", Genre.MASCULIN, 181, 80, 90, "Très actif", Activite.TRES_ACTIF));}
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
		assertThat(personnes.get(0).getDescription()).isEqualTo(personnesStatic.get(0).getDescription());
		assertThat(personnes.get(1).getDescription()).isEqualTo(personnesStatic.get(1).getDescription());
		assertThat(personnes.get(2).getDescription()).isEqualTo(personnesStatic.get(2).getDescription());
		assertThat(personnes.get(3).getDescription()).isEqualTo(personnesStatic.get(3).getDescription());

		assertThat(personnes.get(0).getDateNaissance()).isEqualTo(personnesStatic.get(0).getDateNaissance());
		assertThat(personnes.get(1).getDateNaissance()).isEqualTo(personnesStatic.get(1).getDateNaissance());
		assertThat(personnes.get(2).getDateNaissance()).isEqualTo(personnesStatic.get(2).getDateNaissance());
		assertThat(personnes.get(3).getDateNaissance()).isEqualTo(personnesStatic.get(3).getDateNaissance());

		assertThat(personnes.get(0).getUrlPhoto()).isEqualTo(personnesStatic.get(0).getUrlPhoto());
		assertThat(personnes.get(1).getUrlPhoto()).isEqualTo(personnesStatic.get(1).getUrlPhoto());
		assertThat(personnes.get(2).getUrlPhoto()).isEqualTo(personnesStatic.get(2).getUrlPhoto());
		assertThat(personnes.get(3).getUrlPhoto()).isEqualTo(personnesStatic.get(3).getUrlPhoto());

		assertThat(personnes.get(0).getGenreLibelle()).isEqualTo(personnesStatic.get(0).getGenreLibelle());
		assertThat(personnes.get(1).getGenreLibelle()).isEqualTo(personnesStatic.get(1).getGenreLibelle());
		assertThat(personnes.get(2).getGenreLibelle()).isEqualTo(personnesStatic.get(2).getGenreLibelle());
		assertThat(personnes.get(3).getGenreLibelle()).isEqualTo(personnesStatic.get(3).getGenreLibelle());

		assertThat(personnes.get(0).getGenre()).isEqualTo(personnesStatic.get(0).getGenre());
		assertThat(personnes.get(1).getGenre()).isEqualTo(personnesStatic.get(1).getGenre());
		assertThat(personnes.get(2).getGenre()).isEqualTo(personnesStatic.get(2).getGenre());
		assertThat(personnes.get(3).getGenre()).isEqualTo(personnesStatic.get(3).getGenre());

		assertThat(personnes.get(0).getTaille()).isEqualTo(personnesStatic.get(0).getTaille());
		assertThat(personnes.get(1).getTaille()).isEqualTo(personnesStatic.get(1).getTaille());
		assertThat(personnes.get(2).getTaille()).isEqualTo(personnesStatic.get(2).getTaille());
		assertThat(personnes.get(3).getTaille()).isEqualTo(personnesStatic.get(3).getTaille());

		assertThat(personnes.get(0).getPoids()).isEqualTo(personnesStatic.get(0).getPoids());
		assertThat(personnes.get(1).getPoids()).isEqualTo(personnesStatic.get(1).getPoids());
		assertThat(personnes.get(2).getPoids()).isEqualTo(personnesStatic.get(2).getPoids());
		assertThat(personnes.get(3).getPoids()).isEqualTo(personnesStatic.get(3).getPoids());

		assertThat(personnes.get(0).getObjectifCalorique()).isEqualTo(personnesStatic.get(0).getObjectifCalorique());
		assertThat(personnes.get(1).getObjectifCalorique()).isEqualTo(personnesStatic.get(1).getObjectifCalorique());
		assertThat(personnes.get(2).getObjectifCalorique()).isEqualTo(personnesStatic.get(2).getObjectifCalorique());
		assertThat(personnes.get(3).getObjectifCalorique()).isEqualTo(personnesStatic.get(3).getObjectifCalorique());

		assertThat(personnes.get(0).getActiviteLibelle()).isEqualTo(personnesStatic.get(0).getActiviteLibelle());
		assertThat(personnes.get(1).getActiviteLibelle()).isEqualTo(personnesStatic.get(1).getActiviteLibelle());
		assertThat(personnes.get(2).getActiviteLibelle()).isEqualTo(personnesStatic.get(2).getActiviteLibelle());
		assertThat(personnes.get(3).getActiviteLibelle()).isEqualTo(personnesStatic.get(3).getActiviteLibelle());

		assertThat(personnes.get(0).getActivite()).isEqualTo(personnesStatic.get(0).getActivite());
		assertThat(personnes.get(1).getActivite()).isEqualTo(personnesStatic.get(1).getActivite());
		assertThat(personnes.get(2).getActivite()).isEqualTo(personnesStatic.get(2).getActivite());
		assertThat(personnes.get(3).getActivite()).isEqualTo(personnesStatic.get(3).getActivite());
	}

	@Test
	public void testGetOne()
	{
		when(sp.findById(any(Integer.class))).thenReturn(Optional.of(TestPersonne.getOnePersonne()));
    try {
			Personne personne = rcp.getOne(PID).get();
			assertThatNoException();
			assertThat(personne).isNotNull();
			assertThat(personne.getNom()).isEqualTo(TestPersonne.getOnePersonne().getNom());
			assertThat(personne.getPrenom()).isEqualTo(TestPersonne.getOnePersonne().getPrenom());
			assertThat(personne.getDescription()).isEqualTo(TestPersonne.getOnePersonne().getDescription());
			assertThat(personne.getDateNaissance()).isEqualTo(TestPersonne.getOnePersonne().getDateNaissance());
			assertThat(personne.getUrlPhoto()).isEqualTo(TestPersonne.getOnePersonne().getUrlPhoto());
			assertThat(personne.getGenreLibelle()).isEqualTo(TestPersonne.getOnePersonne().getGenreLibelle());
			assertThat(personne.getGenre()).isEqualTo(TestPersonne.getOnePersonne().getGenre());
			assertThat(personne.getTaille()).isEqualTo(TestPersonne.getOnePersonne().getTaille());
			assertThat(personne.getPoids()).isEqualTo(TestPersonne.getOnePersonne().getPoids());
			assertThat(personne.getObjectifCalorique()).isEqualTo(TestPersonne.getOnePersonne().getObjectifCalorique());
			assertThat(personne.getActiviteLibelle()).isEqualTo(TestPersonne.getOnePersonne().getActiviteLibelle());
			assertThat(personne.getActivite()).isEqualTo(TestPersonne.getOnePersonne().getActivite());
		} catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreate()
	{
		when(sp.save(any(Personne.class))).thenReturn(TestPersonne.getOnePersonne());

		try {
			Personne personne = new Personne();
			personne.setNom(TestPersonne.getOnePersonne().getNom());
			personne.setPrenom(TestPersonne.getOnePersonne().getPrenom());
			personne.setDescription(TestPersonne.getOnePersonne().getDescription());
			personne.setDateNaissance(TestPersonne.getOnePersonne().getDateNaissance());
			personne.setUrlPhoto(TestPersonne.getOnePersonne().getUrlPhoto());
			personne.setGenreLibelle(TestPersonne.getOnePersonne().getGenreLibelle());
			personne.setGenre(TestPersonne.getOnePersonne().getGenre());
			personne.setTaille(TestPersonne.getOnePersonne().getTaille());
			personne.setPoids(TestPersonne.getOnePersonne().getPoids());
			personne.setObjectifCalorique(TestPersonne.getOnePersonne().getObjectifCalorique());
			personne.setActiviteLibelle(TestPersonne.getOnePersonne().getActiviteLibelle());
			personne.setActivite(TestPersonne.getOnePersonne().getActivite());
			BindingResult result = mock(BindingResult.class);
			Personne p2 = rcp.create(personne, result);

			assertThatNoException();

			assertThat(p2).isNotNull();
			assertThat(p2.getNom()).isEqualTo(TestPersonne.getOnePersonne().getNom());
			assertThat(p2.getPrenom()).isEqualTo(TestPersonne.getOnePersonne().getPrenom());
			assertThat(p2.getDescription()).isEqualTo(TestPersonne.getOnePersonne().getDescription());
			assertThat(p2.getDateNaissance()).isEqualTo(TestPersonne.getOnePersonne().getDateNaissance());
			assertThat(p2.getUrlPhoto()).isEqualTo(TestPersonne.getOnePersonne().getUrlPhoto());
			assertThat(p2.getGenreLibelle()).isEqualTo(TestPersonne.getOnePersonne().getGenreLibelle());
			assertThat(p2.getGenre()).isEqualTo(TestPersonne.getOnePersonne().getGenre());
			assertThat(p2.getTaille()).isEqualTo(TestPersonne.getOnePersonne().getTaille());
			assertThat(p2.getPoids()).isEqualTo(TestPersonne.getOnePersonne().getPoids());
			assertThat(p2.getObjectifCalorique()).isEqualTo(TestPersonne.getOnePersonne().getObjectifCalorique());
			assertThat(p2.getActiviteLibelle()).isEqualTo(TestPersonne.getOnePersonne().getActiviteLibelle());
			assertThat(p2.getActivite()).isEqualTo(TestPersonne.getOnePersonne().getActivite());
		} catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate()
	{
		when(sp.findById(any(Integer.class))).thenReturn(Optional.of(TestPersonne.getOnePersonne()));
		try
		{
			Personne personne = rcp.getOne(PID).get();
			personne.setNom(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getNom());
			personne.setPrenom(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPrenom());
			personne.setDescription(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getDescription());
			personne.setDateNaissance(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getDateNaissance());
			personne.setUrlPhoto(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getUrlPhoto());
			personne.setGenreLibelle(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getGenreLibelle());
			personne.setGenre(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getGenre());
			personne.setTaille(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getTaille());
			personne.setPoids(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPoids());
			personne.setObjectifCalorique(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getObjectifCalorique());
			personne.setActiviteLibelle(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getActiviteLibelle());
			personne.setActivite(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getActivite());
			when(sp.save(any(Personne.class))).thenReturn(personne);
			Personne p2 = rcp.update(personne, PID);
			assertThatNoException();

			assertThat(p2).isNotNull();

			assertThat(p2.getNom()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getNom());
			assertThat(p2.getPrenom()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPrenom());
			assertThat(p2.getDescription()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getDescription());
			assertThat(p2.getDateNaissance()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getDateNaissance());
			assertThat(p2.getUrlPhoto()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getUrlPhoto());
			assertThat(p2.getGenreLibelle()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getGenreLibelle());
			assertThat(p2.getGenre()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getGenre());
			assertThat(p2.getTaille()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getTaille());
			assertThat(p2.getPoids()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getPoids());
			assertThat(p2.getObjectifCalorique()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getObjectifCalorique());
			assertThat(p2.getActiviteLibelle()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getActiviteLibelle());
			assertThat(p2.getActivite()).isEqualTo(TestPersonne.getFewPersonnes(NB_MAX_PERSONNES).get(1).getActivite());

		} catch (ErreurPersonne e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete()
	{
		when(sp.findById(any(Integer.class))).thenReturn(Optional.of(TestPersonne.getOnePersonne()));
		try
		{
			rcp.delete(PID);
			assertThatNoException();
		}
		catch (ErreurPersonne e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testCalculBesoinsCaloriques() {
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Masculin", Genre.MASCULIN, 182, 82, 100, "Sédentaire", Activite.SEDENTAIRE)) == 2471);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Masculin", Genre.MASCULIN, 182, 82, 90, "Sédentaire", Activite.SEDENTAIRE))  == 2224);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Masculin", Genre.MASCULIN, 182, 82, 110, "Sédentaire", Activite.SEDENTAIRE)) == 2718);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Masculin", Genre.MASCULIN, 182, 82, 110, "Peu actif", Activite.PEU_ACTIF))   == 3084);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Masculin", Genre.MASCULIN, 182, 82, 110, "Actif", Activite.ACTIF))           == 3242);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Masculin", Genre.MASCULIN, 182, 82, 110, "Très actif", Activite.TRES_ACTIF)) == 3598);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Masculin", Genre.MASCULIN, 182, 82, 100, "Sédentaire", Activite.SEDENTAIRE)) == 5863);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Masculin", Genre.MASCULIN, 182, 82, 90, "Sédentaire", Activite.SEDENTAIRE))  == 5276);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Masculin", Genre.MASCULIN, 182, 82, 110, "Sédentaire", Activite.SEDENTAIRE)) == 6449);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Masculin", Genre.MASCULIN, 182, 82, 110, "Peu actif", Activite.PEU_ACTIF))   == 7317);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Masculin", Genre.MASCULIN, 182, 82, 110, "Actif", Activite.ACTIF))           == 7692);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Masculin", Genre.MASCULIN, 182, 82, 110, "Très actif", Activite.TRES_ACTIF)) == 8536);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Feminin", Genre.FEMININ, 182, 82, 100, "Sédentaire", Activite.SEDENTAIRE))   == 2177);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Feminin", Genre.FEMININ, 182, 82, 90, "Sédentaire", Activite.SEDENTAIRE))    == 1959);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Feminin", Genre.FEMININ, 182, 82, 110, "Sédentaire", Activite.SEDENTAIRE))   == 2395);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Feminin", Genre.FEMININ, 182, 82, 110, "Peu actif", Activite.PEU_ACTIF))     == 2717);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Feminin", Genre.FEMININ, 182, 82, 110, "Actif", Activite.ACTIF))             == 2856);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(45) , null, "Feminin", Genre.FEMININ, 182, 82, 110, "Très actif", Activite.TRES_ACTIF))   == 3170);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Feminin", Genre.FEMININ, 182, 82, 100, "Sédentaire", Activite.SEDENTAIRE))   == 4961);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Feminin", Genre.FEMININ, 182, 82, 90, "Sédentaire", Activite.SEDENTAIRE))    == 4464);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Feminin", Genre.FEMININ, 182, 82, 110, "Sédentaire", Activite.SEDENTAIRE))   == 5457);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Feminin", Genre.FEMININ, 182, 82, 110, "Peu actif", Activite.PEU_ACTIF))     == 6191);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Feminin", Genre.FEMININ, 182, 82, 110, "Actif", Activite.ACTIF))             == 6508);
		assertThat(ServicePersonne.calculBesoinsCaloriques(new Personne(1, "Pierre", "DUPONT", null, UtilDate.getNaissanceFromAge(16) , null, "Feminin", Genre.FEMININ, 182, 82, 110, "Très actif", Activite.TRES_ACTIF))   == 7223);
	}
}
