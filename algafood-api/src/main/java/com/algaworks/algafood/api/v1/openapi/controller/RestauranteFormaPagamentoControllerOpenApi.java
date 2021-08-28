package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.FormaDePagamentoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

  @ApiOperation("Lista as formas de pagamento associadas a restaurante")
  @ApiResponses({
          @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
  })
  CollectionModel<FormaDePagamentoModel> listar(
          @ApiParam(value = "ID do restaurante", example = "1", required = true)
                  Long restauranteId);


  @ApiOperation("Associação de restaurante com forma de pagamento")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
          @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                  response = Problem.class)
  })
  ResponseEntity<Void> associar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId, @ApiParam(value = "Id de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

  @ApiOperation("Desassociação de restaurante com forma de pagamento")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
          @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                  response = Problem.class)
  })
  ResponseEntity<Void> desassociar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId, @ApiParam(value = "Id de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
