package io.github.restaurantes_api.dto;

import io.github.restaurantes_api.entities.Categoria;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestauranteResponse {
    private String nome;
    private String horarioFuncionamento;
    private Categoria categoria;
}
