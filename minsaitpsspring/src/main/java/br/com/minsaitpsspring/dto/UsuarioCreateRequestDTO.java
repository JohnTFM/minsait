package br.com.minsaitpsspring.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioCreateRequestDTO {

    @NotNull
    String nome;

    @NotNull
    @Email
    String email;

}
