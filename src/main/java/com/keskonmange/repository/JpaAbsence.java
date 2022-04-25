package com.keskonmange.repository;

import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Absence;

public interface JpaAbsence extends CrudRepository<Absence, Integer> {

}
