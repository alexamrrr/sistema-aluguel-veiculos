package com.aluguelveiculos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity // Indica que essa classe é uma entidade mapeada para uma tabela no banco de dados
@Table(name = "ordem_aluguel") // Define o nome da tabela no banco de dados
public class OrdemAluguel {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define a estratégia de geração de valor para a chave primária
    private Long id; // Chave primária da entidade OrdemAluguel

    @ManyToOne // Define o relacionamento muitos-para-um com a entidade Cliente
    @JoinColumn(name = "cliente_id") // Define a coluna utilizada para o relacionamento
    private Cliente cliente; // Cliente associado à ordem de aluguel

    @ManyToOne // Define o relacionamento muitos-para-um com a entidade Veiculo
    @JoinColumn(name = "veiculo_id") // Define a coluna utilizada para o relacionamento
    private Veiculo veiculo; // Veículo associado à ordem de aluguel

    private Integer quantidadeDias; 


    public OrdemAluguel() {
        
    }

    public OrdemAluguel(Cliente cliente, Veiculo veiculo, Integer quantidadeDias) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.quantidadeDias = quantidadeDias;
    }

    // Métodos getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Integer getQuantidadeDias() {
        return quantidadeDias;
    }

    public void setQuantidadeDias(Integer quantidadeDias) {
        this.quantidadeDias = quantidadeDias;
    }
}