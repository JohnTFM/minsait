package br.com.minsaitpsspring.rest;

import br.com.minsaitpsspring.dto.UsuarioCreateRequestDTO;
import br.com.minsaitpsspring.dto.UsuarioEditRequestDTO;
import br.com.minsaitpsspring.model.Usuario;
import br.com.minsaitpsspring.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsuarioController {


    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> criarNovoUsuario(@RequestBody @Valid UsuarioCreateRequestDTO usuarioCreateRequestDTO){
        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(usuarioService.salvarUsuario(usuarioCreateRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios(){
        return ResponseEntity.ok(
                usuarioService.listarTodosUsuarios()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(
            @PathVariable("id") UUID id
    ){

        Usuario u = usuarioService.obterPorId(id);

        if(u==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(u);
    }

    @PutMapping
    public ResponseEntity<Usuario> atualizarUsuario(
            @RequestBody @Valid UsuarioEditRequestDTO usuarioEditRequestDTO
    ){
        return ResponseEntity.ok(
                usuarioService.alterarUsuario(usuarioEditRequestDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPorId(
            @PathVariable("id") UUID id
    ){
        usuarioService.deletarPorId(id);
        return ResponseEntity.ok(null);
    }

}
