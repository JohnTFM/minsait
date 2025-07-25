package br.com.minsaitpsspring.repository;

import br.com.minsaitpsspring.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public  class UsuarioRepositoryImpl implements UsuarioRepository{

    private final HashMap<UUID,Usuario> dbMock = new HashMap<>();

    @Override
    public Usuario save(Usuario u) {

        if(u.getId()==null){
            u.setId(UUID.randomUUID());
        }

        dbMock.put(u.getId(),u);

        return u;

    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(dbMock.values());
    }

    @Override
    public Usuario findById(UUID id) {
        return dbMock.get(id);
    }

    @Override
    public void deleteById(UUID id) {
        dbMock.remove(id);
    }
}
