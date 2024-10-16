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

    public void crearCliente(Cliente cliente){
        
        Optional<Cliente> clienteExistente = this.repository.findByIdentificacionOrIdCliente(cliente.getIdentificacion(), cliente.getIdCliente());
        if (!clienteExistente.isPresent()) {
            this.repository.save(cliente);
        } else {
            throw new ExistingClientException("El cliente con cédula " + cliente.getIdentificacion() + " o " + cliente.getIdCliente() + " ya existe");
        }

    }

    public Cliente buscarCliente(String idCliente){
        Optional<Cliente> clienteOpt = this.repository.findByIdCliente(idCliente);
        return clienteOpt.orElseThrow(
            () -> new NotFoundException("Cliente no encontrado")
        );
    }

    public Boolean buscarClienteActivo(String idCliente){
        Cliente cliente = this.buscarClientePorIdCliente(idCliente);
        if (cliente.getEstado().equals(EstadoClienteEnum.ACTIVO.getValor())) {
            return true;
        } else {
            throw new CustomerNotActiveException("El cliente " + cliente.getNombre() + " no puede realizar transacciones." );
        }

    }

    private Cliente buscarClientePorIdCliente(String idCliente){
        Optional<Cliente> clienteOpt = this.repository.findByIdCliente(idCliente);
        return clienteOpt.orElseThrow(
            () -> new NotFoundException("Cliente no encontrado")
        );
    } 

    public void modificarCliente(Cliente cliente){
        Optional<Cliente> clienteOpt = this.repository.findById(cliente.getId());
        if (clienteOpt.isPresent()) {
            Cliente clienteBd = clienteOpt.get();
            clienteBd.setNombre(cliente.getNombre());
            clienteBd.setGenero(cliente.getGenero());
            clienteBd.setEdad(cliente.getEdad());
            clienteBd.setIdentificacion(cliente.getIdentificacion());
            clienteBd.setDireccion(cliente.getDireccion());
            clienteBd.setTelefono(cliente.getTelefono());
            clienteBd.setIdCliente(cliente.getIdCliente());
            clienteBd.setContrasena(cliente.getContrasena());
            clienteBd.setEstado(cliente.getEstado());

            this.repository.save(clienteBd);
        } else {
            throw new NotFoundException("Cliente no encontrado");
        }

    }

    public void eliminarCliente(String idCliente){
        Optional<Cliente> clienteOpt = this.repository.findByIdCliente(idCliente);
        if (clienteOpt.isPresent()) {
            this.repository.delete(clienteOpt.get());
        } else {
            throw new NotFoundException("Cliente no encontrado");
        }
    }
    
}
