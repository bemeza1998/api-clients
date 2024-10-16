package com.bmeza.api_clients.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cliente")
@EqualsAndHashCode(callSuper = false)
public class Cliente extends Persona{

    @Column(name = "ID_CLIENTE", length = 8, nullable = false)
    private String idCliente;

    @Column(name = "CONTRASENA", length = 30, nullable = false)
    private String contrasena;

    @Column(name = "ESTADO", nullable = false)
    private String estado;
    
}
