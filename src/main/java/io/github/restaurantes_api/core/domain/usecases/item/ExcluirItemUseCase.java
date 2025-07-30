package io.github.restaurantes_api.core.domain.usecases.item;

public interface ExcluirItemUseCase {
    void executar(Long restauranteId, Long id, Long usuarioId);
}
