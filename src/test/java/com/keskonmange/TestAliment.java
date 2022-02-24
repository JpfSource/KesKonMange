package com.keskonmange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
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
	private static Date d = new Date();
	private static List<Aliment> alimentsStatic = TestAliment.getFewAliments(4);

	// STATIC.METHODES
	private static Aliment getOneAliment()
	{
		return TestAliment.getFewAliments(1).get(0);
	}
	private static List<Aliment> getFewAliments(Integer nbAliments) {
		List<Aliment> aliments = new ArrayList<Aliment>();
		
		if(nbAliments >= 1) { aliments.add(new Aliment(1, "3700009215081", "Salade macédoire","", 547.0,"salade", d));}
		if(nbAliments >= 2) { aliments.add(new Aliment(2, "3083680904971", "Poelée paysanne","", 459.0,"poelée", d));}
		if(nbAliments >= 3) { aliments.add(new Aliment(3, "3033210085175", "Moussaka","", 535.0,"moussaka", d));}
		if(nbAliments >= 4) { aliments.add(new Aliment(4, "5430001593013", "Lasagnes bolognaises","", 858.0,"lasagne", d));}
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

		assertThat(aliments.get(0).getImgUrl()).isEqualTo(alimentsStatic.get(0).getImgUrl());
		assertThat(aliments.get(1).getImgUrl()).isEqualTo(alimentsStatic.get(1).getImgUrl());
		assertThat(aliments.get(2).getImgUrl()).isEqualTo(alimentsStatic.get(2).getImgUrl());
		assertThat(aliments.get(3).getImgUrl()).isEqualTo(alimentsStatic.get(3).getImgUrl());

		assertThat(aliments.get(0).getTags()).isEqualTo(alimentsStatic.get(0).getTags());
		assertThat(aliments.get(1).getTags()).isEqualTo(alimentsStatic.get(1).getTags());
		assertThat(aliments.get(2).getTags()).isEqualTo(alimentsStatic.get(2).getTags());
		assertThat(aliments.get(3).getTags()).isEqualTo(alimentsStatic.get(3).getTags());

		assertThat(aliments.get(0).getDateMaj()).isEqualTo(alimentsStatic.get(0).getDateMaj());
		assertThat(aliments.get(1).getDateMaj()).isEqualTo(alimentsStatic.get(1).getDateMaj());
		assertThat(aliments.get(2).getDateMaj()).isEqualTo(alimentsStatic.get(2).getDateMaj());
		assertThat(aliments.get(3).getDateMaj()).isEqualTo(alimentsStatic.get(3).getDateMaj());

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
			assertThat(aliment.getImgUrl()).isEqualTo(TestAliment.getOneAliment().getImgUrl());
			assertThat(aliment.getTags()).isEqualTo(TestAliment.getOneAliment().getTags());
			assertThat(aliment.getDateMaj()).isEqualTo(TestAliment.getOneAliment().getDateMaj());
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
			aliment.setImgUrl(TestAliment.getOneAliment().getImgUrl());
			aliment.setTags(TestAliment.getOneAliment().getTags());
			aliment.setDateMaj(TestAliment.getOneAliment().getDateMaj());
			aliment.setKcal_100g(TestAliment.getOneAliment().getKcal_100g());
			BindingResult result = mock(BindingResult.class);
			Aliment p2 = rca.create(aliment, result);

			assertThatNoException();

			assertThat(p2).isNotNull();
			assertThat(p2.getEan()).isEqualTo(TestAliment.getOneAliment().getEan());
			assertThat(p2.getLibelle()).isEqualTo(TestAliment.getOneAliment().getLibelle());
			assertThat(p2.getImgUrl()).isEqualTo(TestAliment.getOneAliment().getImgUrl());
			assertThat(p2.getTags()).isEqualTo(TestAliment.getOneAliment().getTags());
			assertThat(p2.getDateMaj()).isEqualTo(TestAliment.getOneAliment().getDateMaj());
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
			aliment.setImgUrl(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getImgUrl());
			aliment.setTags(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getTags());
			aliment.setDateMaj(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getDateMaj());
			aliment.setKcal_100g(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getKcal_100g());
			when(sa.save(any(Aliment.class))).thenReturn(aliment);
			Aliment p2 = rca.update(aliment, PID);
			assertThatNoException();

			assertThat(p2).isNotNull();

			assertThat(p2.getEan()).isEqualTo(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getEan());
			assertThat(p2.getLibelle()).isEqualTo(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getLibelle());
			assertThat(p2.getImgUrl()).isEqualTo(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getImgUrl());
			assertThat(p2.getTags()).isEqualTo(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getTags());
			assertThat(p2.getDateMaj()).isEqualTo(TestAliment.getFewAliments(NB_MAX_ALIMENTS).get(1).getDateMaj());
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
