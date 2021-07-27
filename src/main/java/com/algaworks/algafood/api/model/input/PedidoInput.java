package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoInput {

  @Valid
  @NotNull
  RestauranteIdInput restaurante;

  @Valid
  @NotNull
  FormaDePagamentoIdInput formaDePagamento;

  @Valid
  @NotNull
  EnderecoInput enderecoEntrega;

  @Size(min = 1)
  @NotNull
  @Valid
  List<ItemPedidoInput> itens;
}