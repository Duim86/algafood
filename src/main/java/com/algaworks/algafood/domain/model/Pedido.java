package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String codigo;

  @Column(name = "taxa_frete", nullable = false)
  private BigDecimal taxaFrete;

  @Column(nullable = false)
  private BigDecimal subtotal;

  @Column(nullable = false)
  private BigDecimal valorTotal;

  @CreationTimestamp
  private OffsetDateTime dataCriacao;

  private OffsetDateTime dataConfirmacao;
  private OffsetDateTime dataCancelamento;
  private OffsetDateTime dataEntrega;

  @Enumerated(EnumType.STRING)
  private StatusPedido status = StatusPedido.CRIADO;

  @Embedded
  private Endereco enderecoEntrega;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private FormaDePagamento formaDePagamento;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Restaurante restaurante;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Usuario cliente;

  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
  private List<ItemPedido> itens = new ArrayList<>();

  public void calcularValorTotal() {
    getItens().forEach(ItemPedido::calcularPrecoTotal);

    this.subtotal = getItens().stream()
            .map(ItemPedido::getPrecoTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    this.valorTotal = this.subtotal.add(this.taxaFrete);
  }

  public void confirmar() {
    setStatus(StatusPedido.CONFIRMADO);
    setDataConfirmacao(OffsetDateTime.now());

    registerEvent(new PedidoConfirmadoEvent(this));
  }
  public void entregar() {
    setStatus(StatusPedido.ENTREGUE);
    setDataEntrega(OffsetDateTime.now());
  }
  public void cancelar() {
    setStatus(StatusPedido.CANCELADO);
    setDataCancelamento(OffsetDateTime.now());

    registerEvent(new PedidoCanceladoEvent(this));
  }

  public boolean podeSerConfirmado() {
    return getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
  }

  public boolean podeSerEntregue() {
    return getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
  }

  public boolean podeSerCancelado() {
    return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
  }

  private void setStatus (StatusPedido novoStatus) {
    if(getStatus().naoPodeAlterarPara(novoStatus)) {
      throw new NegocioException(
              "Status do pedido "
                      + getCodigo()
                      + " não pode ser alterado de "
                      + getStatus().getDescricao() + " para "
                      + novoStatus.getDescricao());
    }
    this.status = novoStatus;
  }

  @PrePersist
  private void gerarCodigo() {
    setCodigo(UUID.randomUUID().toString());
  }
}
