package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastroUsuarioService {

  private static final String MSG_USUARIO_EM_USO = "Usuario de código %d não pode ser removido, pois está em uso";

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private CadastroGrupoService cadastroGrupo;


  @Transactional
  public Usuario salvar(Usuario usuario) {

    usuarioRepository.detach(usuario);

    Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

    if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
      throw new NegocioException("Já existente um usuário cadastrado com o email " + usuario.getEmail());
    }

    return usuarioRepository.save(usuario);
  }

  @Transactional
  public void alterarSenha(Long usuarioId, String senhaAtual, String senhaNova){

    var usuario = buscarOuFalhar(usuarioId);

    if (usuario.senhaNaoCoincideCom(senhaAtual)) {
      throw new NegocioException("Senha atual informada não coincide com a senha do usuário");
    }

    usuario.setSenha(senhaNova);
  }

  @Transactional
  public void excluir(Long usuarioId) {
    try {
      usuarioRepository.deleteById(usuarioId);
      usuarioRepository.flush();

    } catch (EmptyResultDataAccessException e) {
      throw new UsuarioNaoEncontradoException(usuarioId);

    } catch (DataIntegrityViolationException e) {
      throw new EntidadeEmUsoException(
              String.format(MSG_USUARIO_EM_USO, usuarioId));
    }
  }

  @Transactional
  public void associarGrupo(Long usuarioId, Long grupoId) {
    var usuario = buscarOuFalhar(usuarioId);
    var grupo = cadastroGrupo.buscarOuFalhar(grupoId);
    usuario.adicionarGrupo(grupo);
  }


  @Transactional
  public void desassociarGrupo(Long usuarioId, Long grupoId) {
    var usuario = buscarOuFalhar(usuarioId);
    var grupo = cadastroGrupo.buscarOuFalhar(grupoId);
    usuario.removerGrupo(grupo);
  }

  public Usuario buscarOuFalhar(Long usuarioId) {
    return usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
  }
}
