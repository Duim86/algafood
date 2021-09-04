package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.algaworks.algafood.core.security.CheckSecurity.*;

@RestController
@RequestMapping(path = "/v1/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

  @Autowired
  private CadastroUsuarioService cadastroUsuario;

  @Autowired
  private GrupoModelAssembler grupoModelAssembler;

  @Autowired
  private AlgaLinks algaLinks;

  @Override
  @GetMapping
  @UsuariosGruposPermissoes.PodeConsultar
  public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {
    var usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

    var gruposModel = grupoModelAssembler.toCollectionModel(usuario.getGrupos())
            .removeLinks()
            .add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));

    gruposModel.getContent().forEach(grupoModel -> {
      grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(
              usuarioId, grupoModel.getId(), "desassociar"));
    });

    return gruposModel;
  }

  @Override
  @PutMapping("/{grupoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @UsuariosGruposPermissoes.PodeEditar
  public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
    cadastroUsuario.associarGrupo(usuarioId, grupoId);

    return ResponseEntity.noContent().build();
  }
  @Override
  @DeleteMapping("/{grupoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @UsuariosGruposPermissoes.PodeEditar
  public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
    cadastroUsuario.desassociarGrupo(usuarioId, grupoId);

    return ResponseEntity.noContent().build();
  }
}
