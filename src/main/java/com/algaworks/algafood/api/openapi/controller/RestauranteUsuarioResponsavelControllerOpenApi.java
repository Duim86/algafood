package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioModel;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

  @ApiOperation("Lista os usuários associados ao restaurante")
  @ApiResponses({
          @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
  })
  List<UsuarioModel> listar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId);

  @ApiOperation("Associação de restaurante com usuário")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
          @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado",
                  response = Problem.class)
  })
  void associar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId, @ApiParam(value = "Id de um usuário", example = "1", required = true) Long usuarioId);

  @ApiOperation("Desassociação de restaurante com usuário")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
          @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado",
                  response = Problem.class)
  })
  void desassociar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId, @ApiParam(value = "Id de um usuário", example = "1", required = true) Long usuarioId);
}
