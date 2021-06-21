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
public class ItemPedido {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Integer quantidade;

  @Column(nullable = false)
  private BigDecimal precoUnitario;

  @Column(nullable = false)
  private BigDecimal precoTotal;

  @Column(nullable = false)
  private String observacao;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Pedido pedido;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Produto produto;

  public void calcularPrecoTotal() {
    BigDecimal precoUnitario = this.getPrecoUnitario();
    Integer quantidade = this.getQuantidade();

    setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
  }
}
