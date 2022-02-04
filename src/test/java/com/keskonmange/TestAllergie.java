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

import com.keskonmange.entities.Allergie;
import com.keskonmange.exceptions.ErreurAllergie;
import com.keskonmange.restcontrollers.RestControllerAllergie;
import com.keskonmange.services.ServiceAllergie;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestAllergie
{
	/* STATIC */
	// STATIC.DECLARATIONS
	private static final Integer PID = 1;
	private static final Integer NB_MAX_ALLERGIES = 4;
	private static List<Allergie> allergiesStatic = TestAllergie.getFewAllergies(4);

	// STATIC.METHODES
	private static Allergie getOneAllergie()
	{
		return TestAllergie.getFewAllergies(1).get(0);
	}
	private static List<Allergie> getFewAllergies(Integer nbAllergies) {
		List<Allergie> allergies = new ArrayList<Allergie>();
		if(nbAllergies >= 1) { allergies.add(new Allergie(1, "Cacahuète"));}
		if(nbAllergies >= 2) { allergies.add(new Allergie(2, "Produits laitiers"));}
		if(nbAllergies >= 3) { allergies.add(new Allergie(3, "Soja"));}
		if(nbAllergies >= 4) { allergies.add(new Allergie(4, "Gluten"));}
		return allergies;
	}


	/* NON STATIC */
	// NON STATIC.DECLARATIONS
	@Autowired
	@InjectMocks
	RestControllerAllergie rca;

	@Autowired
	@Mock
	ServiceAllergie sa;

	// NON STATIC.METHODES.TESTS
	@Test
	public void testGetAll() {

		when(sa.findAll()).thenReturn(TestAllergie.getFewAllergies(NB_MAX_ALLERGIES));

		List<Allergie> allergies = (List<Allergie>) rca.getAll();

		assertThat(allergies.size()).isEqualTo(NB_MAX_ALLERGIES);
		
		assertThat(allergies.get(0).getLibelle()).isEqualTo(allergiesStatic.get(0).getLibelle());
		assertThat(allergies.get(1).getLibelle()).isEqualTo(allergiesStatic.get(1).getLibelle());
		assertThat(allergies.get(2).getLibelle()).isEqualTo(allergiesStatic.get(2).getLibelle());
		assertThat(allergies.get(3).getLibelle()).isEqualTo(allergiesStatic.get(3).getLibelle());
	}

	@Test
	public void testGetOne()
	{
		when(sa.findById(any(Integer.class))).thenReturn(Optional.of(TestAllergie.getOneAllergie()));
    try {
			Allergie allergie = rca.getOne(PID).get();
			assertThatNoException();
			assertThat(allergie).isNotNull();
			assertThat(allergie.getLibelle()).isEqualTo(TestAllergie.getOneAllergie().getLibelle());
		} catch (ErreurAllergie e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreate()
	{
		when(sa.save(any(Allergie.class))).thenReturn(TestAllergie.getOneAllergie());

		try {
			Allergie allergie = new Allergie();
			allergie.setLibelle(TestAllergie.getOneAllergie().getLibelle());
			BindingResult result = mock(BindingResult.class);
			Allergie p2 = rca.create(allergie, result);

			assertThatNoException();

			assertThat(p2).isNotNull();
			assertThat(p2.getLibelle()).isEqualTo(TestAllergie.getOneAllergie().getLibelle());
		} catch (ErreurAllergie e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate()
	{
		when(sa.findById(any(Integer.class))).thenReturn(Optional.of(TestAllergie.getOneAllergie()));
		try
		{
			Allergie allergie = rca.getOne(PID).get();
			allergie.setLibelle(TestAllergie.getFewAllergies(NB_MAX_ALLERGIES).get(1).getLibelle());
			when(sa.save(any(Allergie.class))).thenReturn(allergie);
			Allergie p2 = rca.update(allergie, PID);
			assertThatNoException();

			assertThat(p2).isNotNull();

			assertThat(p2.getLibelle()).isEqualTo(TestAllergie.getFewAllergies(NB_MAX_ALLERGIES).get(1).getLibelle());

		} catch (ErreurAllergie e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete()
	{
		when(sa.findById(any(Integer.class))).thenReturn(Optional.of(TestAllergie.getOneAllergie()));
		try
		{
			rca.delete(PID);
			assertThatNoException();
		}
		catch (ErreurAllergie e)
		{
			e.printStackTrace();
		}
	}
}
