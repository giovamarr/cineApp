package com.cineApp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="salas")
public class Sala {
	
	public Sala() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = true, length = 25)
	private String name;
	
	@Column(nullable = false)
	private boolean state;


	public Integer getNumber_row() {
		return number_row;
	}

	public void setNumber_row(Integer number_row) {
		this.number_row = number_row;
	}

	public Integer getNumber_column() {
		return number_column;
	}

	public void setNumber_column(Integer number_column) {
		this.number_column = number_column;
	}

	@Column()
	private Integer number_row;
	
	@Column()
	private Integer number_column;
	
	@OneToMany(mappedBy = "sala")
	private List<Funcion> funcion;
	
	@OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Butaca> butaca;

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

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setButaca(List<Butaca> butaca) {
		this.butaca = butaca;
	}
	

	public void setFuncion(List<Funcion> funcion) {
		this.funcion = funcion;
	}
	
	
}
