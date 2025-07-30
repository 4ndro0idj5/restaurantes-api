package io.github.restaurantes_api.infrastructure.data.datamappers;

import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.infrastructure.data.entities.RestauranteEntity;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RestauranteDataMapper {

    private final EnderecoDataMapper enderecoDataMapper;
    private final ItemDataMapper itemDataMapper;

    public RestauranteDataMapper(EnderecoDataMapper enderecoDataMapper, ItemDataMapper itemDataMapper) {
        this.enderecoDataMapper = enderecoDataMapper;
        this.itemDataMapper = itemDataMapper;
    }

    public RestauranteEntity toEntity(Restaurante restaurante) {
        RestauranteEntity restauranteEntity = RestauranteEntity.builder()
                .id(restaurante.getId())
                .nome(restaurante.getNome())
                .categoria(restaurante.getCategoria())
                .horarioFuncionamento(restaurante.getHorarioFuncionamento())
                .proprietarioId(restaurante.getProprietarioId())
                .endereco(enderecoDataMapper.toEntity(restaurante.getEndereco()))
                .build();

        restauranteEntity.setItens(
                itemDataMapper.toEntityList(
                        restaurante.getItens() != null ? restaurante.getItens() : List.of(),
                        restauranteEntity
                )
        );

        return restauranteEntity;
    }

    public Restaurante toDomain(RestauranteEntity entity) {
        return Restaurante.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .categoria(entity.getCategoria())
                .horarioFuncionamento(entity.getHorarioFuncionamento())
                .proprietarioId(entity.getProprietarioId())
                .endereco(enderecoDataMapper.toDomain(entity.getEndereco()))
                .itens(entity.getItens().stream().map(itemDataMapper::toDomain).toList())
                .build();
    }
}