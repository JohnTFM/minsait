package br.com.minsaitpsspring.service;

import br.com.minsaitpsspring.dto.UsuarioCreateRequestDTO;
import br.com.minsaitpsspring.dto.UsuarioEditRequestDTO;
import br.com.minsaitpsspring.model.Usuario;
import br.com.minsaitpsspring.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvarUsuario(UsuarioCreateRequestDTO usuarioCreateRequestDTO){
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioCreateRequestDTO.getNome());
        usuario.setNome(usuarioCreateRequestDTO.getNome());
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodosUsuarios() {
        return this.usuarioRepository.findAll();
    }

    public Usuario obterPorId(UUID id) {
        return this.usuarioRepository.findById(id);
    }

    public Usuario alterarUsuario(@Valid UsuarioEditRequestDTO usuarioEditRequestDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioEditRequestDTO.getEmail());
        usuario.setNome(usuarioEditRequestDTO.getNome());
        usuario.setId(usuarioEditRequestDTO.getId());
        return this.usuarioRepository.save(usuario);
    }

    public void deletarPorId(UUID id) {
        this.usuarioRepository.deleteById(id);
    }
}
