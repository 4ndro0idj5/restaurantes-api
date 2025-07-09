package io.github.restaurantes_api.mapper;

import io.github.restaurantes_api.dto.ItemDTO;
import io.github.restaurantes_api.entities.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ItemMapperTest {

    private ItemMapper itemMapper;

    @BeforeEach
    void setup() {
        itemMapper = new ItemMapper();
    }

    @Test
    void deveMapearItemParaItemDTO() {

        Item item = Item.builder()
                .nome("Pizza Margherita")
                .descricao("Molho de tomate, mussarela e manjericão")
                .preco(39.90)
                .foto("https://cdn.exemplo.com/pizza.jpg")
                .consumoLocal(true)
                .build();


        ItemDTO dto = itemMapper.toItemDTO(item);


        assertThat(dto).isNotNull();
        assertThat(dto.getNome()).isEqualTo(item.getNome());
        assertThat(dto.getDescricao()).isEqualTo(item.getDescricao());
        assertThat(dto.getPreco()).isEqualTo(item.getPreco());
        assertThat(dto.getFoto()).isEqualTo(item.getFoto());
        assertThat(dto.isConsumoLocal()).isEqualTo(item.isConsumoLocal());
    }
}