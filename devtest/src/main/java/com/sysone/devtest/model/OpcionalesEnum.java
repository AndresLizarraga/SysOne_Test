package com.sysone.devtest.model;

import java.math.BigDecimal;

public enum OpcionalesEnum {

	TC("Techo Corredizo", new BigDecimal(12000)),
	AA("Aire Acondicionado", new BigDecimal(20000)),
	ABS("Sistemas de Frenos ABS", new BigDecimal(14000)),
	AB("Airbag", new BigDecimal(7000)),
	CC("Llantas de Aleación", new BigDecimal(12000));
	
	private String nombre;
	private BigDecimal costo;
	
	private OpcionalesEnum(String nombre, BigDecimal precio) {
		this.nombre=nombre;
		this.costo=precio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPrecio() {
		return costo;
	}

	public void setPrecio(BigDecimal precio) {
		this.costo = precio;
	}
	
}
