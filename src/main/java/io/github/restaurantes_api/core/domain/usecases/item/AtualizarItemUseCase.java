package io.github.restaurantes_api.core.domain.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.dtos.ItemUpdateDTO;


public interface AtualizarItemUseCase {
    void executar(Long idRestaurante, ItemUpdateDTO dto, Long id, Long idUsuario);
}
