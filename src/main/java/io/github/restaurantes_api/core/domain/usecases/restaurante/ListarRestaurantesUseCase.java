package io.github.restaurantes_api.core.domain.usecases.restaurante;

import io.github.restaurantes_api.core.domain.entities.Restaurante;

import java.util.List;


public interface ListarRestaurantesUseCase {
    List<Restaurante> executar(Long usuarioId);
}