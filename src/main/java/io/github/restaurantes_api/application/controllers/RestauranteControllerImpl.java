package io.github.restaurantes_api.application.controllers;

import io.github.restaurantes_api.core.controllers.RestauranteController;
import io.github.restaurantes_api.core.domain.usecases.*;
import io.github.restaurantes_api.core.dtos.RestauranteRequest;
import io.github.restaurantes_api.core.dtos.RestauranteResponse;
import io.github.restaurantes_api.core.dtos.RestauranteUpdateDTO;
import io.github.restaurantes_api.core.interfaces.RestaurantePresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestauranteControllerImpl implements RestauranteController {

    private final CadastrarRestauranteUseCase cadastrarUC;
    private final ListarRestaurantesUseCase listarUC;
    private final BuscarRestaurantePorIdUseCase buscarUC;
    private final ExcluirRestauranteUseCase excluirUC;
    private final AtualizarRestauranteUseCase atualizarUC;
    private final RestaurantePresenter presenter;

    @Override
    public void cadastrar(RestauranteRequest request) {
        var restaurante = presenter.fromDTO(request);
        cadastrarUC.executar(restaurante);
    }

    @Override
    public List<RestauranteResponse> listar(Long idUsuario) {
        return listarUC.executar(idUsuario).stream()
                .map(presenter::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestauranteResponse buscarPorId(Long id, Long idUsuario) {
        var restaurante = buscarUC.executar(id, idUsuario);
        return presenter.toResponseDTO(restaurante);
    }

    @Override
    public void excluir(Long id, Long idUsuario) {
        excluirUC.executar(id, idUsuario);
    }

    @Override
    public RestauranteResponse atualizar(RestauranteUpdateDTO dto, Long id, Long idUsuario) {
        var atualizado = atualizarUC.executar(dto, id, idUsuario);
        return presenter.toResponseDTO(atualizado);
    }
}

