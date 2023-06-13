package com.aluguelveiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aluguelveiculos.model.OrdemAluguel;

@Repository
public interface OrdemAluguelRepository extends JpaRepository<OrdemAluguel, Long> {
}