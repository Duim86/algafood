package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

  @ApiOperation("Lista os grupos")
  CollectionModel<GrupoModel> listar();

  @ApiOperation("Busca um grupo por Id")
  @ApiResponses({
          @ApiResponse(code = 400, message = "Id do grupo inválido", response = Problem.class),
          @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
  })
  GrupoModel buscar(@ApiParam(value = "Id de um grupo", example = "1", required = true) Long grupoId);

  @ApiOperation("Cadastra um grupo")
  @ApiResponses({
          @ApiResponse(code = 201, message = "Grupo cadastrado"),
  })
  GrupoModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoInput grupoInput);


  @ApiOperation("Atualiza um grupo por Id")
  @ApiResponses({
          @ApiResponse(code = 200, message = "Grupo atualizado"),
          @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class),
  })
  GrupoModel atualizar(@ApiParam(value = "Id de um grupo", example = "1", required = true) Long grupoId,
                       @ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoInput grupoInput);

  @ApiOperation("Remove um grupo por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Grupo excluido"),
          @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class),
  })
  void remover(@ApiParam(value = "Id de um grupo", example = "1", required = true) Long grupoId);
}
