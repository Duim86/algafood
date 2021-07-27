package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

  @ApiOperation("Confirma uma pedido por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Pedido confirmado"),
          @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class),
  })
  void confirmar(@ApiParam(value = "Códogio de um pedido", example = "b172992b-37a7-4a07-8e06-cf230bcb186a", required = true) String codigoPedido);


  @ApiOperation("Entrega uma pedido por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Pedido entregue"),
          @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class),
  })
  void entregar(@ApiParam(value = "Códogio de um pedido", example = "b172992b-37a7-4a07-8e06-cf230bcb186a", required = true) String codigoPedido);

  @ApiOperation("Cancela uma pedido por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Pedido cancelado"),
          @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class),
  })
  void cancelar(@ApiParam(value = "Códogio de um pedido", example = "b172992b-37a7-4a07-8e06-cf230bcb186a", required = true) String codigoPedido);
}
