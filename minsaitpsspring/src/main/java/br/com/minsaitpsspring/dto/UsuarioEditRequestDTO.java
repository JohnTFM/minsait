package br.com.minsaitpsspring.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UsuarioEditRequestDTO {
    @NotNull
    @Email
    String email;
    @NotNull
    String nome;
    @NotNull
    UUID id;
}
