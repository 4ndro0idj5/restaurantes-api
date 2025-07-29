package io.github.restaurantes_api.core.dtos;

import io.github.restaurantes_api.core.domain.entities.enums.Categoria;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestauranteResponse {
    private String nome;
    private String horarioFuncionamento;
    private Categoria categoria;
    private EnderecoRequest enderecoRequest;
}
