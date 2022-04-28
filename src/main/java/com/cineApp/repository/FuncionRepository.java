package com.cineApp.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cineApp.model.Funcion;


public interface FuncionRepository extends JpaRepository<Funcion, Integer> {
	@Transactional
	@Modifying
	@Query("SELECT fun.fechaFuncion from Funcion fun where fun.pelicula.id = ?1 and fun.fechaFuncion >= DATE(NOW())")
	List<LocalDate> findByMovieId(Integer pelicula_id);
	
	@Transactional
	@Modifying
	@Query("SELECT fun from Funcion fun where fun.pelicula.id = ?1 and fun.fechaFuncion = ?2 and fun.fechaFuncion >= DATE(NOW())")
	List<Funcion> findByMovieIdAndDate(Integer pelicula_id, LocalDate fechaFuncion);

}
