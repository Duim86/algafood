package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.FormaDePagamentoModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.FormaDePagamentoInputDisassembler;
import com.algaworks.algafood.api.model.FormaDePagamentoModel;
import com.algaworks.algafood.api.model.input.FormaDePagamentoInput;
import com.algaworks.algafood.domain.repository.FormaDePagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaDePagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-de-pagamento")
public class FormaDePagamentoController {

  @Autowired
  private FormaDePagamentoRepository formaDePagamentoRepository;

  @Autowired
  private CadastroFormaDePagamentoService cadastroFormaDePagamento;

  @Autowired
  private FormaDePagamentoModelAssembler formaDePagamentoModelAssembler;

  @Autowired
  private FormaDePagamentoInputDisassembler formaDePagamentoInputDisassembler;

  @GetMapping
  public ResponseEntity<List<FormaDePagamentoModel>> listar(ServletWebRequest request){
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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FormaDePagamentoModel salvar(@RequestBody @Valid FormaDePagamentoInput formaDePagamentoInput) {
    return formaDePagamentoModelAssembler
            .toModel(formaDePagamentoRepository
                    .save(formaDePagamentoInputDisassembler
                            .toDomainObject(formaDePagamentoInput)));
  }

  @PutMapping("/{formaDePagamentoId}")
  public FormaDePagamentoModel atualizar(@RequestBody @Valid FormaDePagamentoInput formaDePagamentoInput,
                               @PathVariable Long formaDePagamentoId) {

    var formaDePagamentoAtual = cadastroFormaDePagamento.buscarOuFalhar(formaDePagamentoId);
    formaDePagamentoInputDisassembler.copyToDomainObject(formaDePagamentoInput, formaDePagamentoAtual);
    return formaDePagamentoModelAssembler.toModel(cadastroFormaDePagamento.salvar(formaDePagamentoAtual));
  }

  @DeleteMapping("/{formaDePagamentoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long formaDePagamentoId){
    cadastroFormaDePagamento.excluir(formaDePagamentoId);
  }
}
