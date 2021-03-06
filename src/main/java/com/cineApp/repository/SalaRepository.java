package com.cineApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cineApp.model.Sala;

public interface SalaRepository extends JpaRepository<Sala, Integer> {
	@Query("SELECT sal from Sala sal where sal.name = ?1")
	Optional<Sala> findByName(String name);

}
