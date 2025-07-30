package io.github.restaurantes_api.application.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.usecases.item.ExcluirItemUseCase;
import io.github.restaurantes_api.core.gateways.ItemGateway;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExcluirItemUseCaseImpl implements ExcluirItemUseCase {

    private final RestauranteGateway restauranteGateway;
    private final ItemGateway itemGateway;
    private final UsuarioServiceGateway usuarioService;

    @Override
    public void executar(Long restauranteId, Long id, Long usuarioId) {
        Restaurante restaurante = restauranteGateway.buscarPorId(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        usuarioService.validarUsuarioAutenticado(restaurante.getProprietarioId());

        if (!restaurante.getProprietarioId().equals(usuarioId)) {
            throw new RuntimeException("Usuário não tem permissão para excluir este prato.");
        }

        Item item = itemGateway.buscarPorIdEPorRestauranteId(id, restauranteId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        itemGateway.excluir(item);
    }
}