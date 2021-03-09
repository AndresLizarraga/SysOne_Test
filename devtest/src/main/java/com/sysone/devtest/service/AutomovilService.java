package com.sysone.devtest.service;

import com.sysone.devtest.model.Variante;

public class AutomovilService {
	
	 private int contadorSedan=0;
	 private int contadorCoupe=0;
	 private int contadorFamiliar=0;

	public AutomovilService() {
		this.contadorSedan= 0;
		this.contadorFamiliar=0;
		this.contadorCoupe= 0;
	}
	
	public void contarVariantes(Variante variante) {
		switch (variante) {
		case SEDAN:
			contadorSedan++;
			break;
		case COUPE:
			contadorCoupe++;
			break;
		case FAMILIAR:
			contadorFamiliar++;
			break;
		}
	}
	
	public int getByVariante(Variante variante) {
		if (variante != null) {
		switch (variante) {
		case SEDAN:
			return this.contadorSedan;
		case COUPE:
			return this.contadorCoupe;
		case FAMILIAR:
			return this.contadorFamiliar;
		default:
			return 0;
		}
		}
		else {
			return 0;
		}
	}
	
	public int getContadorSedan() {
		return contadorSedan;
	}

	public void setContadorSedan(int contadorSedan) {
		this.contadorSedan = contadorSedan;
	}

	public int getContadorCoupe() {
		return contadorCoupe;
	}

	public void setContadorCoupe(int contadorCoupe) {
		this.contadorCoupe = contadorCoupe;
	}

	public int getContadorFamiliar() {
		return contadorFamiliar;
	}

	public void setContadorFamiliar(int contadorFamiliar) {
		this.contadorFamiliar = contadorFamiliar;
	}

}
