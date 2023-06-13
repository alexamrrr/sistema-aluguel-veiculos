package com.aluguelveiculos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluguelveiculos.model.Cliente;
import com.aluguelveiculos.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Obter todos os clientes
    @GetMapping("")
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    // Criar um novo cliente
    @PostMapping("")
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Obter um cliente pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable(value = "id") Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()) {
            return ResponseEntity.ok().body(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar um cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable(value = "id") Long clienteId,
            @RequestBody Cliente clienteDetails) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()) {
            Cliente updatedCliente = cliente.get();
            updatedCliente.setNomeCompleto(clienteDetails.getNomeCompleto());
            updatedCliente.setCpf(clienteDetails.getCpf());
            updatedCliente.setEmail(clienteDetails.getEmail());
            updatedCliente.setEndereco(clienteDetails.getEndereco());
            return ResponseEntity.ok(clienteRepository.save(updatedCliente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Excluir um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable(value = "id") Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()) {
            clienteRepository.delete(cliente.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}