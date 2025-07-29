package io.github.restaurantes_api.core.dtos;

import io.github.restaurantes_api.core.domain.entities.enums.Categoria;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestauranteUpdateDTO {
    private String nome;
    private Categoria categoria;
    private String horarioFuncionamento;
    private EnderecoRequest enderecoRequest;
}

