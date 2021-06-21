package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.dtos.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

  @Autowired
  private PedidoRepository pedidoRepository;

  @Autowired
  private EmissaoPedidoService emissaoPedido;

  @Autowired
  private PedidoModelAssembler pedidoModelAssembler;

  @Autowired
  private PedidoInputDisassembler pedidoInputDisassembler;

  @Autowired
  private PedidoResumoModelAssembler pedidoResumoModelAssembler;


  @GetMapping
  public List<PedidoResumoModel> listar() {
    List<Pedido> todosPedidos = pedidoRepository.findAll();

    return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
  }

  @GetMapping("/{codigoPedido}")
  public PedidoModel buscar(@PathVariable String codigoPedido) {
    Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

    return pedidoModelAssembler.toModel(pedido);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PedidoModel salvar(@RequestBody @Valid PedidoInput pedidoInput) {
    try {
      Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
      return pedidoModelAssembler.toModel(emissaoPedido.salvar(pedido));
    } catch (EntidadeNaoEncontradaException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }
}