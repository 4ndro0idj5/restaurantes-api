package io.github.restaurantes_api.application.presenters;

import io.github.restaurantes_api.application.mapper.RestauranteMapper;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.usecases.restaurante.*;
import io.github.restaurantes_api.core.dtos.RestauranteRequest;
import io.github.restaurantes_api.core.dtos.RestauranteResponse;
import io.github.restaurantes_api.core.dtos.RestauranteUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantePresenter  {

    private final CadastrarRestauranteUseCase cadastrarUC;
    private final ListarRestaurantesUseCase listarUC;
    private final BuscarRestaurantePorIdUseCase buscarUC;
    private final ExcluirRestauranteUseCase excluirUC;
    private final AtualizarRestauranteUseCase atualizarUC;

    private final RestauranteMapper restauranteMapper;


    public void cadastrar(RestauranteRequest request) {
        Restaurante restaurante = restauranteMapper.fromDTO(request);
        cadastrarUC.executar(restaurante);
    }


    public List<RestauranteResponse> listar(Long idUsuario) {
        return listarUC.executar(idUsuario).stream()
                .map(restauranteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    public RestauranteResponse buscarPorId(Long id, Long idUsuario) {
        var restaurante = buscarUC.executar(id, idUsuario);
        return restauranteMapper.toResponseDTO(restaurante);
    }


    public void excluir(Long id, Long idUsuario) {
        excluirUC.executar(id, idUsuario);
    }

    public void atualizar(RestauranteUpdateDTO dto, Long id, Long idUsuario) {
        atualizarUC.executar(dto, id, idUsuario);
    }


}

