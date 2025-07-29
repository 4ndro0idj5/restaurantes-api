package io.github.restaurantes_api.application.usecases;

import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.usecases.BuscarRestaurantePorIdUseCase;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarRestaurantePorIdUseCaseImpl implements BuscarRestaurantePorIdUseCase {

    private final RestauranteGateway gateway;
    private final UsuarioServiceGateway usuarioService;

    @Override
    public Restaurante executar(Long restauranteId, Long usuarioId) {
        usuarioService.validarUsuarioAutenticado(usuarioId);
        return gateway.buscarPorId(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
    }
}

