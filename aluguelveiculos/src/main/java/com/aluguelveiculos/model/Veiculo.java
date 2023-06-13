package com.aluguelveiculos.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Indica que essa classe é uma entidade mapeada para uma tabela no banco de dados
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define a estratégia de geração de valor para a chave primária
    private Long id; // Chave primária da entidade Veiculo

    private String modelo;
    private String marca;
    private String placa;
    private BigDecimal valorDiaria;

    // Métodos getters e setters para cada propriedade da entidade Veiculo

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(BigDecimal valorDiaria) {
        this.valorDiaria = valorDiaria;
    }
}