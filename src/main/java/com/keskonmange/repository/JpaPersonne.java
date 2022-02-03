package com.keskonmange.repository;

import org.springframework.data.repository.CrudRepository;
import com.keskonmange.entities.Personne;

public interface JpaPersonne extends CrudRepository<Personne, Integer> {
}
