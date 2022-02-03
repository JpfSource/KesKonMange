package com.keskonmange.repository;

import org.springframework.data.repository.CrudRepository;
import com.keskonmange.entities.Aliment;

public interface JpaAliment extends CrudRepository<Aliment, Integer>{

}
