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
	
	@OneToMany(mappedBy = "sala")
	private List<Funcion> funcion;
	
	@OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
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


	public List<Butaca> getButaca() {
		return butaca;
	}

	public void setButaca(List<Butaca> butaca) {
		this.butaca = butaca;
	}
	

	public List<Funcion> getFuncion() {
		return funcion;
	}

	public void setFuncion(List<Funcion> funcion) {
		this.funcion = funcion;
	}
	
	
}
