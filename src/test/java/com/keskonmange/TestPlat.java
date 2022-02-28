package com.keskonmange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import com.keskonmange.entities.Plat;
import com.keskonmange.entities.Utilisateur;
import com.keskonmange.enums.TypePlat;
import com.keskonmange.exceptions.ErreurPlat;
import com.keskonmange.restcontrollers.RestControllerPlat;
import com.keskonmange.services.ServicePlat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestPlat
{
	/* STATIC */
	// STATIC.DECLARATIONS
	private static final Integer PID = 1;
	private static final Integer NB_MAX_PLATS = 4;
	private static List<Plat> platsStatic = TestPlat.getFewPlats(4);

	// STATIC.METHODES
	private static Plat getOnePlat()
	{
		return TestPlat.getFewPlats(1).get(0);
	}

	private static List<Plat> getFewPlats(Integer nbPlats)
	{
		
		List<Plat> plats = new ArrayList<Plat>();

		if(nbPlats >= 1)
		{
			plats.add(new Plat(1, "LASAGNE", "Plat principal", TypePlat.PLAT_PRINCIPAL, new Utilisateur()));
		}
		if(nbPlats >= 2)
		{
			plats.add(new Plat(2, "BOEUF_BOURGUIGNON", "Plat principal", TypePlat.PLAT_PRINCIPAL, new Utilisateur()));
		}
		if(nbPlats >= 3)
		{
			plats.add(new Plat(3, "CAROTTES_RAPEES", "Entrée", TypePlat.ENTREE, new Utilisateur()));
		}
		if(nbPlats >= 4)
		{
			plats.add(new Plat(4, "TIRAMISU", "Dessert", TypePlat.DESSERT, new Utilisateur()));
		}
		
		return plats;
	}
	/* NON STATIC */

	// NON STATIC.DECLARATIONS
	@Autowired
	@InjectMocks
	RestControllerPlat rcpl;
	@Autowired
	@Mock
	ServicePlat spl;

	// NON STATIC.METHODES.TESTS
	@Test
	public void testGetAll()
	{
		when(spl.findAll()).thenReturn(TestPlat.getFewPlats(NB_MAX_PLATS));
		List<Plat> plats = (List<Plat>) rcpl.getAll();
		assertThat(plats.size()).isEqualTo(NB_MAX_PLATS);
		assertThat(plats.get(0).getLibelle()).isEqualTo(platsStatic.get(0).getLibelle());
		assertThat(plats.get(1).getLibelle()).isEqualTo(platsStatic.get(1).getLibelle());
		assertThat(plats.get(2).getLibelle()).isEqualTo(platsStatic.get(2).getLibelle());
		assertThat(plats.get(3).getLibelle()).isEqualTo(platsStatic.get(3).getLibelle());
	}

	@Test
	public void testGetOne()
	{
		when(spl.findById(any(Integer.class))).thenReturn(Optional.of(TestPlat.getOnePlat()));
		try
		{
			Plat plat = rcpl.getOne(PID).get();
			assertThatNoException();
			assertThat(plat).isNotNull();
			assertThat(plat.getLibelle()).isEqualTo(TestPlat.getOnePlat().getLibelle());
		}
		catch (ErreurPlat e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testCreate()
	{
		when(spl.save(any(Plat.class))).thenReturn(TestPlat.getOnePlat());
		try
		{
			Plat plat = new Plat();
			plat.setLibelle(TestPlat.getOnePlat().getLibelle());
			BindingResult result = mock(BindingResult.class);
			Plat plat2 = rcpl.create(plat, result);
			assertThatNoException();
			assertThat(plat2).isNotNull();
			assertThat(plat2.getLibelle()).isEqualTo(TestPlat.getOnePlat().getLibelle());
		}
		catch (ErreurPlat e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate()
	{
		when(spl.findById(any(Integer.class))).thenReturn(Optional.of(TestPlat.getOnePlat()));
		try
		{
			Plat plat = rcpl.getOne(PID).get();
			plat.setLibelle(TestPlat.getFewPlats(NB_MAX_PLATS).get(1).getLibelle());
			when(spl.save(any(Plat.class))).thenReturn(plat);
			Plat p2 = rcpl.update(plat, PID);
			assertThatNoException();
			assertThat(p2).isNotNull();
			assertThat(p2.getLibelle()).isEqualTo(TestPlat.getFewPlats(NB_MAX_PLATS).get(1).getLibelle());
		}
		catch (ErreurPlat e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete()
	{
		when(spl.findById(any(Integer.class))).thenReturn(Optional.of(TestPlat.getOnePlat()));
		try
		{
			rcpl.delete(PID);
			assertThatNoException();
		}
		catch (ErreurPlat e)
		{
			e.printStackTrace();
		}
	}
}
