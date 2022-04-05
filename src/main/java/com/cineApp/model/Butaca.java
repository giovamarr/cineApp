package com.cineApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="butacas")
public class Butaca {
	
	public Butaca() {
	
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sala_id")
	private Sala sala;
	
	@Column()
	private boolean state;
	
	public Integer getPosition_x() {
		return position_x;
	}

	public void setPosition_x(Integer position_x) {
		this.position_x = position_x;
	}

	public Integer getPosition_y() {
		return position_y;
	}

	public void setPosition_y(Integer position_y) {
		this.position_y = position_y;
	}

	@Column()
	private Integer position_x;
	
	@Column()
	private Integer position_y;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	
}
