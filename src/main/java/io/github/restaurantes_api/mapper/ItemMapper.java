package io.github.restaurantes_api.mapper;

import io.github.restaurantes_api.dto.ItemDTO;
import io.github.restaurantes_api.entities.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemDTO toItemDTO(Item item){
        return ItemDTO.builder()
                .nome(item.getNome())
                .descricao(item.getDescricao())
                .preco(item.getPreco())
                .foto(item.getFoto())
                .consumoLocal(item.isConsumoLocal())
                .build();
    }
}
