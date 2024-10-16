package com.bmeza.api_clients.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmeza.api_clients.model.Cliente;
import com.bmeza.api_clients.service.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/clientes")
public class ClienteResource {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<String> crearCliente(@RequestBody Cliente cliente){
        this.service.crearCliente(cliente);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activo/{idPersona}")
    public ResponseEntity<Boolean> buscarClienteActivo(@PathVariable String idPersona){
        Boolean estadoCliente = this.service.buscarClienteActivo(idPersona);
        return ResponseEntity.ok(estadoCliente);
    }

    @GetMapping("/{idPersona}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable String idPersona){
        Cliente cliente = this.service.buscarCliente(idPersona);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping
    public ResponseEntity<String> modificarCliente(@RequestBody Cliente cliente){
        this.service.modificarCliente(cliente);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{idPersona}")
    public ResponseEntity<String> eliminarCliente(@PathVariable String idPersona){
        this.service.eliminarCliente(idPersona);
        return ResponseEntity.ok().build();
    }
}
