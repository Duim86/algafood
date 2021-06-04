package com.algaworks.algafood.domain.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @JoinColumn(nullable = false)
  private String nome;

  @JoinColumn(nullable = false)
  private String descricao;

  @JoinColumn(nullable = false)
  private BigDecimal preco;

  @JoinColumn(nullable = false)
  private boolean Ativo;

  @JoinColumn(nullable = false)
  @ManyToOne
  private Restaurante restaurante;
}
