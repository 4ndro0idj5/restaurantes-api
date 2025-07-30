package io.github.restaurantes_api.application.usecases.restaurante;

import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.usecases.restaurante.CadastrarRestauranteUseCase;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarRestauranteUseCaseImpl implements CadastrarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;
    private final UsuarioServiceGateway usuarioService;

    @Override
    public void executar(Restaurante restaurante) {
        usuarioService.validarUsuarioAutenticadoEProprietario(restaurante.getProprietarioId());
        restauranteGateway.salvar(restaurante);
    }
}