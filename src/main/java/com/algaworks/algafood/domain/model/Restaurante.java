package com.algaworks.algafood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

  //@JsonIgnore
  //@JsonIgnoreProperties("hibernateLazyInitializer")
  @ManyToOne //(fetch = FetchType.LAZY)
  // to One é padrão Eagger
  @JoinColumn(name = "cozinha_id", nullable = false)
  private Cozinha cozinha;

  @JsonIgnore
  @OneToMany(mappedBy = "restaurante")
  //to Many é padrão Lazy
  private List<Produto> produtos = new ArrayList<>();

  @JsonIgnore
  @Embedded
  private Endereco endereco;

  @JsonIgnore
  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime dataCadastro;

  @JsonIgnore
  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime dataAtualizacao;

  //@JsonIgnore
  @ManyToMany
  @JoinTable(name = "restaurante_forma_pagamento",
          joinColumns = @JoinColumn(name = "restaurante_id"),
          inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
  private List<FormaDePagamento> formasDePagamento = new ArrayList<>();
}