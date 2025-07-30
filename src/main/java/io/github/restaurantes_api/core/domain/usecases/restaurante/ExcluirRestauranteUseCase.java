package io.github.restaurantes_api.core.domain.usecases.restaurante;

public interface ExcluirRestauranteUseCase {
    void executar(Long restauranteId, Long usuarioId);
}
