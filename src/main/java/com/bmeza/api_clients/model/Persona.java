package com.bmeza.api_clients.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="persona")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Persona implements Serializable{

    private static final long serialVersionUID = -16568915442358L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column(name = "GENERO", length = 1, nullable = false)
    private String genero;

    @Column(name = "EDAD", nullable = false)
    private Integer edad;

    @Column(name = "IDENTIFICACION", length = 13, nullable = false)
    private String identificacion;

    @Column(name = "DIRECCION", length = 200, nullable = false)
    private String direccion;

    @Column(name = "TELEFONO", length = 10, nullable = false)
    private String telefono;
    
}
