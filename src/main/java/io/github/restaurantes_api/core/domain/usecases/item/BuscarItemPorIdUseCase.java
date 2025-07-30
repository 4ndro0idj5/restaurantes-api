package io.github.restaurantes_api.core.domain.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;

import java.util.Optional;

public interface BuscarItemPorIdUseCase {
    Optional<Item> executar(Long id, Long restauranteId, Long usuarioId);
}
