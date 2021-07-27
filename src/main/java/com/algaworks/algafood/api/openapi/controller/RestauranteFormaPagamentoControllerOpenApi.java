package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaDePagamentoModel;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

  @ApiOperation("Lista as formas de pagamento associadas a restaurante")
  @ApiResponses({
          @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
  })
  List<FormaDePagamentoModel> listar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId);


  @ApiOperation("Associação de restaurante com forma de pagamento")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
          @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                  response = Problem.class)
  })
  void associar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId, @ApiParam(value = "Id de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

  @ApiOperation("Desassociação de restaurante com forma de pagamento")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
          @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                  response = Problem.class)
  })
  void desassociar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId, @ApiParam(value = "Id de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
