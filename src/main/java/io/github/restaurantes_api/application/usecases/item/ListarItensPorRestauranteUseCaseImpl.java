package io.github.restaurantes_api.application.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.domain.usecases.item.ListarItensPorRestauranteUseCase;
import io.github.restaurantes_api.core.gateways.ItemGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import io.github.restaurantes_api.infrastructure.data.repositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarItensPorRestauranteUseCaseImpl implements ListarItensPorRestauranteUseCase {

    private final ItemGateway itemGateway;
    private final UsuarioServiceGateway usuarioService;

    @Override
    public List<Item> executar(Long restauranteId, Long usuarioid) {
        usuarioService.validarUsuarioAutenticado(usuarioid);

        if (!itemGateway.restauranteExiste(restauranteId)) {
            throw new NotFoundException("Restaurante não encontrado.");
        }

        return itemGateway.listarPorRestauranteId(restauranteId);
    }

}