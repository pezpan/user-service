package com.bigeek.microservices.userservice.model;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@ApiModel(description="Detalles del usuario.")
public class User implements Serializable{

	private static final long serialVersionUID = 4743095498049466247L;
	
	@Id
	@GeneratedValue
	private Integer id; 
	
	@Size(min=3, message="El nombre debe tener al menos 3 caracteres")
	@ApiModelProperty(notes="El nombre debe tener al menos 3 caracteres")
	private String nombre; 
	
	@Min(value=1, message= "La edad debe ser mayor de 1")
	@ApiModelProperty(notes="La edad debe ser mayor de 1")
	private Integer edad;
	
	public User(){
		
	}
	
	public User(Integer id, String nombre, Integer edad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
	}

}
