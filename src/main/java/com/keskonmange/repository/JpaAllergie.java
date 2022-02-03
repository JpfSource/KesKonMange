package com.keskonmange.repository;

import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Allergie;

public interface JpaAllergie extends CrudRepository<Allergie, Integer>{

}
