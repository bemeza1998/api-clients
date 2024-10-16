package com.bmeza.api_clients.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bmeza.api_clients.dao.ClienteRepository;
import com.bmeza.api_clients.enums.EstadoClienteEnum;
import com.bmeza.api_clients.exception.CustomerNotActiveException;
import com.bmeza.api_clients.exception.NotFoundException;
import com.bmeza.api_clients.model.Cliente;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setNombre("Bryan Meza");
        cliente.setGenero("M");
        cliente.setEdad(26);
        cliente.setIdentificacion("1727432534");
        cliente.setDireccion("Quitumbe");
        cliente.setTelefono("2845410");
        cliente.setIdPersona("CL-00003");
        cliente.setContrasena("passwd");
        cliente.setEstado(EstadoClienteEnum.ACTIVO.getValor());
    }


    @Test
    public void crearCliente_guardarCliente() {

        clienteService.crearCliente(cliente);

        verify(repository).save(cliente);
        
    }

    @Test
    public void buscarCliente_clienteExistente_retornarCliente() throws Exception {

        when(repository.findByIdPersona(cliente.getIdPersona())).thenReturn(Optional.of(cliente));

        Cliente clienteEncontrado = clienteService.buscarCliente(cliente.getIdPersona());

        verify(repository).findByIdPersona(cliente.getIdPersona());

        assertEquals(clienteEncontrado, cliente);
    }

    @Test
    public void buscarCliente_clienteNoExistente_lanzarNotFoundException() throws Exception {

        when(repository.findByIdPersona(cliente.getIdPersona())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> clienteService.buscarCliente(cliente.getIdPersona()));

        verify(repository).findByIdPersona(cliente.getIdPersona());
    }

    @Test
    void buscarClienteActivo_clienteActivo_retornarTrue() {

        cliente.setEstado(EstadoClienteEnum.ACTIVO.getValor());

        when(repository.findByIdPersona(cliente.getIdPersona())).thenReturn(Optional.of(cliente));

        Boolean resultado = clienteService.buscarClienteActivo(cliente.getIdPersona());

        assertTrue(resultado);
        verify(repository, times(1)).findByIdPersona(cliente.getIdPersona());
    }

    @Test
    void buscarClienteActivo_clienteInactivo_lanzarCustomerNotActiveException() {

        cliente.setEstado(EstadoClienteEnum.BLOQUEADO.getValor());

        when(repository.findByIdPersona(cliente.getIdPersona())).thenReturn(Optional.of(cliente));

        CustomerNotActiveException exception = assertThrows(CustomerNotActiveException.class, 
            () -> clienteService.buscarClienteActivo((cliente.getIdPersona())));

        assertEquals("El cliente Bryan Meza no puede realizar transacciones.", exception.getMessage());
        verify(repository, times(1)).findByIdPersona((cliente.getIdPersona()));

    }

    @Test
    public void modificarCliente_clienteExistente_actualizarCliente() throws Exception {

        cliente.setNombre("Esteban");
        cliente.setDireccion("Cuenca");
        cliente.setEdad(33);


        when(repository.findByIdPersona(cliente.getIdPersona())).thenReturn(Optional.of(new Cliente()));

        clienteService.modificarCliente(cliente);
        
        verify(repository).findByIdPersona(cliente.getIdPersona());

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
        verify(repository).save(clienteCaptor.capture());
        Cliente clienteModificado = clienteCaptor.getValue();

        assertEquals(clienteModificado.getNombre(), "Esteban");
        assertEquals(clienteModificado.getDireccion(), "Cuenca");
        assertEquals(clienteModificado.getEdad(), 33);

    }

    @Test
    public void eliminarCliente_clienteExistente_eliminarCliente() throws Exception {

        when(repository.findByIdPersona(cliente.getIdPersona())).thenReturn(Optional.of(new Cliente()));

        clienteService.eliminarCliente(cliente.getIdPersona());

        verify(repository).findByIdPersona(cliente.getIdPersona());
        verify(repository).delete(any(Cliente.class));
    }
}