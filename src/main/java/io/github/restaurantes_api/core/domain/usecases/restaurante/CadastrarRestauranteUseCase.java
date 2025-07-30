package io.github.restaurantes_api.core.domain.usecases.restaurante;

import io.github.restaurantes_api.core.domain.entities.Restaurante;


public interface CadastrarRestauranteUseCase {
    void executar(Restaurante restaurante);
}

