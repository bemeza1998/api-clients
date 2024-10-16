package com.bmeza.api_clients.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bmeza.api_clients.dao.ClienteRepository;
import com.bmeza.api_clients.enums.EstadoClienteEnum;
import com.bmeza.api_clients.exception.CustomerNotActiveException;
import com.bmeza.api_clients.exception.ExistingClientException;
import com.bmeza.api_clients.exception.NotFoundException;
import com.bmeza.api_clients.model.Cliente;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public void crearCliente(Cliente cliente) {

        Optional<Cliente> clienteExistente = this.repository.findByIdentificacionOrIdPersonaOrIdCliente(
                cliente.getIdentificacion(), cliente.getIdPersona(), cliente.getIdCliente());
        if (!clienteExistente.isPresent()) {
            this.repository.save(cliente);
        } else {
            throw new ExistingClientException("Ya existe un cliente con uno de los siguientes datos: "
                    + cliente.getIdentificacion() + ", " + cliente.getIdPersona() + " o id cliente " + cliente.getIdCliente());
        }

    }

    public Cliente buscarCliente(String idPersona) {
        Optional<Cliente> clienteOpt = this.repository.findByIdPersona(idPersona);
        return clienteOpt.orElseThrow(
                () -> new NotFoundException("Cliente no encontrado"));
    }

    public Boolean buscarClienteActivo(String IdPersona) {
        Cliente cliente = this.buscarClientePorIdPersona(IdPersona);
        if (cliente.getEstado().equals(EstadoClienteEnum.ACTIVO.getValor())) {
            return true;
        } else {
            throw new CustomerNotActiveException(
                    "El cliente " + cliente.getNombre() + " no puede realizar transacciones.");
        }

    }

    private Cliente buscarClientePorIdPersona(String IdPersona) {
        Optional<Cliente> clienteOpt = this.repository.findByIdPersona(IdPersona);
        return clienteOpt.orElseThrow(
                () -> new NotFoundException("Cliente no encontrado"));
    }

    public void modificarCliente(Cliente cliente) {
        Optional<Cliente> clienteOpt = this.repository.findByIdPersona(cliente.getIdPersona());
        if (clienteOpt.isPresent()) {
            Cliente clienteBd = clienteOpt.get();
            clienteBd.setNombre(cliente.getNombre());
            clienteBd.setGenero(cliente.getGenero());
            clienteBd.setEdad(cliente.getEdad());
            clienteBd.setIdentificacion(cliente.getIdentificacion());
            clienteBd.setDireccion(cliente.getDireccion());
            clienteBd.setTelefono(cliente.getTelefono());
            clienteBd.setContrasena(cliente.getContrasena());
            clienteBd.setEstado(cliente.getEstado());

            this.repository.save(clienteBd);
        } else {
            throw new NotFoundException("Cliente no encontrado");
        }

    }

    public void eliminarCliente(String idPersona) {
        Optional<Cliente> clienteOpt = this.repository.findByIdPersona(idPersona);
        if (clienteOpt.isPresent()) {
            this.repository.delete(clienteOpt.get());
        } else {
            throw new NotFoundException("Cliente no encontrado");
        }
    }

}
