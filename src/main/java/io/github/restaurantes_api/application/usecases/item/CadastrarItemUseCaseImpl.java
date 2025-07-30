package io.github.restaurantes_api.application.usecases.item;

import io.github.restaurantes_api.application.mapper.ItemMapper;
import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.usecases.item.CadastrarItemUseCase;
import io.github.restaurantes_api.core.dtos.ItemDTO;
import io.github.restaurantes_api.core.gateways.ItemGateway;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarItemUseCaseImpl implements CadastrarItemUseCase {

    private final RestauranteGateway restauranteGateway;
    private final ItemGateway itemGateway;
    private final UsuarioServiceGateway usuarioService;
    private final ItemMapper itemMapper;

    @Override
    public void executar(Long restauranteId, ItemDTO dto, Long usuarioId) {
        Restaurante restaurante = restauranteGateway.buscarPorId(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        usuarioService.validarUsuarioAutenticado(restaurante.getProprietarioId());

        if (!restaurante.getProprietarioId().equals(usuarioId)) {
            throw new RuntimeException("Usuário não tem permissão para incluir este prato.");
        }

        Item item = itemMapper.fromDTO(dto);

        item.setRestauranteId(restauranteId);

        itemGateway.salvar(item);
    }
}