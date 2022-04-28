package com.cineApp.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="funciones")
public class Funcion {
	
	public Funcion() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "pelicula_id")
	private Pelicula pelicula;
	
	public LocalTime getHoraFuncion() {
		return horaFuncion;
	}

	public void setHoraFuncion(LocalTime horaFuncion) {
		this.horaFuncion = horaFuncion;
	}

	@OneToMany(mappedBy = "funcion")
	private List<Reserva> Reserva;
	
	@ManyToOne
	@JoinColumn(name = "sala_id")
	private Sala sala;
	
	@Column(nullable = false ,name = "fecha_funcion")
	private LocalDate fechaFuncion;
	
	@Column(nullable = false ,name = "hora_funcion")
	private LocalTime horaFuncion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	@Override
	public String toString() {
		return "Funcion [id=" + id + ", pelicula=" + pelicula + ", sala=" + sala + "]";
	}

	public LocalDate getFechaFuncion() {
		return fechaFuncion;
	}

	public void setFechaFuncion(LocalDate fechaFuncion) {
		this.fechaFuncion = fechaFuncion;
	}

	
}
