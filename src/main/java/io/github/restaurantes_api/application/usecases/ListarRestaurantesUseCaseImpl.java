package io.github.restaurantes_api.application.usecases;


import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.usecases.ListarRestaurantesUseCase;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarRestaurantesUseCaseImpl implements ListarRestaurantesUseCase {

    private final RestauranteGateway gateway;
    private final UsuarioServiceGateway usuarioService;

    @Override
    public List<Restaurante> executar(Long usuarioId) {
        usuarioService.validarUsuarioAutenticado(usuarioId);
        return gateway.listarTodos();
    }
}