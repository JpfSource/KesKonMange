package com.keskonmange.enums;

import java.util.stream.Stream;

public enum BesoinEnergetiqueMineur {

	FEMININ_AGE_01(Genre.FEMININ,  1, 80),
	FEMININ_AGE_02(Genre.FEMININ,  2, 81),
	FEMININ_AGE_03(Genre.FEMININ,  3, 77),
	FEMININ_AGE_04(Genre.FEMININ,  4, 74),
	FEMININ_AGE_05(Genre.FEMININ,  5, 72),
	FEMININ_AGE_06(Genre.FEMININ,  6, 69),
	FEMININ_AGE_07(Genre.FEMININ,  7, 67),
	FEMININ_AGE_08(Genre.FEMININ,  8, 64),
	FEMININ_AGE_09(Genre.FEMININ,  9, 61),
	FEMININ_AGE_10(Genre.FEMININ, 10, 58),
	FEMININ_AGE_11(Genre.FEMININ, 11, 55),
	FEMININ_AGE_12(Genre.FEMININ, 12, 52),
	FEMININ_AGE_13(Genre.FEMININ, 13, 49),
	FEMININ_AGE_14(Genre.FEMININ, 14, 47),
	FEMININ_AGE_15(Genre.FEMININ, 15, 45),
	FEMININ_AGE_16(Genre.FEMININ, 16, 44),
	FEMININ_AGE_17(Genre.FEMININ, 17, 44),
	MASCULIN_AGE_01(Genre.MASCULIN,  1, 82),
	MASCULIN_AGE_02(Genre.MASCULIN,  2, 84),
	MASCULIN_AGE_03(Genre.MASCULIN,  3, 80),
	MASCULIN_AGE_04(Genre.MASCULIN,  4, 77),
	MASCULIN_AGE_05(Genre.MASCULIN,  5, 74),
	MASCULIN_AGE_06(Genre.MASCULIN,  6, 73),
	MASCULIN_AGE_07(Genre.MASCULIN,  7, 71),
	MASCULIN_AGE_08(Genre.MASCULIN,  8, 69),
	MASCULIN_AGE_09(Genre.MASCULIN,  9, 67),
	MASCULIN_AGE_10(Genre.MASCULIN, 10, 65),
	MASCULIN_AGE_11(Genre.MASCULIN, 11, 62),
	MASCULIN_AGE_12(Genre.MASCULIN, 12, 60),
	MASCULIN_AGE_13(Genre.MASCULIN, 13, 58),
	MASCULIN_AGE_14(Genre.MASCULIN, 14, 56),
	MASCULIN_AGE_15(Genre.MASCULIN, 15, 53),
	MASCULIN_AGE_16(Genre.MASCULIN, 16, 52),
	MASCULIN_AGE_17(Genre.MASCULIN, 17, 50);

	private Genre genre;
	private Integer age;
	private Integer besoinEnergetique;

	private BesoinEnergetiqueMineur(Genre genre, Integer age, Integer besoinEnergetique) {
		this.genre = genre;
		this.age = age;
		this.besoinEnergetique = besoinEnergetique;
	}

	public Genre getGenre() {
		return genre;
	}

	public Integer getAge() {
		return age;
	}

	public Integer getBesoinEnergetique() {
		return besoinEnergetique;
	}

	public static BesoinEnergetiqueMineur of(Genre genre, Integer age) {
		return Stream.of(BesoinEnergetiqueMineur.values())
				.filter(bem -> bem.getGenre() == genre && bem.getAge() == age)
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}	
}
