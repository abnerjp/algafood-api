package com.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algafood.domain.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends CustomJpaRepository<FormaPagamento, Long> {

}
