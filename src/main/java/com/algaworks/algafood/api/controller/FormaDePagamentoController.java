package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.FormaDePagamentoModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.FormaDePagamentoInputDisassembler;
import com.algaworks.algafood.api.model.FormaDePagamentoModel;
import com.algaworks.algafood.api.model.input.FormaDePagamentoInput;
import com.algaworks.algafood.domain.repository.FormaDePagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaDePagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
  public List<FormaDePagamentoModel> listar(){
    return formaDePagamentoModelAssembler.toCollectionModel(formaDePagamentoRepository.findAll());
  }

  @GetMapping("/{formaDePagamentoId}")
  public FormaDePagamentoModel buscar(@PathVariable Long formaDePagamentoId){
    return formaDePagamentoModelAssembler.toModel(cadastroFormaDePagamento.buscarOuFalhar(formaDePagamentoId));
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
