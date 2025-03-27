package com.daw.persistence.entities;

import java.time.LocalDate;

import com.daw.persistence.entities.enums.Estado;

public class Tarea {
	private int id;
	private String titulo;
	private String descripcion;
	private LocalDate fechaCreacion;
	private LocalDate fechaVencimiento;
	private Estado estado;
	
	
	
	
	public Tarea(int id, String titulo, String descripcion, LocalDate fechaCreacion, LocalDate fechaVencimiento,
			Estado estado) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.fechaVencimiento = fechaVencimiento;
		this.estado = estado;
	}


	public Tarea() {
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}


	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}


	public Estado getEstado() {
		return estado;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
	

}
