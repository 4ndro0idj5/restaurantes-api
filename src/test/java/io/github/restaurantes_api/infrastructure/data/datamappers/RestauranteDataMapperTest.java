package io.github.restaurantes_api.infrastructure.data.datamappers;

import io.github.restaurantes_api.core.domain.entities.Endereco;
import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.entities.enums.Categoria;
import io.github.restaurantes_api.infrastructure.data.entities.EnderecoEntity;
import io.github.restaurantes_api.infrastructure.data.entities.ItemEntity;
import io.github.restaurantes_api.infrastructure.data.entities.RestauranteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RestauranteDataMapperTest {

    private EnderecoDataMapper enderecoDataMapper;
    private ItemDataMapper itemDataMapper;
    private RestauranteDataMapper restauranteDataMapper;

    @BeforeEach
    void setUp() {
        enderecoDataMapper = mock(EnderecoDataMapper.class);
        itemDataMapper = mock(ItemDataMapper.class);
        restauranteDataMapper = new RestauranteDataMapper(enderecoDataMapper, itemDataMapper);
    }

    @Test
    void deveMapearDomainParaEntity() {
        Endereco endereco = Endereco.builder().rua("Rua Teste").build();
        EnderecoEntity enderecoEntity = EnderecoEntity.builder().rua("Rua Teste").build();
        when(enderecoDataMapper.toEntity(endereco)).thenReturn(enderecoEntity);

        Item item = Item.builder().id(1L).nome("Pizza").restauranteId(2L).build();
        ItemEntity itemEntity = ItemEntity.builder().id(1L).nome("Pizza").build();
        when(itemDataMapper.toEntityList(anyList(), any())).thenReturn(List.of(itemEntity));

        Restaurante restaurante = Restaurante.builder()
                .id(2L)
                .nome("Restaurante Teste")
                .categoria(Categoria.ITALIANA)
                .horarioFuncionamento("18:00h - 23:00h")
                .proprietarioId(10L)
                .endereco(endereco)
                .itens(List.of(item))
                .build();

        RestauranteEntity entity = restauranteDataMapper.toEntity(restaurante);

        assertThat(entity.getId()).isEqualTo(restaurante.getId());
        assertThat(entity.getNome()).isEqualTo(restaurante.getNome());
        assertThat(entity.getCategoria()).isEqualTo(restaurante.getCategoria());
        assertThat(entity.getHorarioFuncionamento()).isEqualTo(restaurante.getHorarioFuncionamento());
        assertThat(entity.getProprietarioId()).isEqualTo(restaurante.getProprietarioId());
        assertThat(entity.getEndereco()).isEqualTo(enderecoEntity);
        assertThat(entity.getItens()).hasSize(1).contains(itemEntity);

        verify(enderecoDataMapper).toEntity(endereco);
        verify(itemDataMapper).toEntityList(anyList(), eq(entity));
    }

    @Test
    void deveMapearEntityParaDomain() {
        EnderecoEntity enderecoEntity = EnderecoEntity.builder().rua("Rua Teste").build();
        Endereco endereco = Endereco.builder().rua("Rua Teste").build();
        when(enderecoDataMapper.toDomain(enderecoEntity)).thenReturn(endereco);

        ItemEntity itemEntity = ItemEntity.builder().id(1L).nome("Pizza").build();
        Item item = Item.builder().id(1L).nome("Pizza").build();
        when(itemDataMapper.toDomain(itemEntity)).thenReturn(item);

        RestauranteEntity restauranteEntity = RestauranteEntity.builder()
                .id(2L)
                .nome("Restaurante Teste")
                .categoria(Categoria.ITALIANA)
                .horarioFuncionamento("18:00h - 23:00h")
                .proprietarioId(10L)
                .endereco(enderecoEntity)
                .itens(List.of(itemEntity))
                .build();

        Restaurante restaurante = restauranteDataMapper.toDomain(restauranteEntity);

        assertThat(restaurante.getId()).isEqualTo(restauranteEntity.getId());
        assertThat(restaurante.getNome()).isEqualTo(restauranteEntity.getNome());
        assertThat(restaurante.getCategoria()).isEqualTo(restauranteEntity.getCategoria());
        assertThat(restaurante.getHorarioFuncionamento()).isEqualTo(restauranteEntity.getHorarioFuncionamento());
        assertThat(restaurante.getProprietarioId()).isEqualTo(restauranteEntity.getProprietarioId());
        assertThat(restaurante.getEndereco()).isEqualTo(endereco);
        assertThat(restaurante.getItens()).hasSize(1).contains(item);

        verify(enderecoDataMapper).toDomain(enderecoEntity);
        verify(itemDataMapper).toDomain(itemEntity);
    }
}
