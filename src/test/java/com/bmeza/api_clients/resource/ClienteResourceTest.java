package com.bmeza.api_clients.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bmeza.api_clients.exception.NotFoundException;
import com.bmeza.api_clients.model.Cliente;
import com.bmeza.api_clients.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ClienteResource.class)
class ClienteResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    private Cliente cliente;
    private ObjectMapper objectMapper;

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
        cliente.setEstado("ACT");

        objectMapper = new ObjectMapper();
    }

    @Test
    void crearCliente_retornarOk() throws Exception {

        String clienteJson = objectMapper.writeValueAsString(cliente);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson)).andReturn();

        verify(clienteService, times(1)).crearCliente(any(Cliente.class));

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void buscarCliente_clienteExistente_retornarCliente() throws Exception {

        when(clienteService.buscarCliente("CL-00003")).thenReturn(cliente);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes/" + cliente.getIdPersona()))
                .andReturn();

        verify(clienteService, times(1)).buscarCliente("CL-00003");

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void buscarCliente_clienteNoExistente_lanzarNotFoundException() throws Exception {

        when(clienteService.buscarCliente("CL-00003")).thenThrow(new NotFoundException("Cliente no encontrado"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes/" + cliente.getIdPersona()))
                .andReturn();

        verify(clienteService, times(1)).buscarCliente("CL-00003");

        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void modificarCliente_clienteExistente_retornarOk() throws Exception {

        String clienteJson = objectMapper.writeValueAsString(cliente);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson)).andReturn();

        verify(clienteService, times(1)).modificarCliente(any(Cliente.class));

        assertEquals(200, mvcResult.getResponse().getStatus());

    }

    @Test
    void eliminarCliente_RetornarOk() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/clientes/eliminar/" + cliente.getIdPersona())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        verify(clienteService, times(1)).eliminarCliente("CL-00003");

        assertEquals(200, mvcResult.getResponse().getStatus());

    }

}