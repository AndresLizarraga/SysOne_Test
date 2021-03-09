package com.sysone.devtest.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity
@Table(name="automovil")
public class Automovil {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@SerializedName("modelo")
	@Expose
	private String modelo;
	
	@SerializedName("placa")
	@Expose
	private String placa;
	
	private Date fechaFabricacion;
	
	@SerializedName("variante")
	@Expose
	@NotNull(message = "El campo variante no puede ser null.")
	private Variante variante;
	
	@OneToMany (orphanRemoval = true, mappedBy="automovil")
	private List<Adicionales> adicionales;
	
	private BigDecimal costoFabricacion;
	
	/**Constructor**/
	
	public Automovil() {
		Date date = new Date();
		this.fechaFabricacion = date;
	}
	
	/**Métodos de lógica de negocio **/
	public void calcularCostoFabricacion() {
		if (this.variante!= null) {
			this.costoFabricacion= this.variante.getPrecio();
			if(!this.adicionales.isEmpty()) {
				for (Adicionales adicional : this.adicionales) {
					this.costoFabricacion= this.costoFabricacion.add(adicional.getOpcional().getPrecio());
				}	
			}  
		}
	}
	
	/**Getters y Setters **/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getFechaFabricacion() {
		return fechaFabricacion;
	}

	public void setFechaFabricacion(Date fechaFabricacion) {
		this.fechaFabricacion = fechaFabricacion;
	}

	public BigDecimal getCostoFabricacion() {
		return costoFabricacion;
	}

	public void setCostoFabricacion(BigDecimal costoFabricacion) {
		this.costoFabricacion = costoFabricacion;
	}

	public Variante getVariante() {
		return variante;
	}

	public void setVariante(Variante variante) {
		this.variante = variante;
	}
	
	public BigDecimal getPrecioFinal() {
		return costoFabricacion;
	}

	public void setPrecioFinal(BigDecimal precioFinal) {
		this.costoFabricacion = precioFinal;
	}

	public List<Adicionales> getAdicionales() {
		return adicionales;
	}

	public void setAdicionales(List<Adicionales> adicionales) {
		this.adicionales = adicionales;
	}

	public void agregarAdicionales(Adicionales adic)
	{
	    this.adicionales.add(adic);
	}
	
	public void removerAdicionales()
	{
	    this.adicionales.clear();
	}
	
}
