package io.github.restaurantes_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteResponse {
    private String mensagem;
    private String nomeRestaurante;
    private String nomeProprietario;
}

