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
//
//{
//        "restaurante": {
//        "id": 1
//        },
//        "formaPagamento": {
//        "id": 1
//        },
//        "enderecoEntrega": {
//        "cep": "38400-000",
//        "logradouro": "",
//        "numero": "600",
//        "complmento": "123",
//        "bairro": "Lancaster",
//        "cidade": {
//        "id": 1
//        }
//        },
//        "itens": [
//        {
//        "produtoId": 1,
//        "quantidade": 3,
//        "observacao": "Sem pimenta"
//        },
//        {
//        "produtoId": 1,
//        "quantidade": 3
//        }
//        ]
//        }