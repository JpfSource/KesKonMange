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

import com.keskonmange.entities.Aliment;
import com.keskonmange.exceptions.ErreurAliment;
import com.keskonmange.restcontrollers.RestControllerAliment;
import com.keskonmange.services.ServiceAliment;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestAliment
{
	/* STATIC */
	// STATIC.DECLARATIONS
	private static final Integer PID = 1;
	private static final Integer NB_MAX_ALIMENTS = 4;
	private static List<Aliment> alimentsStatic = TestAliment.getFewAliments(4);

	// STATIC.METHODES
	private static Aliment getOneAliment()
	{
		return TestAliment.getFewAliments(1).get(0);
	}
	private static List<Aliment> getFewAliments(Integer nbAliments) {
		List<Aliment> aliments = new ArrayList<Aliment>();
		if(nbAliments >= 1) { aliments.add(new Aliment(1, 3700009215081L, "Salade macédoire", 1, 2, 3, 547));}
		if(nbAliments >= 2) { aliments.add(new Aliment(2, 3083680904971L, "Poelée paysanne", 3, 2, 1, 459));}
		if(nbAliments >= 3) { aliments.add(new Aliment(3, 3033210085175L, "Moussaka", 1, 3, 2, 535));}
		if(nbAliments >= 4) { aliments.add(new Aliment(4, 5430001593013L, "Lasagnes bolognaises", 2, 1, 3, 858));}
		return aliments;
	}


	/* NON STATIC */
	// NON STATIC.DECLARATIONS
	@Autowired
	@InjectMocks
	RestControllerAliment rca;

	@Autowired
	@Mock
	ServiceAliment sa;

	// NON STATIC.METHODES.TESTS
	@Test
	public void testGetAll() {

		when(sa.findAll()).thenReturn(TestAliment.getFewAliments(NB_MAX_ALIMENTS));

		List<Aliment> aliments = (List<Aliment>) rca.getAll();

		assertThat(aliments.size()).isEqualTo(NB_MAX_ALIMENTS);
		
		assertThat(aliments.get(0).getEan()).isEqualTo(alimentsStatic.get(0).getEan());
		assertThat(aliments.get(1).getEan()).isEqualTo(alimentsStatic.get(1).getEan());
		assertThat(aliments.get(2).getEan()).isEqualTo(alimentsStatic.get(2).getEan());
		assertThat(aliments.get(3).getEan()).isEqualTo(alimentsStatic.get(3).getEan());

		assertThat(aliments.get(0).getLibelle()).isEqualTo(alimentsStatic.get(0).getLibelle());
		assertThat(aliments.get(1).getLibelle()).isEqualTo(alimentsStatic.get(1).getLibelle());
		assertThat(aliments.get(2).getLibelle()).isEqualTo(alimentsStatic.get(2).getLibelle());
		assertThat(aliments.get(3).getLibelle()).isEqualTo(alimentsStatic.get(3).getLibelle());

		assertThat(aliments.get(0).getNutriscore()).isEqualTo(alimentsStatic.get(0).getNutriscore());
		assertThat(aliments.get(1).getNutriscore()).isEqualTo(alimentsStatic.get(1).getNutriscore());
		assertThat(aliments.get(2).getNutriscore()).isEqualTo(alimentsStatic.get(2).getNutriscore());
		assertThat(aliments.get(3).getNutriscore()).isEqualTo(alimentsStatic.get(3).getNutriscore());

		assertThat(aliments.get(0).getEcoscore()).isEqualTo(alimentsStatic.get(0).getEcoscore());
		assertThat(aliments.get(1).getEcoscore()).isEqualTo(alimentsStatic.get(1).getEcoscore());
		assertThat(aliments.get(2).getEcoscore()).isEqualTo(alimentsStatic.get(2).getEcoscore());
		assertThat(aliments.get(3).getEcoscore()).isEqualTo(alimentsStatic.get(3).getEcoscore());

		assertThat(aliments.get(0).getNovagroupe()).isEqualTo(alimentsStatic.get(0).getNovagroupe());
		assertThat(aliments.get(1).getNovagroupe()).isEqualTo(alimentsStatic.get(1).getNovagroupe());
		assertThat(aliments.get(2).getNovagroupe()).isEqualTo(alimentsStatic.get(2).getNovagroupe());
		assertThat(aliments.get(3).getNovagroupe()).isEqualTo(alimentsStatic.get(3).getNovagroupe());

		assertThat(aliments.get(0).getKcal_100g()).isEqualTo(alimentsStatic.get(0).getKcal_100g());
		assertThat(aliments.get(1).getKcal_100g()).isEqualTo(alimentsStatic.get(1).getKcal_100g());
		assertThat(aliments.get(2).getKcal_100g()).isEqualTo(alimentsStatic.get(2).getKcal_100g());
		assertThat(aliments.get(3).getKcal_100g()).isEqualTo(alimentsStatic.get(3).getKcal_100g());
	}

	@Test
	public void testGetOne()
	{
		when(sa.findById(any(Integer.class))).thenReturn(Optional.of(TestAliment.getOneAliment()));
    try {
			Aliment aliment = rca.getOne(PID).get();
			assertThatNoException();
			assertThat(aliment).isNotNull();
			assertThat(aliment.getEan()).isEqualTo(TestAliment.getOneAliment().getEan());
			assertThat(aliment.getLibelle()).isEqualTo(TestAliment.getOneAliment().getLibelle());
			assertThat(aliment.getNutriscore()).isEqualTo(TestAliment.getOneAliment().getNutriscore());
			assertThat(aliment.getEcoscore()).isEqualTo(TestAliment.getOneAliment().getEcoscore());
			assertThat(aliment.getNovagroupe()).isEqualTo(TestAliment.getOneAliment().getNovagroupe());
			assertThat(aliment.getKcal_100g()).isEqualTo(TestAliment.getOneAliment().getKcal_100g());
		} catch (ErreurAliment e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreate()
	{
		when(sa.save(any(Aliment.class))).thenReturn(TestAliment.getOneAliment());

		try {
			Aliment aliment = new Aliment();
			aliment.setEan(TestAliment.getOneAliment().getEan());
			aliment.setLibelle(TestAliment.getOneAliment().getLibelle());
			aliment.setNutriscore(TestAliment.getOneAliment().getNutriscore());
			aliment.setEcoscore(TestAliment.getOneAliment().getEcoscore());
			aliment.setNovagroupe(TestAliment.getOneAliment().getNovagroupe());
			aliment.setKcal_100g(TestAliment.getOneAliment().getKcal_100g());
			BindingResult result = mock(BindingResult.class);
			Aliment p2 = rca.create(aliment, result);

			assertThatNoException();

			assertThat(p2).isNotNull();
			assertThat(p2.getEan()).isEqualTo(TestAliment.getOneAliment().getEan());
			assertThat(p2.getLibelle()).isEqualTo(TestAliment.getOneAliment().getLibelle());
			assertThat(p2.getNutriscore()).isEqualTo(TestAliment.getOneAliment().getNutriscore());
			assertThat(p2.getEcoscore()).isEqualTo(TestAliment.getOneAliment().getEcoscore());
			assertThat(p2.getNovagroupe()).isEqualTo(TestAliment.getOneAliment().getNovagroupe());
			assertThat(p2.getKcal_100g()).isEqualTo(TestAliment.getOneAliment().getKcal_100g());
		} catch (ErreurAliment e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate()
	{
		when(sa.findById(any(Integer.class))).thenReturn(Optional.of(TestAliment.getOneAliment()));
		try
		{
			Aliment aliment = rca.getOne(PID).get();
			aliment.setEan(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getEan());
			aliment.setLibelle(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getLibelle());
			aliment.setNutriscore(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getNutriscore());
			aliment.setEcoscore(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getEcoscore());
			aliment.setNovagroupe(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getNovagroupe());
			aliment.setKcal_100g(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getKcal_100g());
			when(sa.save(any(Aliment.class))).thenReturn(aliment);
			Aliment p2 = rca.update(aliment, PID);
			assertThatNoException();

			assertThat(p2).isNotNull();

			assertThat(p2.getEan()).isEqualTo(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getEan());
			assertThat(p2.getLibelle()).isEqualTo(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getLibelle());
			assertThat(p2.getNutriscore()).isEqualTo(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getNutriscore());
			assertThat(p2.getEcoscore()).isEqualTo(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getEcoscore());
			assertThat(p2.getNovagroupe()).isEqualTo(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getNovagroupe());
			assertThat(p2.getKcal_100g()).isEqualTo(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getKcal_100g());

		} catch (ErreurAliment e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete()
	{
		when(sa.findById(any(Integer.class))).thenReturn(Optional.of(TestAliment.getOneAliment()));
		try
		{
			rca.delete(PID);
			assertThatNoException();
		}
		catch (ErreurAliment e)
		{
			e.printStackTrace();
		}
	}
}
