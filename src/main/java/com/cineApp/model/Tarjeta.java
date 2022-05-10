package com.cineApp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tarjetas")
public class Tarjeta {

	public Tarjeta() {
			
		}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 25)
	private String number_card;
	
	@Column(length = 10)
	private String expiry;
	
	@Column(length = 10)
	private String cvc;
	
	@Column(length = 50)
	private String owner;

	public String getNumberCard() {
		return number_card;
	}

	public void setNumberCard(String numberCard) {
		this.number_card = numberCard;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	
	
}
