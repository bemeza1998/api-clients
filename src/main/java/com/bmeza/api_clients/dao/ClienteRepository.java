package com.bmeza.api_clients.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmeza.api_clients.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    Optional<Cliente> findByIdCliente(String idCliente);

    Optional<Cliente> findByIdClienteAndEstado(String idCliente, String estado);

    Optional<Cliente> findByIdentificacionOrIdCliente(String identificacion, String idCliente);

}
