package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(name = "taxa_frete", nullable = false)
  private BigDecimal taxaFrete;

  @ManyToOne
  @JoinColumn(name = "cozinha_id", nullable = false)
  private Cozinha cozinha;

  @Embedded
  private Endereco endereco;

  private Boolean ativo = Boolean.TRUE;

  @CreationTimestamp
  @Column(nullable = false, columnDefinition = "datetime", updatable = false)
  private OffsetDateTime dataCadastro;

  @UpdateTimestamp
  @Column(nullable = false, columnDefinition = "datetime")
  private OffsetDateTime dataAtualizacao;

  @ManyToMany
  @JoinTable(name = "restaurante_forma_pagamento",
          joinColumns = @JoinColumn(name = "restaurante_id"),
          inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
  private Set<FormaDePagamento> formasDePagamento = new HashSet<>();

  @OneToMany(mappedBy = "restaurante")
  //to Many é padrão Lazy
  private List<Produto> produtos = new ArrayList<>();

  public void ativar(){
    setAtivo(true);
  }

  public void inativar(){
    setAtivo(false);
  }

  public void adicionarFormaPagamento(FormaDePagamento formaDePagamento) {
    getFormasDePagamento().add(formaDePagamento);
  }

  public void removerFormaPagamento(FormaDePagamento formaDePagamento) {
    getFormasDePagamento().remove(formaDePagamento);
  }
}