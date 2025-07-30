package io.github.restaurantes_api.core.domain.usecases.item;

import io.github.restaurantes_api.core.dtos.ItemDTO;

public interface CadastrarItemUseCase {
    void executar(Long restauranteId, ItemDTO dto, Long usuarioId);
}

