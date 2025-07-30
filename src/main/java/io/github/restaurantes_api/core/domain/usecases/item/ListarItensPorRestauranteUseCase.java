package io.github.restaurantes_api.core.domain.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;


import java.util.List;

public interface ListarItensPorRestauranteUseCase {
    List<Item> executar(Long restauranteId, Long usuarioId);
}