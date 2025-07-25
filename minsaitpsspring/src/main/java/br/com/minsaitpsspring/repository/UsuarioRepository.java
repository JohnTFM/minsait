package br.com.minsaitpsspring.repository;

import br.com.minsaitpsspring.model.Usuario;

import java.util.List;
import java.util.UUID;

/**
 * Simula um repository JPA
 */
public interface UsuarioRepository {

    Usuario save(Usuario u);

    List<Usuario> findAll();

    Usuario findById(UUID id);

    void deleteById(UUID id);

}
