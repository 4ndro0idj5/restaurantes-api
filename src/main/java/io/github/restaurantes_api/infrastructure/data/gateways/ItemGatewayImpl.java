package io.github.restaurantes_api.infrastructure.data.gateways;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.gateways.ItemGateway;
import io.github.restaurantes_api.infrastructure.data.datamappers.ItemDataMapper;
import io.github.restaurantes_api.infrastructure.data.entities.ItemEntity;
import io.github.restaurantes_api.infrastructure.data.entities.RestauranteEntity;
import io.github.restaurantes_api.infrastructure.data.repositories.ItemRepository;
import io.github.restaurantes_api.infrastructure.data.repositories.RestauranteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemGatewayImpl implements ItemGateway {
    private final RestauranteRepository restauranteRepository;
    private final ItemRepository itemRepository;
    private final ItemDataMapper mapper;

    public ItemGatewayImpl(ItemRepository itemRepository,
                                  ItemDataMapper mapper,
                                    RestauranteRepository restauranteRepository) {
        this.itemRepository = itemRepository;
        this.mapper = mapper;
        this.restauranteRepository= restauranteRepository;
    }


    @Override
    public Item salvar(Item item) {
        RestauranteEntity restaurante = RestauranteEntity.builder()
                .id(item.getRestauranteId())
                .build();

        ItemEntity entity = mapper.toEntity(item, restaurante);
        ItemEntity saved = itemRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<Item> listarPorRestauranteId(Long restauranteId) {
        return itemRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }



    @Override
    public Optional<Item> buscarPorIdEPorRestauranteId(Long id, Long restauranteId) {
        return itemRepository.findByIdAndRestauranteId(id, restauranteId)
                .map(mapper::toDomain);
    }



    @Transactional
    @Override
    public void excluir(Item item) {
        RestauranteEntity restaurante = restauranteRepository.findById(item.getRestauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        boolean removed = restaurante.getItens().removeIf(i -> i.getId().equals(item.getId()));

        if (!removed) {
            throw new RuntimeException("Item não encontrado para deleção");
        }

    }

}