package com.algaworks.algafood.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "taxa_frete", nullable = false)
  private BigDecimal taxaFrete;

  @Column(nullable = false)
  private BigDecimal subTotal;

  @Column(nullable = false)
  private BigDecimal valorTotal;

  @CreationTimestamp
  private LocalDateTime dataCriacao;

  private LocalDateTime dataConfirmacao;
  private LocalDateTime dataCancelamento;
  private LocalDateTime dataEntrega;

  @Enumerated(EnumType.STRING)
  private StatusPedido status;

  @Embedded
  private Endereco enderecoEntrega;

  @ManyToOne
  @JoinColumn(nullable = false)
  private FormaDePagamento formaDePagamento;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Restaurante restaurante;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Usuario cliente;

  @OneToMany(mappedBy = "pedido")
  private List<ItemPedido> itens = new ArrayList<>();

}
