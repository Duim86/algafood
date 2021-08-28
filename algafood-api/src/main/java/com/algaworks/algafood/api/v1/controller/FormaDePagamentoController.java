package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.FormaDePagamentoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.FormaDePagamentoInputDisassembler;
import com.algaworks.algafood.api.v1.model.FormaDePagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaDePagamentoInput;
import com.algaworks.algafood.api.v1.openapi.controller.FormaDePagamentoControllerOpenApi;
import com.algaworks.algafood.domain.repository.FormaDePagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaDePagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/v1/formas-de-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaDePagamentoController implements FormaDePagamentoControllerOpenApi {

  @Autowired
  private AlgaLinks algaLinks;

  @Autowired
  private FormaDePagamentoRepository formaDePagamentoRepository;

  @Autowired
  private CadastroFormaDePagamentoService cadastroFormaDePagamento;

  @Autowired
  private FormaDePagamentoModelAssembler formaDePagamentoModelAssembler;

  @Autowired
  private FormaDePagamentoInputDisassembler formaDePagamentoInputDisassembler;

  @Override
  @GetMapping
  public ResponseEntity<CollectionModel<FormaDePagamentoModel>> listar(ServletWebRequest request){
    ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

    String eTag = "0";
    OffsetDateTime dataUltimaAtualizacao = formaDePagamentoRepository.getDataUltimaAtualizacao();

    if(dataUltimaAtualizacao != null) {
      eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
    }
    if(request.checkNotModified(eTag)) {
      return null;
    }

    var formasDePagamentoModel = formaDePagamentoModelAssembler.toCollectionModel(formaDePagamentoRepository.findAll());

    return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
            .eTag(eTag)
            .body(formasDePagamentoModel);
  }

  @Override
  @GetMapping("/{formaDePagamentoId}")
  public ResponseEntity<FormaDePagamentoModel> buscar(ServletWebRequest request, @PathVariable Long formaDePagamentoId){
    ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

    String eTag = "0";
    OffsetDateTime dataAtualizacao = formaDePagamentoRepository
            .getDataAtualizacaoById(formaDePagamentoId);

    if (dataAtualizacao != null) {
      eTag = String.valueOf(dataAtualizacao.toEpochSecond());
    }

    if (request.checkNotModified(eTag)) {
      return null;
    }

    var formasDePagamentoModel = formaDePagamentoModelAssembler.toModel(cadastroFormaDePagamento.buscarOuFalhar(formaDePagamentoId));
//
    return ResponseEntity.ok()
//            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
            .eTag(eTag)
//            .cacheControl(CacheControl.noCache())
//            .cacheControl(CacheControl.noStore())
            .body(formasDePagamentoModel);
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FormaDePagamentoModel salvar(@RequestBody @Valid FormaDePagamentoInput formaDePagamentoInput) {
    return formaDePagamentoModelAssembler
            .toModel(formaDePagamentoRepository
                    .save(formaDePagamentoInputDisassembler
                            .toDomainObject(formaDePagamentoInput)));
  }

  @Override
  @PutMapping("/{formaDePagamentoId}")
  public FormaDePagamentoModel atualizar(@RequestBody @Valid FormaDePagamentoInput formaDePagamentoInput,
                                         @PathVariable Long formaDePagamentoId) {

    var formaDePagamentoAtual = cadastroFormaDePagamento.buscarOuFalhar(formaDePagamentoId);
    formaDePagamentoInputDisassembler.copyToDomainObject(formaDePagamentoInput, formaDePagamentoAtual);
    return formaDePagamentoModelAssembler.toModel(cadastroFormaDePagamento.salvar(formaDePagamentoAtual));
  }

  @Override
  @DeleteMapping("/{formaDePagamentoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long formaDePagamentoId){
    cadastroFormaDePagamento.excluir(formaDePagamentoId);
  }
}
