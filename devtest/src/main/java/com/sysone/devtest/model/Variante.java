package com.sysone.devtest.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Variante {
	
	SEDAN("Sedan",new BigDecimal(230000)),
	COUPE("Coupe",new BigDecimal(270000)),
	FAMILIAR("Familiar",new BigDecimal(245000)) ;
	
	private String nombre;
	private final BigDecimal costo;
	
	private Variante(String nombre, BigDecimal costo) {
		this.nombre=nombre;
		this.costo=costo;
	}
	
    public String getVariante() {
        return this.nombre;
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
	
}
