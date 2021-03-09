package com.sysone.devtest.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name="adicionales")
public class Adicionales {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="automovil_id", updatable = true)
	private Automovil automovil;
	
	@SerializedName("opcional")
	@Expose
	private OpcionalesEnum opcional;
	
	private BigDecimal costo;
	
	public Adicionales() {}

	public void calcularCosto() {
	if (this.opcional!= null) {
		this.costo= this.opcional.getPrecio();
	}
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Automovil getAutomovil() {
		return automovil;
	}

	public void setAutomovil(Automovil automovil) {
		this.automovil = automovil;
	}

	public OpcionalesEnum getOpcional() {
		return opcional;
	}

	public void setOpcional(OpcionalesEnum opcional) {
		this.opcional = opcional;
	}

	public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}
	
}
