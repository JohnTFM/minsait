package br.com.minsaitpsspring.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Usuario {

    UUID id;

    String nome;

    String email;


}
