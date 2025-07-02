package io.github.restaurantes_api.dto;

import io.github.restaurantes_api.entities.Categoria;
import lombok.Data;

@Data
public class RestauranteUpdateDTO {
    private String nome;
    private Categoria categoria;
    private String horarioFuncionamento;
    private EnderecoRequest enderecoRequest;
}

