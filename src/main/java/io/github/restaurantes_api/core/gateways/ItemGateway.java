package io.github.restaurantes_api.core.gateways;

import io.github.restaurantes_api.core.domain.entities.Item;

import java.util.List;
import java.util.Optional;

public interface ItemGateway {
    Item salvar(Item item);
    List<Item> listarPorRestauranteId(Long restauranteId);
    Optional<Item> buscarPorIdEPorRestauranteId(Long id, Long restauranteId);
    void excluir(Item item);
}
