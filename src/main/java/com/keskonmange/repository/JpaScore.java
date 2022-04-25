package com.keskonmange.repository;

import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Score;

public interface JpaScore extends CrudRepository<Score, Integer> {

}
