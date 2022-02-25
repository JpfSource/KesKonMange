package com.keskonmange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import com.keskonmange.entities.Groupe;
import com.keskonmange.entities.Repas;
import com.keskonmange.enums.TypeRepas;
import com.keskonmange.exceptions.ErreurRepas;
import com.keskonmange.restcontrollers.RestControllerRepas;
import com.keskonmange.services.ServiceRepas;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestRepas
{
	/* STATIC */
	// STATIC.DECLARATIONS
	private static final Integer PID = 1;
	private static final Integer NB_MAX_REPAS = 4;
	private static List<Repas> repasStatic = TestRepas.getFewRepas(4);

	// STATIC.METHODES
	private static Repas getOneRepas()
	{
		return TestRepas.getFewRepas(1).get(0);
	}

	private static List<Repas> getFewRepas(Integer nbRepas)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<Repas> repas = new ArrayList<Repas>();
		if(nbRepas >= 1)
		{
			repas.add(new Repas(1, LocalDate.now(), "Déjeuner", TypeRepas.DEJEUNER, new Groupe(), new Repas()));
		}
		if(nbRepas >= 2)
		{
			repas.add(new Repas(2, LocalDate.now(), "Diner", TypeRepas.DINER, new Groupe(), new Repas()));
		}
		if(nbRepas >= 3)
		{
			repas.add(new Repas(3, LocalDate.now(), "Petit déjeuner", TypeRepas.PETIT_DEJEUNER, new Groupe(), new Repas()));
		}
		if(nbRepas >= 4)
		{
			repas.add(new Repas(4, LocalDate.now(), "Déjeuner", TypeRepas.DEJEUNER, new Groupe(), new Repas()));
		}
		return repas;
	}
	/* NON STATIC */

	// NON STATIC.DECLARATIONS
	@Autowired
	@InjectMocks
	RestControllerRepas rcr;
	@Autowired
	@Mock
	ServiceRepas sr;

	// NON STATIC.METHODES.TESTS
	@Test
	public void testGetAll()
	{
		when(sr.findAll()).thenReturn(TestRepas.getFewRepas(NB_MAX_REPAS));
		List<Repas> repas = (List<Repas>) rcr.getAll();
		assertThat(repas.size()).isEqualTo(NB_MAX_REPAS);
		assertThat(repas.get(0).getTypeRepasLibelle()).isEqualTo(repasStatic.get(0).getTypeRepasLibelle());
		assertThat(repas.get(1).getTypeRepasLibelle()).isEqualTo(repasStatic.get(1).getTypeRepasLibelle());
		assertThat(repas.get(2).getTypeRepasLibelle()).isEqualTo(repasStatic.get(2).getTypeRepasLibelle());
		assertThat(repas.get(3).getTypeRepasLibelle()).isEqualTo(repasStatic.get(3).getTypeRepasLibelle());
	}

	@Test
	public void testGetOne()
	{
		when(sr.findById(any(Integer.class))).thenReturn(Optional.of(TestRepas.getOneRepas()));
		try
		{
			Repas repas = rcr.getOne(PID).get();
			assertThatNoException();
			assertThat(repas).isNotNull();
			assertThat(repas.getTypeRepasLibelle()).isEqualTo(TestRepas.getOneRepas().getTypeRepasLibelle());
			assertThat(repas.getTypeRepasLibelle()).isEqualTo(TestRepas.getOneRepas().getTypeRepasLibelle());
		}
		catch (ErreurRepas e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testCreate()
	{
		when(sr.save(any(Repas.class))).thenReturn(TestRepas.getOneRepas());
		try
		{
			Repas repas = new Repas();
			repas.setTypeRepasLibelle(TestRepas.getOneRepas().getTypeRepasLibelle());
			BindingResult result = mock(BindingResult.class);
			Repas p2 = rcr.create(repas, result);
			assertThatNoException();
			assertThat(p2).isNotNull();
			assertThat(p2.getTypeRepasLibelle()).isEqualTo(TestRepas.getOneRepas().getTypeRepasLibelle());
		}
		catch (ErreurRepas e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate()
	{
		when(sr.findById(any(Integer.class))).thenReturn(Optional.of(TestRepas.getOneRepas()));
		try
		{
			Repas repas = rcr.getOne(PID).get();
			repas.setTypeRepasLibelle(TestRepas.getFewRepas(NB_MAX_REPAS).get(1).getTypeRepasLibelle());
			when(sr.save(any(Repas.class))).thenReturn(repas);
			Repas p2 = rcr.update(repas, PID);
			assertThatNoException();
			assertThat(p2).isNotNull();
			assertThat(p2.getTypeRepasLibelle())
					.isEqualTo(TestRepas.getFewRepas(NB_MAX_REPAS).get(1).getTypeRepasLibelle());
		}
		catch (ErreurRepas e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete()
	{
		when(sr.findById(any(Integer.class))).thenReturn(Optional.of(TestRepas.getOneRepas()));
		try
		{
			rcr.delete(PID);
			assertThatNoException();
		}
		catch (ErreurRepas e)
		{
			e.printStackTrace();
		}
	}
}
