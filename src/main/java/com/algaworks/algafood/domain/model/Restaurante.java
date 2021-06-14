package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ValorZeroIncluiDescricao(valorField = "taxaFrete",
        descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String nome;

  @PositiveOrZero
  //@TaxaFrete
  //@Multiplo(numero = 5)
  @NotNull
  @Column(name = "taxa_frete", nullable = false)
  private BigDecimal taxaFrete;

  @Valid
  @NotNull
  @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
  @ManyToOne
  @JoinColumn(name = "cozinha_id", nullable = false)
  private Cozinha cozinha;

  @Embedded
  private Endereco endereco;

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
  private List<FormaDePagamento> formasDePagamento = new ArrayList<>();

  @OneToMany(mappedBy = "restaurante")
  //to Many é padrão Lazy
  private List<Produto> produtos = new ArrayList<>();
}