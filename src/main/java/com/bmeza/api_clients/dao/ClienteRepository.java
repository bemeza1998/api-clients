package com.bmeza.api_clients.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmeza.api_clients.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String>{

    Optional<Cliente> findByIdPersona(String idPersona);

    Optional<Cliente> findByIdentificacionOrIdPersonaOrIdCliente(String identificacion, String idPersona, Long idCliente);

}
