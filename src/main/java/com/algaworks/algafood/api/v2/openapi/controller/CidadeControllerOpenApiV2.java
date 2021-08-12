package com.algaworks.algafood.api.v2.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApiV2 {
  @ApiOperation("Lista as cidades")
  CollectionModel<CidadeModelV2> listar();

  @ApiOperation("Busca uma cidade por Id")
  @ApiResponses({
          @ApiResponse(code = 400, message = "Id da cidade inválido", response = Problem.class),
          @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
  })
  CidadeModelV2 buscar(@ApiParam(value = "Id de uma cidade", example = "1", required = true) Long cidadeId);

  @ApiOperation("Cadastra uma cidade")
  @ApiResponses({
          @ApiResponse(code = 201, message = "Cidade cadastrada"),
  })
  CidadeModelV2 adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInputV2 cidadeInput);

  @ApiOperation("Atualiza uma cidade por Id")
  @ApiResponses({
          @ApiResponse(code = 200, message = "Cidade atualizada"),
          @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
  })
  Cidade atualizar(@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados", required = true) CidadeInputV2 cidadeInput,
                   @ApiParam(value = "Id de uma cidade", example = "1", required = true) Long cidadeId);

  @ApiOperation("Remove uma cidade por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Cidade excluida"),
          @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
  })
  void remover(@ApiParam(value = "Id de uma cidade", example = "1", required = true) Long cidadeId);
}
