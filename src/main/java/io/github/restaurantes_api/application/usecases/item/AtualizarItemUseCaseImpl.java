package io.github.restaurantes_api.application.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.exceptions.ForbiddenException;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.domain.usecases.item.AtualizarItemUseCase;
import io.github.restaurantes_api.core.dtos.ItemUpdateDTO;
import io.github.restaurantes_api.core.gateways.ItemGateway;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarItemUseCaseImpl implements AtualizarItemUseCase {

    private final RestauranteGateway restauranteGateway;
    private final ItemGateway itemGateway;
    private final UsuarioServiceGateway usuarioService;

    @Override
    public void executar(Long restauranteId, ItemUpdateDTO dto, Long id, Long usuarioId) {
        Restaurante restaurante = restauranteGateway.buscarPorId(restauranteId)
                .orElseThrow(() -> new NotFoundException("Restaurante não encontrado"));

        usuarioService.validarUsuarioAutenticado(restaurante.getProprietarioId());

        if (!restaurante.getProprietarioId().equals(usuarioId)) {
            throw new ForbiddenException("Usuário não tem permissão para atualizar este prato.");
        }

        Item item = itemGateway.buscarPorIdEPorRestauranteId(id, restauranteId)
                .orElseThrow(() -> new NotFoundException("Item não encontrado."));

        item.setNome(dto.getNome() != null ? dto.getNome() : item.getNome());
        item.setDescricao(dto.getDescricao() != null ? dto.getDescricao() : item.getDescricao());
        item.setPreco(dto.getPreco() != null ? dto.getPreco() : item.getPreco());
        item.setConsumoLocal(dto.getConsumoLocal() != null ? dto.getConsumoLocal() : item.isConsumoLocal());
        item.setFoto(dto.getFoto() != null ? dto.getFoto() : item.getFoto());

        itemGateway.salvar(item);
    }
}