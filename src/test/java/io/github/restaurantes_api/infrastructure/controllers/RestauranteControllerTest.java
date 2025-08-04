package io.github.restaurantes_api.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.restaurantes_api.application.presenters.RestaurantePresenter;
import io.github.restaurantes_api.core.domain.entities.enums.Categoria;
import io.github.restaurantes_api.core.domain.exceptions.ForbiddenException;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.dtos.EnderecoRequest;
import io.github.restaurantes_api.core.dtos.RestauranteRequest;
import io.github.restaurantes_api.core.dtos.RestauranteResponse;
import io.github.restaurantes_api.core.dtos.RestauranteUpdateDTO;
import io.github.restaurantes_api.infrastructure.web.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RestauranteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestaurantePresenter restaurantePresenter;

    @InjectMocks
    private RestauranteController restauranteController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private RestauranteRequest restauranteRequest;
    private RestauranteResponse restauranteResponse;

    @BeforeEach
    void setup() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(restauranteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        EnderecoRequest endereco = EnderecoRequest.builder()
                .rua("Rua Exemplo")
                .numero("123")
                .bairro("Centro")
                .cidade("Cidade")
                .cep("00000-000")
                .build();

        restauranteRequest = RestauranteRequest.builder()
                .nome("Restaurante Teste")
                .categoria(Categoria.ITALIANA)
                .horarioFuncionamento("08:00h - 20:00h")
                .proprietarioId(1L)
                .enderecoRequest(endereco)
                .build();

        restauranteResponse = RestauranteResponse.builder()
                .nome("Restaurante Teste")
                .categoria(Categoria.ITALIANA)
                .horarioFuncionamento("08:00h - 20:00h")
                .enderecoRequest(endereco)
                .build();
    }

    @Test
    void deveCadastrarRestaurante() throws Exception {
        mockMvc.perform(post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveListarRestaurantes() throws Exception {
        when(restaurantePresenter.listar(anyLong())).thenReturn(List.of(restauranteResponse));

        mockMvc.perform(get("/api/v1/restaurantes/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value(restauranteResponse.getNome()));
    }

    @Test
    void deveBuscarRestaurantePorId() throws Exception {
        when(restaurantePresenter.buscarPorId(anyLong(), anyLong())).thenReturn(restauranteResponse);

        mockMvc.perform(get("/api/v1/restaurantes/1/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(restauranteResponse.getNome()));
    }

    @Test
    void deveExcluirRestaurante() throws Exception {
        mockMvc.perform(delete("/api/v1/restaurantes/1/usuario/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveAtualizarRestaurante() throws Exception {
        RestauranteUpdateDTO updateDTO = RestauranteUpdateDTO.builder()
                .nome("Atualizado")
                .build();

        mockMvc.perform(put("/api/v1/restaurantes/1/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarForbiddenAoCadastrar() throws Exception {
        doThrow(new ForbiddenException("Acesso negado")).when(restaurantePresenter).cadastrar(any());

        mockMvc.perform(post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deveRetornarNotFoundAoExcluir() throws Exception {
        doThrow(new NotFoundException("Restaurante não encontrado"))
                .when(restaurantePresenter).excluir(anyLong(), anyLong());

        mockMvc.perform(delete("/api/v1/restaurantes/1/usuario/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Restaurante não encontrado"));
    }

    @Test
    void deveValidarCamposObrigatorios() throws Exception {
        RestauranteRequest invalido = RestauranteRequest.builder().build();

        mockMvc.perform(post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(5)));
    }
}
