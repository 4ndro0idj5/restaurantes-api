package io.github.restaurantes_api.application.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;
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
        return itemGateway.buscarPorIdEPorRestauranteId(id, restauranteId);
    }
}