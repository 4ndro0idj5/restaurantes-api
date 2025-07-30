package io.github.restaurantes_api.application.mapper;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.dtos.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public Item fromDTO(ItemDTO itemDTO){
        return Item.builder()
                .nome(itemDTO.getNome())
                .descricao(itemDTO.getDescricao())
                .preco(itemDTO.getPreco())
                .foto(itemDTO.getFoto())
                .consumoLocal(itemDTO.getConsumoLocal())
                .build();
    }
    public ItemDTO toItemDTO(Item item) {
        return ItemDTO.builder()
                .nome(item.getNome())
                .descricao(item.getDescricao())
                .preco(item.getPreco())
                .foto(item.getFoto())
                .consumoLocal(item.isConsumoLocal())
                .build();
    }
}
