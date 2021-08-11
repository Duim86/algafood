package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

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

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

  @Override
  @GetMapping
  public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro,
                                                 @PageableDefault(size = 10) Pageable pageable) {
    var pageableTraduzido = traduzirPageable(pageable);
    var pedidosPage = pedidoRepository.findAll(
            PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

    pedidosPage = new PageWrapper<>(pedidosPage, pageable);

    return pagedResourcesAssembler
            .toModel(pedidosPage, pedidoResumoModelAssembler);
  }

  @Override
  @GetMapping("/{codigoPedido}")
  public PedidoModel buscar(@PathVariable String codigoPedido) {
    var pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

    return pedidoModelAssembler.toModel(pedido);
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PedidoModel salvar(@RequestBody @Valid PedidoInput pedidoInput) {
    try {
      var pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
      return pedidoModelAssembler.toModel(emissaoPedido.salvar(pedido));
    } catch (EntidadeNaoEncontradaException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }

  private Pageable traduzirPageable(Pageable apiPageable) {
    var mapeamento = Map.of(
            "codigo", "codigo",
            "subtotal", "subtotal",
            "taxaFrete", "taxaFrete",
            "valorTotal", "valorTotal",
            "dataCriacao", "dataCriacao",
            "nomerestaurante", "restaurante.nome",
            "restaurante.id", "restaurante.id",
            "cliente.id", "cliente.id",
            "cliente.nome", "cliente.nome"
    );

    return PageableTranslator.translate(apiPageable, mapeamento);
  }
}