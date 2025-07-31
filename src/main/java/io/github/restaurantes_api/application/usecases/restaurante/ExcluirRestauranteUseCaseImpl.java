package io.github.restaurantes_api.application.usecases.restaurante;

import io.github.restaurantes_api.core.domain.exceptions.ForbiddenException;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.domain.usecases.restaurante.ExcluirRestauranteUseCase;
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
                .orElseThrow(() -> new NotFoundException("Restaurante não encontrado"));

        if (!restaurante.getProprietarioId().equals(usuarioId)) {
            throw new ForbiddenException("Usuário não tem permissão para excluir este restaurante");
        }

        gateway.excluir(restaurante);
    }
}
