package io.github.restaurantes_api.mapper;

import io.github.restaurantes_api.dto.RestauranteResponse;
import io.github.restaurantes_api.entities.Restaurante;
import org.junit.jupiter.api.Test;
import io.github.restaurantes_api.dto.EnderecoRequest;
import io.github.restaurantes_api.dto.RestauranteRequest;
import org.junit.jupiter.api.BeforeEach;

import io.github.restaurantes_api.entities.Categoria;

import static org.assertj.core.api.Assertions.assertThat;


class RestauranteMapperTest {

    private RestauranteMapper restauranteMapper;

    @BeforeEach
    void setUp() {
        restauranteMapper = new RestauranteMapper();
    }

    @Test
    void deveConverterDeRequestParaEntity() {
        EnderecoRequest enderecoRequest = EnderecoRequest.builder()
                .rua("Rua A")
                .numero("123")
                .bairro("Centro")
                .cidade("São Paulo")
                .cep("01000-000")
                .build();

        RestauranteRequest request = RestauranteRequest.builder()
                .nome("Pizzaria Bella Napoli")
                .categoria(Categoria.ITALIANA)
                .horarioFuncionamento("18:00 - 23:00")
                .proprietarioId(1L)
                .enderecoRequest(enderecoRequest)
                .build();

        Restaurante restaurante = restauranteMapper.fromDTO(request);

        assertThat(restaurante.getNome()).isEqualTo("Pizzaria Bella Napoli");
        assertThat(restaurante.getCategoria()).isEqualTo(Categoria.ITALIANA);
        assertThat(restaurante.getHorarioFuncionamento()).isEqualTo("18:00 - 23:00");
        assertThat(restaurante.getProprietarioId()).isEqualTo(1L);
        assertThat(restaurante.getEndereco()).isNotNull();
        assertThat(restaurante.getEndereco().getRua()).isEqualTo("Rua A");
        assertThat(restaurante.getEndereco().getCidade()).isEqualTo("São Paulo");
    }

    @Test
    void deveConverterDeEntityParaResponse() {
        Restaurante restaurante = Restaurante.builder()
                .nome("Pizzaria Bella Napoli")
                .categoria(Categoria.ITALIANA)
                .horarioFuncionamento("18:00 - 23:00")
                .build();

        RestauranteResponse response = restauranteMapper.toResponseDTO(restaurante);

        assertThat(response.getNome()).isEqualTo("Pizzaria Bella Napoli");
        assertThat(response.getCategoria()).isEqualTo(Categoria.ITALIANA);
        assertThat(response.getHorarioFuncionamento()).isEqualTo("18:00 - 23:00");
    }
}