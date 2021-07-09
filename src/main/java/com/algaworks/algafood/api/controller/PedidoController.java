package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.dtos.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

//  @GetMapping
//  public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//    List<Pedido> pedidos = pedidoRepository.findAll();
//    List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);
//
//    MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//    SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//    filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//    if(StringUtils.isNotBlank(campos)) {
//      filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//    }
//
//    pedidosWrapper.setFilters(filterProvider);
//
//    return pedidosWrapper;
//  }


  @GetMapping
  public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro,
                                           @PageableDefault(size = 10) Pageable pageable) {
    pageable = traduzirPageable(pageable);
    Page<Pedido> pedidosPage = pedidoRepository.findAll(
            PedidoSpecs.usandoFiltro(filtro), pageable);

    List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler
            .toCollectionModel(pedidosPage.getContent());

    return new PageImpl<>(
            pedidosResumoModel, pageable, pedidosPage.getTotalElements());
  }

  @GetMapping("/{codigoPedido}")
  public PedidoModel buscar(@PathVariable String codigoPedido) {
    var pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

    return pedidoModelAssembler.toModel(pedido);
  }

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
            "restaurante.nome", "restaurante.nome",
            "nomeCliente", "cliente.nome",
            "valorTotal", "valorTotal"
    );
            return PageableTranslator.translate(apiPageable, mapeamento);
  }
}