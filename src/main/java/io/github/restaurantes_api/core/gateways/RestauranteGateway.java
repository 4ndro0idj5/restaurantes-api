package io.github.restaurantes_api.core.gateways;

import io.github.restaurantes_api.core.domain.entities.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteGateway {
    Restaurante salvar(Restaurante restaurante);
    List<Restaurante> listarTodos();
    Optional<Restaurante> buscarPorId(Long id);
    void excluir(Restaurante restaurante);
}
