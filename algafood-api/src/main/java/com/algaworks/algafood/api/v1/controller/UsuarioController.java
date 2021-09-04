package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.algaworks.algafood.core.security.CheckSecurity.*;

@RestController
@RequestMapping(value = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
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
  @UsuariosGruposPermissoes.PodeConsultar
  public CollectionModel<UsuarioModel> listar() {
    return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
  }

  @Override
  @GetMapping("/{usuarioId}")
  @UsuariosGruposPermissoes.PodeConsultar
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
  @UsuariosGruposPermissoes.PodeAlterarUsuario
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
  @UsuariosGruposPermissoes.PodeAlterarPropriaSenha
  public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
    cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getSenhaNova());
  }


  @Override
  @DeleteMapping("/{usuarioId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @UsuariosGruposPermissoes.PodeEditar
  public void remover(@PathVariable Long usuarioId) {
    cadastroUsuario.excluir(usuarioId);
  }
}

