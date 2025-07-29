package io.github.restaurantes_api.infrastructure.data.gateways;

import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.infrastructure.data.datamappers.RestauranteDataMapper;
import io.github.restaurantes_api.infrastructure.data.entities.RestauranteEntity;
import io.github.restaurantes_api.infrastructure.data.repositories.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestauranteGatewayImpl implements RestauranteGateway {

    private final RestauranteRepository restauranteRepository;
    private final RestauranteDataMapper mapper;

    public RestauranteGatewayImpl(RestauranteRepository restauranteRepository,
                                  RestauranteDataMapper mapper) {
        this.restauranteRepository = restauranteRepository;
        this.mapper = mapper;
    }

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        RestauranteEntity entity = mapper.toEntity(restaurante);
        RestauranteEntity saved = restauranteRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<Restaurante> listarTodos() {
        return restauranteRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void excluir(Restaurante restaurante) {
        RestauranteEntity entity = mapper.toEntity(restaurante);
        restauranteRepository.delete(entity);
    }
}