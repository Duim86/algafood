package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private CadastroUsuarioService cadastroUsuario;

  @Autowired
  private UsuarioModelAssembler usuarioModelAssembler;

  @Autowired
  private UsuarioInputDisassembler usuarioInputDisassembler;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<UsuarioModel> listar() {
    return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
  }

  @GetMapping("/{usuarioId}")
  public UsuarioModel buscar(@PathVariable Long usuarioId) {
    return usuarioModelAssembler.toModel(cadastroUsuario.buscarOuFalhar(usuarioId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
      Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
      return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuario));
  }

  @PutMapping("/{usuarioId}")
  public UsuarioModel atualizar(@PathVariable Long usuarioId,
                              @RequestBody @Valid UsuarioInput usuarioInput) {
    Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);

    usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

    usuarioAtual = cadastroUsuario.salvar(usuarioAtual);

    return usuarioModelAssembler.toModel(usuarioAtual);
  }

  @PutMapping("/{usuarioId}/senha")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
    cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getSenhaNova());
  }


  @DeleteMapping("/{usuarioId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long usuarioId) {
    cadastroUsuario.excluir(usuarioId);
  }
}

