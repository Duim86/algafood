package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FormaDePagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;

public interface FormaDePagamentoRepository extends JpaRepository<FormaDePagamento, Long> {

  @Query("select max(dataAtualizacao) from FormaDePagamento")
  OffsetDateTime getDataUltimaAtualizacao();

  @Query("select dataAtualizacao from FormaDePagamento where id = :formaDePagamentoId")
  OffsetDateTime getDataAtualizacaoById(Long formaDePagamentoId);
}
