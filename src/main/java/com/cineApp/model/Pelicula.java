package com.cineApp.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="peliculas")
public class Pelicula {
	
	public Pelicula() {
		
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false , length = 64)
	private String name;
	
	@Column( length = 300)
	private String description;
	
	@Column
	private Integer duration;
	
	
	@Column( nullable = false , length = 100)
	private String poster;
	
	
	@OneToMany(mappedBy = "pelicula")
	private List<Funcion> funcion;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	@Override
	public String toString() {
		return "Pelicula [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
}
