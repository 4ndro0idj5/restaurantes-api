package io.github.restaurantes_api.infrastructure.data.datamappers;


import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.infrastructure.data.entities.ItemEntity;
import io.github.restaurantes_api.infrastructure.data.entities.RestauranteEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemDataMapper {


    public ItemEntity toEntity(Item item, RestauranteEntity restaurante) {
        return ItemEntity.builder()
                .id(item.getId())
                .nome(item.getNome())
                .descricao(item.getDescricao())
                .preco(item.getPreco())
                .foto(item.getFoto())
                .consumoLocal(item.isConsumoLocal())
                .restaurante(restaurante)
                .build();
    }


    public ItemEntity toEntity(Item item) {
        if (item.getRestauranteId() == null) {
            throw new IllegalArgumentException("RestauranteId não pode ser nulo para mapear ItemEntity");
        }

        RestauranteEntity restaurante = RestauranteEntity.builder()
                .id(item.getRestauranteId())
                .build();

        return toEntity(item, restaurante);
    }


    public Item toDomain(ItemEntity entity) {
        return Item.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .preco(entity.getPreco())
                .foto(entity.getFoto())
                .consumoLocal(entity.isConsumoLocal())
                .restauranteId(entity.getRestaurante() != null ? entity.getRestaurante().getId() : null)
                .build();
    }


    public List<ItemEntity> toEntityList(List<Item> items, RestauranteEntity restaurante) {
        if (items == null) return List.of();
        return items.stream()
                .map(item -> toEntity(item, restaurante))
                .toList();
    }
}