package io.github.restaurantes_api.core.domain.usecases;

import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;

import java.util.List;


public interface ListarRestaurantesUseCase {
    List<Restaurante> executar(Long usuarioId);
}