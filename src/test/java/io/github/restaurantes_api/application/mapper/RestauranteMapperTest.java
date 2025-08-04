package io.github.restaurantes_api.application.mapper;

import io.github.restaurantes_api.core.domain.entities.Endereco;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.entities.enums.Categoria;
import io.github.restaurantes_api.core.dtos.EnderecoRequest;
import io.github.restaurantes_api.core.dtos.RestauranteRequest;
import io.github.restaurantes_api.core.dtos.RestauranteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteMapperTest {

    private RestauranteMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new RestauranteMapper();
    }

    @Test
    void deveMapearFromDTO() {
        EnderecoRequest enderecoRequest = EnderecoRequest.builder()
                .rua("Rua A")
                .numero("123")
                .bairro("Centro")
                .cidade("Cidade")
                .cep("12345-678")
                .build();

        RestauranteRequest request = RestauranteRequest.builder()
                .nome("Restaurante X")
                .categoria(Categoria.BRASILEIRA)
                .horarioFuncionamento("10:00 - 22:00")
                .proprietarioId(1L)
                .enderecoRequest(enderecoRequest)
                .build();

        Restaurante restaurante = mapper.fromDTO(request);

        assertEquals(request.getNome(), restaurante.getNome());
        assertEquals(request.getCategoria(), restaurante.getCategoria());
        assertEquals(request.getHorarioFuncionamento(), restaurante.getHorarioFuncionamento());
        assertEquals(request.getProprietarioId(), restaurante.getProprietarioId());
        assertNotNull(restaurante.getEndereco());
        assertEquals(enderecoRequest.getRua(), restaurante.getEndereco().getRua());
    }

    @Test
    void deveMapearToResponseDTO() {
        Endereco endereco = Endereco.builder()
                .rua("Rua B")
                .numero("456")
                .bairro("Bairro B")
                .cidade("Cidade B")
                .cep("98765-432")
                .build();

        Restaurante restaurante = Restaurante.builder()
                .nome("Restaurante Y")
                .categoria(Categoria.ITALIANA)
                .horarioFuncionamento("11:00 - 23:00")
                .endereco(endereco)
                .build();

        RestauranteResponse response = mapper.toResponseDTO(restaurante);

        assertEquals(restaurante.getNome(), response.getNome());
        assertEquals(restaurante.getCategoria(), response.getCategoria());
        assertEquals(restaurante.getHorarioFuncionamento(), response.getHorarioFuncionamento());
        assertNotNull(response.getEnderecoRequest());
        assertEquals(endereco.getRua(), response.getEnderecoRequest().getRua());
    }

    @Test
    void deveMapearToEnderecoRequest() {
        Endereco endereco = Endereco.builder()
                .rua("Rua C")
                .numero("789")
                .bairro("Bairro C")
                .cidade("Cidade C")
                .cep("11223-445")
                .build();

        EnderecoRequest request = mapper.toEnderecoRequest(endereco);

        assertEquals(endereco.getRua(), request.getRua());
        assertEquals(endereco.getNumero(), request.getNumero());
        assertEquals(endereco.getBairro(), request.getBairro());
        assertEquals(endereco.getCidade(), request.getCidade());
        assertEquals(endereco.getCep(), request.getCep());
    }

    @Test
    void deveRetornarNullQuandoEnderecoNulo() {
        assertNull(mapper.toEnderecoRequest(null));
    }
}
