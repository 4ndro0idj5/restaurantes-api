package io.github.restaurantes_api.application.usecases;

import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.usecases.CadastrarRestauranteUseCase;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarRestauranteUseCaseImpl implements CadastrarRestauranteUseCase {

    private final RestauranteGateway restauranteRepository;

    @Override
    public void executar(Restaurante restaurante) {
        restauranteRepository.salvar(restaurante);
    }
}
