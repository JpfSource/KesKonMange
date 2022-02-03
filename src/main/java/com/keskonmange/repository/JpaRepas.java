package com.keskonmange.repository;

import org.springframework.data.repository.CrudRepository;

import com.keskonmange.entities.Repas;

public interface JpaRepas extends CrudRepository<Repas, Integer>
{
}