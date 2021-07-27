package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private CadastroUsuarioService cadastroUsuario;

  @Autowired
  private UsuarioModelAssembler usuarioModelAssembler;

  @Autowired
  private UsuarioInputDisassembler usuarioInputDisassembler;

  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<UsuarioModel> listar() {
    return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
  }

  @Override
  @GetMapping("/{usuarioId}")
  public UsuarioModel buscar(@PathVariable Long usuarioId) {
    return usuarioModelAssembler.toModel(cadastroUsuario.buscarOuFalhar(usuarioId));
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
      var usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
      return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuario));
  }

  @Override
  @PutMapping("/{usuarioId}")
  public UsuarioModel atualizar(@PathVariable Long usuarioId,
                                @RequestBody @Valid UsuarioInput usuarioInput) {
    var usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);

    usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

    usuarioAtual = cadastroUsuario.salvar(usuarioAtual);

    return usuarioModelAssembler.toModel(usuarioAtual);
  }

  @Override
  @PutMapping("/{usuarioId}/senha")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
    cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getSenhaNova());
  }


  @Override
  @DeleteMapping("/{usuarioId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long usuarioId) {
    cadastroUsuario.excluir(usuarioId);
  }
}

