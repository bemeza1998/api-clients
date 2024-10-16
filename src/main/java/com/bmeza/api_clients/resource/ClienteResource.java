package com.bmeza.api_clients.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/activo/{idCliente}")
    public ResponseEntity<Boolean> buscarClienteActivo(@PathVariable String idCliente){
        Boolean estadoCliente = this.service.buscarClienteActivo(idCliente);
        return ResponseEntity.ok(estadoCliente);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable String idCliente){
        Cliente cliente = this.service.buscarCliente(idCliente);
        return ResponseEntity.ok(cliente);
    }

    @PatchMapping
    public ResponseEntity<String> modificarCliente(@RequestBody Cliente cliente){
        this.service.modificarCliente(cliente);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<String> eliminarCliente(@RequestBody String idCliente){
        this.service.eliminarCliente(idCliente);
        return ResponseEntity.ok().build();
    }
}
