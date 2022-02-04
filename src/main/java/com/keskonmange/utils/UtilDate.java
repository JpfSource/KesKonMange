package com.keskonmange.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class UtilDate {


	public static LocalDate convertToLocalDate(Date dateToConvert) {
		return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
		
	}

	public static Integer calculAge(Date dateNaissanse){
		return calculAge(convertToLocalDate(dateNaissanse));
	}

	public static Integer calculAge(LocalDate dateNaissanse){
		Integer age = 0;
		LocalDate dateDuJour = LocalDate.now();  
		if (dateNaissanse != null && dateDuJour != null){
			age = Period.between(dateNaissanse, dateDuJour).getYears();  
		}
		return age;
	}  
	public static LocalDate getNaissanceFromAge(Integer age){
		/*Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, age * -1);
		return calendar.getTime();
		*/
		LocalDate ld = LocalDate.now();
		return ld.plus(age * -1, ChronoUnit.YEARS);
	}

}
