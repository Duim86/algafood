package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {
  @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
  @ApiImplicitParams(
          @ApiImplicitParam(
                  value = "Nome da projeção de pedidos", name = "projecao", paramType = "query",
                  type = "string", allowableValues = "apenas-nome"
          )
  )
  CollectionModel<RestauranteModel> listar();

  @ApiOperation(value = "Lista restaurantes", hidden = true)
  CollectionModel<RestauranteModel> listarApenasNome();

  @ApiOperation("Busca um restaurante por Id")
  @ApiResponses({
          @ApiResponse(code = 400, message = "Id do restaurante inválido", response = Problem.class),
          @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
  })
  RestauranteModel buscar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId);


  @ApiOperation("Cadastra um restaurante")
  @ApiResponses({
          @ApiResponse(code = 201, message = "Restaurante cadastrado"),
  })
  RestauranteModel adicionar(
          @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestauranteInput restauranteInput);

  @ApiOperation("Atualiza um restaurante por Id")
  @ApiResponses({
          @ApiResponse(code = 200, message = "Restaurante atualizado"),
          @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
  })
  RestauranteModel atualizar(@ApiParam(value = "Id de um restaurante", example = "1", required = true ) Long restauranteId,
                             @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestauranteInput restauranteInput);

  @ApiOperation("Atualiza um restaurante por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Restaurante excluído"),
          @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
  })
  void remover(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId);

  @ApiOperation("Ativa um restaurante por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Restaurante ativado"),
          @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
  })
  void ativar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId);

  @ApiOperation("Inativa um restaurante por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Restaurante inativado"),
          @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
  })
  void inativar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId);

  @ApiOperation("Ativa múltiplos restaurantes")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
  })
  void ativarEmMassa(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

  @ApiOperation("Inativa múltiplos restaurantes")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
  })
  void inativarEmMassa(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

  @ApiOperation("Abre um restaurante por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Restaurante aberto"),
          @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
  })
  void abrir(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId);

  @ApiOperation("Fecha um restaurante por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Restaurante fechado"),
          @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
  })
  void fechar(@ApiParam(value = "Id de um restaurante", example = "1", required = true) Long restauranteId);
}
