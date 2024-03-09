package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="libro")
public class Libro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idlibro", nullable = false)
	public Long idlibro;
	
	@Column(name="nombre", nullable = false)

	private String nombre;
	
	@Column(name="autor", nullable = false)

    private String autor;
	
	@Column(name="fechaPublicacion", nullable = false)

    private Date fechaPublicacion;
	
	@ManyToOne
	@JoinColumn(name = "idgenero", nullable = false)
    private Genero genero;
	

	public Libro() {
		super();
	}

	public Libro(Long idlibro, String nombre, String autor, Date fechaPublicacion, Genero genero) {
		super();
		this.idlibro = idlibro;
		this.nombre = nombre;
		this.autor = autor;
		this.fechaPublicacion = fechaPublicacion;
		this.genero = genero;
	}

	public Long getIdlibro() {
		return idlibro;
	}

	public void setIdlibro(Long idlibro) {
		this.idlibro = idlibro;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	
}
