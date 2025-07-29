package io.github.restaurantes_api.core.controllers;

import io.github.restaurantes_api.core.dtos.RestauranteRequest;
import io.github.restaurantes_api.core.dtos.RestauranteResponse;
import io.github.restaurantes_api.core.dtos.RestauranteUpdateDTO;


import java.util.List;


public interface RestauranteController {
    void cadastrar(RestauranteRequest request);
    List<RestauranteResponse> listar(Long idUsuario);
    RestauranteResponse buscarPorId(Long id, Long idUsuario);
    void excluir(Long id, Long idUsuario);
    RestauranteResponse atualizar(RestauranteUpdateDTO dto, Long id, Long idUsuario);
}
