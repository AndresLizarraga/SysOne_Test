package com.sysone.devtest.service;

import com.sysone.devtest.model.OpcionalesEnum;

public class AdicionalesService {

	private int contadorTc = 0;
	private int contadorAa = 0;
	private int contadorAbs = 0;
	private int contadorAb = 0;
	private int contadorCc = 0;
	
	public AdicionalesService() {
		this.contadorTc=0;
		this.contadorAa=0;
		this.contadorAbs=0;
		this.contadorAb=0;
		this.contadorCc=0;
	}
	
	public void contarAdicionales(OpcionalesEnum op) {
		switch (op) {
		case TC:
			contadorTc++;
			break;
		case AA:
			contadorAa++;
			break;
		case ABS:
			contadorAbs++;
			break;
		case AB:
			contadorAb++;
			break;
		case CC:
			contadorCc++;
			break;
		}
	}
	
	public int getByAdicional(OpcionalesEnum op) {
		if (op != null) {
		switch (op) {
		case TC:
			return this.contadorTc;
		case AA:
			return this.contadorAa;
		case ABS:
			return this.contadorAbs;
		case AB:
			return this.contadorAb;
		case CC:
			return this.contadorCc;
		default:
			return 0;
		}
		}
		else {
			return 0;
		}
	}

	public int getContadorTc() {
		return contadorTc;
	}

	public void setContadorTc(int contadorTc) {
		this.contadorTc = contadorTc;
	}

	public int getContadorAa() {
		return contadorAa;
	}

	public void setContadorAa(int contadorAa) {
		this.contadorAa = contadorAa;
	}

	public int getContadorAbs() {
		return contadorAbs;
	}

	public void setContadorAbs(int contadorAbs) {
		this.contadorAbs = contadorAbs;
	}

	public int getContadorAb() {
		return contadorAb;
	}

	public void setContadorAb(int contadorAb) {
		this.contadorAb = contadorAb;
	}

	public int getContadorCc() {
		return contadorCc;
	}

	public void setContadorCc(int contadorCc) {
		this.contadorCc = contadorCc;
	}
	
}
