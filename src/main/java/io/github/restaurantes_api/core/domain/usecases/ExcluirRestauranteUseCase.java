package io.github.restaurantes_api.core.domain.usecases;

import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;

public interface ExcluirRestauranteUseCase {
    void executar(Long restauranteId, Long usuarioId);
}
