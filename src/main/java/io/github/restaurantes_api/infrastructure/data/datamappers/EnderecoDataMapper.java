package io.github.restaurantes_api.infrastructure.data.datamappers;

import io.github.restaurantes_api.core.domain.entities.Endereco;
import io.github.restaurantes_api.infrastructure.data.entities.EnderecoEntity;

import org.springframework.stereotype.Component;


@Component
public class EnderecoDataMapper {

    public EnderecoEntity toEntity(Endereco endereco) {
        return EnderecoEntity.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .cep(endereco.getCep())
                .build();
    }

    public Endereco toDomain(EnderecoEntity entity) {
        return Endereco.builder()
                .id(entity.getId())
                .rua(entity.getRua())
                .numero(entity.getNumero())
                .bairro(entity.getBairro())
                .cidade(entity.getCidade())
                .cep(entity.getCep())
                .build();
    }
}


