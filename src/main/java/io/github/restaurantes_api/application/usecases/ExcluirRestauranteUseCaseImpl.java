package io.github.restaurantes_api.application.usecases;

import io.github.restaurantes_api.core.domain.usecases.ExcluirRestauranteUseCase;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExcluirRestauranteUseCaseImpl implements ExcluirRestauranteUseCase {

    private final RestauranteGateway gateway;
    private final UsuarioServiceGateway usuarioService;

    @Override
    public void executar(Long restauranteId, Long usuarioId) {
        usuarioService.validarUsuarioAutenticado(usuarioId);

        var restaurante = gateway.buscarPorId(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        if (!restaurante.getProprietarioId().equals(usuarioId)) {
            throw new RuntimeException("Usuário não tem permissão para excluir este restaurante");
        }

        gateway.excluir(restaurante);
    }
}
