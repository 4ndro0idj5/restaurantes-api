package io.github.restaurantes_api.services;


import io.github.restaurantes_api.dto.RestauranteResponse;
import io.github.restaurantes_api.entities.Restaurante;
import io.github.restaurantes_api.mapper.RestauranteMapper;
import io.github.restaurantes_api.dto.RestauranteRequest;
import io.github.restaurantes_api.respositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteMapper restauranteMapper;
    private final RestauranteRepository restauranteRepository;
    private final UsuarioService usuarioService;

    public Restaurante cadastrar(RestauranteRequest request) {
        usuarioService.validarUsuarioAutenticadoEProprietario(request.getProprietarioId());
        Restaurante restaurante = restauranteRepository.save(restauranteMapper.fromDTO(request));
        return restaurante;
    }

    public List<RestauranteResponse> listarTodos() {

        return restauranteRepository.findAll().stream()
                .map(restauranteMapper::toResponseDTO)
                .toList();
    }
}

