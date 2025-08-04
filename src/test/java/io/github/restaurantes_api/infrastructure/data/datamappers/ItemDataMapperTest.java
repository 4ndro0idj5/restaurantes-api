package io.github.restaurantes_api.infrastructure.data.datamappers;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.infrastructure.data.entities.ItemEntity;
import io.github.restaurantes_api.infrastructure.data.entities.RestauranteEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemDataMapperTest {

    private final ItemDataMapper mapper = new ItemDataMapper();

    @Test
    void deveMapearDomainParaEntityComRestaurante() {
        Item item = Item.builder()
                .id(1L)
                .nome("Pizza")
                .descricao("Deliciosa pizza")
                .preco("29.90")
                .foto("http://foto.com/pizza.jpg")
                .consumoLocal(true)
                .build();

        RestauranteEntity restaurante = RestauranteEntity.builder()
                .id(2L)
                .build();

        ItemEntity entity = mapper.toEntity(item, restaurante);

        assertThat(entity.getId()).isEqualTo(item.getId());
        assertThat(entity.getNome()).isEqualTo(item.getNome());
        assertThat(entity.getDescricao()).isEqualTo(item.getDescricao());
        assertThat(entity.getPreco()).isEqualTo(item.getPreco());
        assertThat(entity.getFoto()).isEqualTo(item.getFoto());
        assertThat(entity.isConsumoLocal()).isTrue();
        assertThat(entity.getRestaurante().getId()).isEqualTo(restaurante.getId());
    }

    @Test
    void deveMapearDomainParaEntityQuandoRestauranteIdExiste() {
        Item item = Item.builder()
                .id(1L)
                .nome("Pizza")
                .descricao("Deliciosa pizza")
                .preco("29.90")
                .foto("http://foto.com/pizza.jpg")
                .consumoLocal(true)
                .restauranteId(2L)
                .build();

        ItemEntity entity = mapper.toEntity(item);

        assertThat(entity.getId()).isEqualTo(item.getId());
        assertThat(entity.getRestaurante()).isNotNull();
        assertThat(entity.getRestaurante().getId()).isEqualTo(2L);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteIdForNulo() {
        Item item = Item.builder()
                .id(1L)
                .nome("Pizza")
                .build();

        assertThatThrownBy(() -> mapper.toEntity(item))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("RestauranteId não pode ser nulo");
    }

    @Test
    void deveMapearEntityParaDomain() {
        RestauranteEntity restaurante = RestauranteEntity.builder()
                .id(2L)
                .build();

        ItemEntity entity = ItemEntity.builder()
                .id(1L)
                .nome("Pizza")
                .descricao("Deliciosa pizza")
                .preco("29.90")
                .foto("http://foto.com/pizza.jpg")
                .consumoLocal(true)
                .restaurante(restaurante)
                .build();

        Item item = mapper.toDomain(entity);

        assertThat(item.getId()).isEqualTo(entity.getId());
        assertThat(item.getNome()).isEqualTo(entity.getNome());
        assertThat(item.getDescricao()).isEqualTo(entity.getDescricao());
        assertThat(item.getPreco()).isEqualTo(entity.getPreco());
        assertThat(item.getFoto()).isEqualTo(entity.getFoto());
        assertThat(item.isConsumoLocal()).isTrue();
        assertThat(item.getRestauranteId()).isEqualTo(restaurante.getId());
    }

    @Test
    void deveMapearListaDomainParaListaEntity() {
        Item item1 = Item.builder().id(1L).nome("Pizza").restauranteId(2L).build();
        Item item2 = Item.builder().id(2L).nome("Hamburger").restauranteId(2L).build();

        RestauranteEntity restaurante = RestauranteEntity.builder()
                .id(2L)
                .build();

        List<ItemEntity> entities = mapper.toEntityList(List.of(item1, item2), restaurante);

        assertThat(entities).hasSize(2);
        assertThat(entities.get(0).getNome()).isEqualTo("Pizza");
        assertThat(entities.get(1).getNome()).isEqualTo("Hamburger");
    }

    @Test
    void deveRetornarListaVaziaQuandoListaDomainForNula() {
        RestauranteEntity restaurante = RestauranteEntity.builder().id(2L).build();

        List<ItemEntity> entities = mapper.toEntityList(null, restaurante);

        assertThat(entities).isEmpty();
    }
}
