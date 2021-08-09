package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {
  @ApiOperation("Pesquisa os pedidos por filtro")
  @ApiImplicitParams({
          @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                  name = "campos", paramType = "query", type = "string")
  })
  PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro,
                                          Pageable pageable);

  @ApiOperation("Busca um pedido por codigo do Pedido")
  @ApiResponses({
          @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
  })
  @ApiImplicitParams({
          @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                  name = "campos", paramType = "query", type = "string")
  })
  PedidoModel buscar(@ApiParam(value = "Código do pedido", example = "b172992b-37a7-4a07-8e06-cf230bcb186a", required = true) String codigoPedido);

  @ApiOperation("Cadastra um pedido")
  @ApiResponses({
          @ApiResponse(code = 201, message = "Pedido cadastrado"),
  })

  @ApiParam(name = "corpo", value = "Representação de um novo pedido")
  PedidoModel salvar(@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) PedidoInput pedidoInput);
}
