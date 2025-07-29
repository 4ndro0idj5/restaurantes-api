package io.github.restaurantes_api.core.domain.usecases;

import io.github.restaurantes_api.core.domain.entities.Endereco;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.dtos.RestauranteUpdateDTO;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;

public interface AtualizarRestauranteUseCase {
    Restaurante executar(RestauranteUpdateDTO dto, Long restauranteId, Long usuarioId);
}