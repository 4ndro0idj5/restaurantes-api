package io.github.restaurantes_api.application.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.domain.usecases.item.BuscarItemPorIdUseCase;
import io.github.restaurantes_api.core.gateways.ItemGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuscarItemPorIdUseCaseImpl implements BuscarItemPorIdUseCase {

    private final ItemGateway itemGateway;
    private final UsuarioServiceGateway usuarioService;


    @Override
    public Optional<Item> executar(Long id, Long restauranteId, Long usuarioId) {

        usuarioService.validarUsuarioAutenticado(usuarioId);

        if (!itemGateway.restauranteExiste(restauranteId)) {
            throw new NotFoundException(
                    "Restaurante não encontrado."
            );
        }

        Optional<Item> itemOptional = itemGateway.buscarPorIdEPorRestauranteId(id, restauranteId);

        if (itemOptional.isEmpty()) {
            throw new NotFoundException(
                    "Item não encontrado."
            );
        }

        return itemOptional;
    }
}