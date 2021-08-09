package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {
  @ApiOperation("Lista as cozinhas com paginação")
  PagedModel<CozinhaModel> listar(Pageable pageable);

  @ApiOperation("Busca uma cozinha por Id")
  @ApiResponses({
          @ApiResponse(code = 400, message = "Id da cozinha inválida", response = Problem.class),
          @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
  })
  CozinhaModel buscar(@ApiParam(value = "Id de uma cozinha", example = "1", required = true) Long cozinhaId);

  @ApiOperation("Cadastra uma cozinha")
  @ApiResponses({
          @ApiResponse(code = 201, message = "Cozinha cadastrada"),
  })
  CozinhaModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha") CozinhaInput cozinhaInput);

  @ApiOperation("Atualiza uma cozinha por Id")
  @ApiResponses({
          @ApiResponse(code = 200, message = "Cozinha atualizada"),
          @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
  })
  CozinhaModel atualizar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha") CozinhaInput cozinhaInput,
                         @ApiParam(value = "Id de uma cozinha", example = "1", required = true) Long cozinhaId);

  @ApiOperation("Remove uma cozinha por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Cozinha excluida"),
          @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
  })
  void remover(@ApiParam(value = "Id de uma cozinha", example = "1", required = true) Long cozinhaId);
}
