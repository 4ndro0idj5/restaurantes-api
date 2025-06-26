package io.github.restaurantes_api.services;


import io.github.restaurantes_api.entities.Restaurante;
import io.github.restaurantes_api.mapper.RestauranteMapper;
import io.github.restaurantes_api.dto.RestauranteRequest;
import io.github.restaurantes_api.respositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteMapper restauranteMapper;
    private final RestauranteRepository restauranteRepository;

    public Restaurante cadastrar(RestauranteRequest request) {
        Restaurante restaurante = restauranteRepository.save(restauranteMapper.fromDTO(request));

        return restaurante;
    }
}

