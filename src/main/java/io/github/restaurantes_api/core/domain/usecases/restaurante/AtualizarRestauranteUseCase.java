package io.github.restaurantes_api.core.domain.usecases.restaurante;

import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.dtos.RestauranteUpdateDTO;

public interface AtualizarRestauranteUseCase {
    Restaurante executar(RestauranteUpdateDTO dto, Long restauranteId, Long usuarioId);
}