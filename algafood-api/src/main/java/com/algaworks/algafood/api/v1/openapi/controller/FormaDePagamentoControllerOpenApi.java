package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.FormaDePagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaDePagamentoInput;
import com.algaworks.algafood.api.v1.openapi.model.FormasDePagamentoModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Formas de pagamento")
public interface FormaDePagamentoControllerOpenApi {

  @ApiOperation(value = "Lista as formas de pagamento", response = FormasDePagamentoModelOpenApi.class)
  ResponseEntity<CollectionModel<FormaDePagamentoModel>> listar(ServletWebRequest request);

  @ApiOperation("Busca uma forma de pagamento por Id")
  @ApiResponses({
          @ApiResponse(code = 400, message = "Id da forma de pagamento inválida", response = Problem.class),
          @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
  })
  ResponseEntity<FormaDePagamentoModel> buscar(ServletWebRequest request, @ApiParam(value = "Id de uma forma de pagamento", example = "1", required = true) Long formaDePagamentoId);


  @ApiOperation("Cadastra uma forma de pagamento")
  @ApiResponses({
          @ApiResponse(code = 201, message = "Forma de pagamento cadastrada"),
  })
  FormaDePagamentoModel salvar(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true) FormaDePagamentoInput formaDePagamentoInput);


  @ApiOperation("Atualiza uma cozinha por Id")
  @ApiResponses({
          @ApiResponse(code = 200, message = "Cozinha atualizada"),
          @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
  })
  FormaDePagamentoModel atualizar(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true) FormaDePagamentoInput formaDePagamentoInput,
                                  @ApiParam(value = "Id de uma forma de pagamento", example = "1", required = true) Long formaDePagamentoId);

  @ApiOperation("Remove uma cozinha por Id")
  @ApiResponses({
          @ApiResponse(code = 204, message = "Forma de pagamento excluida"),
          @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class),
  })
  void excluir(@ApiParam(value = "Id de uma forma de pagamento", example = "1", required = true) Long formaDePagamentoId);
}
