package io.github.restaurantes_api.core.interfaces;

import io.github.restaurantes_api.core.domain.entities.Endereco;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.dtos.EnderecoRequest;
import io.github.restaurantes_api.core.dtos.RestauranteRequest;
import io.github.restaurantes_api.core.dtos.RestauranteResponse;

public interface RestaurantePresenter {
    Restaurante fromDTO(RestauranteRequest request);
    RestauranteResponse toResponseDTO(Restaurante restaurante);
    EnderecoRequest toEnderecoRequest(Endereco endereco);
}
