package io.github.restaurantes_api.infrastructure.data.datamappers;


import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.infrastructure.data.entities.ItemEntity;
import io.github.restaurantes_api.infrastructure.data.entities.RestauranteEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemDataMapper {

    public ItemEntity toEntity(Item item, RestauranteEntity restauranteEntity) {
        ItemEntity entity = new ItemEntity();
        entity.setId(item.getId());
        entity.setNome(item.getNome());
        entity.setDescricao(item.getDescricao());
        entity.setPreco(item.getPreco());
        entity.setFoto(item.getFoto());
        entity.setConsumoLocal(item.isConsumoLocal());
        entity.setRestaurante(restauranteEntity);
        return entity;
    }

    public Item toDomain(ItemEntity entity) {
        return Item.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .preco(entity.getPreco())
                .foto(entity.getFoto())
                .consumoLocal(entity.isConsumoLocal())
                .restauranteId(entity.getRestaurante().getId())
                .build();
    }

    public List<ItemEntity> toEntityList(List<Item> items, RestauranteEntity restauranteEntity) {
        return items.stream()
                .map(item -> toEntity(item, restauranteEntity))
                .toList();
    }
}