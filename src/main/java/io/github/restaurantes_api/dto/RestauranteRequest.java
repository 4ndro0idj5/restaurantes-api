package io.github.restaurantes_api.dto;


import io.github.restaurantes_api.entities.Categoria;
import lombok.Data;

@Data
public class RestauranteRequest {
    private String nome;
    private String cnpj;
    private Categoria categoria;
    private String telefone;
    private String horarioFuncionamento;
    private Long proprietarioId;
    private EnderecoRequest enderecoRequest;
}


