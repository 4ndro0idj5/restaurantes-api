package io.github.restaurantes_api.infrastructure.data.datamappers;

import io.github.restaurantes_api.core.domain.entities.Endereco;
import io.github.restaurantes_api.infrastructure.data.entities.EnderecoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnderecoDataMapperTest {

    private EnderecoDataMapper enderecoDataMapper;

    @BeforeEach
    void setUp() {
        enderecoDataMapper = new EnderecoDataMapper();
    }

    @Test
    void deveMapearDomainParaEntity() {
        Endereco endereco = Endereco.builder()
                .id(1L)
                .rua("Rua A")
                .numero("123")
                .bairro("Centro")
                .cidade("Cidade X")
                .cep("12345-678")
                .build();

        EnderecoEntity entity = enderecoDataMapper.toEntity(endereco);

        assertThat(entity.getId()).isEqualTo(endereco.getId());
        assertThat(entity.getRua()).isEqualTo(endereco.getRua());
        assertThat(entity.getNumero()).isEqualTo(endereco.getNumero());
        assertThat(entity.getBairro()).isEqualTo(endereco.getBairro());
        assertThat(entity.getCidade()).isEqualTo(endereco.getCidade());
        assertThat(entity.getCep()).isEqualTo(endereco.getCep());
    }

    @Test
    void deveMapearEntityParaDomain() {
        EnderecoEntity entity = EnderecoEntity.builder()
                .id(1L)
                .rua("Rua B")
                .numero("456")
                .bairro("Bairro Y")
                .cidade("Cidade Z")
                .cep("98765-432")
                .build();

        Endereco endereco = enderecoDataMapper.toDomain(entity);

        assertThat(endereco.getId()).isEqualTo(entity.getId());
        assertThat(endereco.getRua()).isEqualTo(entity.getRua());
        assertThat(endereco.getNumero()).isEqualTo(entity.getNumero());
        assertThat(endereco.getBairro()).isEqualTo(entity.getBairro());
        assertThat(endereco.getCidade()).isEqualTo(entity.getCidade());
        assertThat(endereco.getCep()).isEqualTo(entity.getCep());
    }
}
