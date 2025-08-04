package io.github.restaurantes_api.application.mapper;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.dtos.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {

    private ItemMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ItemMapper();
    }

    @Test
    void deveMapearFromDTO() {
        ItemDTO dto = ItemDTO.builder()
                .nome("Pizza")
                .descricao("Deliciosa")
                .preco("25.00")
                .foto("http://imagem.jpg")
                .consumoLocal(true)
                .build();

        Item item = mapper.fromDTO(dto);

        assertEquals(dto.getNome(), item.getNome());
        assertEquals(dto.getDescricao(), item.getDescricao());
        assertEquals(dto.getPreco(), item.getPreco());
        assertEquals(dto.getFoto(), item.getFoto());
        assertEquals(dto.getConsumoLocal(), item.isConsumoLocal());
    }

    @Test
    void deveMapearToItemDTO() {
        Item item = Item.builder()
                .nome("Pizza")
                .descricao("Deliciosa")
                .preco("25.00")
                .foto("http://imagem.jpg")
                .consumoLocal(true)
                .build();

        ItemDTO dto = mapper.toItemDTO(item);

        assertEquals(item.getNome(), dto.getNome());
        assertEquals(item.getDescricao(), dto.getDescricao());
        assertEquals(item.getPreco(), dto.getPreco());
        assertEquals(item.getFoto(), dto.getFoto());
        assertEquals(item.isConsumoLocal(), dto.getConsumoLocal());
    }
}
