package com.cineApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cineApp.model.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {
	@Query("SELECT pel from Pelicula pel where pel.name = ?1")
	Optional<Pelicula> findByName(String name);
	
}
