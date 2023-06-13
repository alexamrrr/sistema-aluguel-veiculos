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

import com.aluguelveiculos.model.Veiculo;
import com.aluguelveiculos.repository.VeiculoRepository;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    // Obter todos os veículos
    @GetMapping("")
    public List<Veiculo> getAllVeiculos() {
        return veiculoRepository.findAll();
    }

    // Criar um novo veículo
    @PostMapping("")
    public Veiculo createVeiculo(@RequestBody Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    // Obter um veículo pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> getVeiculoById(@PathVariable(value = "id") Long veiculoId) {
        Optional<Veiculo> veiculo = veiculoRepository.findById(veiculoId);
        if (veiculo.isPresent()) {
            return ResponseEntity.ok().body(veiculo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar um veículo existente
    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> updateVeiculo(@PathVariable(value = "id") Long veiculoId,
            @RequestBody Veiculo veiculoDetails) {
        Optional<Veiculo> veiculo = veiculoRepository.findById(veiculoId);
        if (veiculo.isPresent()) {
            Veiculo updatedVeiculo = veiculo.get();
            updatedVeiculo.setModelo(veiculoDetails.getModelo());
            updatedVeiculo.setMarca(veiculoDetails.getMarca());
            updatedVeiculo.setPlaca(veiculoDetails.getPlaca());
            updatedVeiculo.setValorDiaria(veiculoDetails.getValorDiaria());
            return ResponseEntity.ok(veiculoRepository.save(updatedVeiculo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Excluir um veículo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVeiculo(@PathVariable(value = "id") Long veiculoId) {
        Optional<Veiculo> veiculo = veiculoRepository.findById(veiculoId);
        if (veiculo.isPresent()) {
            veiculoRepository.delete(veiculo.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}