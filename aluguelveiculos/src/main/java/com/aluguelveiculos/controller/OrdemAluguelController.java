package com.aluguelveiculos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aluguelveiculos.model.Cliente;
import com.aluguelveiculos.model.OrdemAluguel;
import com.aluguelveiculos.model.Veiculo;
import com.aluguelveiculos.repository.ClienteRepository;
import com.aluguelveiculos.repository.OrdemAluguelRepository;
import com.aluguelveiculos.repository.VeiculoRepository;

@RestController
@RequestMapping("/ordens-aluguel")

public class OrdemAluguelController {
    @Autowired
    private OrdemAluguelRepository ordemAluguelRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;
    
    // Cadastrar uma nova ordem de aluguel
    @PostMapping("")
    public ResponseEntity<?> cadastrarOrdemAluguel(@RequestBody OrdemAluguel ordemAluguel) {
        // Verifica se o cliente e o veículo existem
        Optional<Cliente> cliente = clienteRepository.findById(ordemAluguel.getCliente().getId());
        Optional<Veiculo> veiculo = veiculoRepository.findById(ordemAluguel.getVeiculo().getId());
        
        if (cliente.isPresent() && veiculo.isPresent()) {
			// Associa o cliente e o veículo à ordem de aluguel
            ordemAluguel.setCliente(cliente.get());
            ordemAluguel.setVeiculo(veiculo.get());
            ordemAluguelRepository.save(ordemAluguel);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Listar todas as ordens de aluguel
    @GetMapping("")
    public List<OrdemAluguel> listarOrdensAluguel() {
        return ordemAluguelRepository.findAll();
    }
    
    // Buscar uma ordem de aluguel pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarOrdemAluguelPorId(@PathVariable("id") Long id) {
        Optional<OrdemAluguel> ordemAluguel = ordemAluguelRepository.findById(id);
        
        if (ordemAluguel.isPresent()) {
            return new ResponseEntity<>(ordemAluguel.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Atualizar uma ordem de aluguel existente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarOrdemAluguel(@PathVariable("id") Long id, @RequestBody OrdemAluguel ordemAluguel) {
        Optional<OrdemAluguel> ordemAluguelExistente = ordemAluguelRepository.findById(id);
        
        if (ordemAluguelExistente.isPresent()) {
            // Verifica se o cliente e o veículo existem
            Optional<Cliente> cliente = clienteRepository.findById(ordemAluguel.getCliente().getId());
            Optional<Veiculo> veiculo = veiculoRepository.findById(ordemAluguel.getVeiculo().getId());
            
            if (cliente.isPresent() && veiculo.isPresent()) {
                // Associa o cliente e o veículo à ordem de aluguel
                ordemAluguelExistente.get().setCliente(cliente.get());
                ordemAluguelExistente.get().setVeiculo(veiculo.get());
                ordemAluguelExistente.get().setQuantidadeDias(ordemAluguel.getQuantidadeDias());
                ordemAluguelRepository.save(ordemAluguelExistente.get());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Excluir uma ordem de aluguel existente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirOrdemAluguel(@PathVariable("id") Long id) {
        Optional<OrdemAluguel> ordemAluguel = ordemAluguelRepository.findById(id);
        
        if (ordemAluguel.isPresent()) {
            ordemAluguelRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
