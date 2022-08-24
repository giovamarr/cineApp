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
	@Query("SELECT distinct fun.fechaFuncion from Funcion fun where fun.pelicula.id = ?1 and fun.fechaFuncion >= DATE(NOW())")
	List<LocalDate> findByMovieId(Integer pelicula_id);
	
	@Query("SELECT fun from Funcion fun where fun.pelicula.id = ?1 and fun.fechaFuncion = ?2 and fun.fechaFuncion >= DATE(NOW())")
	List<Funcion> findByMovieIdAndDate(Integer pelicula_id, LocalDate fechaFuncion);
	
	@Query("SELECT fun from Funcion fun where fun.fechaFuncion=?1 and fun.sala.id=?2")
	List<Funcion> findByDateAndSala(LocalDate fechaFuncion, Integer sala_id);
	
	@Query("SELECT fun from Funcion fun where fun.fechaFuncion >= DATE(NOW())")
	List<Funcion> findAllAfterToday();

	@Query("SELECT fun from Funcion fun where fun.fechaFuncion >= DATE(NOW()) and fun.sala.id=?1")
	List<Funcion> findBySalaAfterToday(Integer sala_id);
}
