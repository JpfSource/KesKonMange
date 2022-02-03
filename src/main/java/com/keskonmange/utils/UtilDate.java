package com.keskonmange.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class UtilDate {


	public static LocalDate convertToLocalDate(Date dateToConvert) {
		return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
		
	}

	public static Integer calculAge(Date dateNaissanse){
		return calculAge(convertToLocalDate(dateNaissanse));
	}

	public static Integer calculAge(LocalDate dateNaissanse){
		System.out.println("*************** => "+ dateNaissanse.toString());
		Integer age = 0;
		LocalDate dateDuJour = LocalDate.now();  
		if (dateNaissanse != null && dateDuJour != null){
			age = Period.between(dateNaissanse, dateDuJour).getYears();  
		}
		return age;
	}  
	public static Date getNaissanceFromAge(Integer age){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, age * -1);
		return calendar.getTime();
	}

}
