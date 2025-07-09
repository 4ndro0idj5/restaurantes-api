package io.github.restaurantes_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.restaurantes_api.dto.RestauranteRequest;
import io.github.restaurantes_api.dto.RestauranteResponse;
import io.github.restaurantes_api.dto.RestauranteUpdateDTO;
import io.github.restaurantes_api.services.RestauranteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import io.github.restaurantes_api.entities.Categoria;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestauranteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestauranteService restauranteService;

    @InjectMocks
    private RestauranteController restauranteController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restauranteController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void deveCadastrarRestauranteComSucesso() throws Exception {
        RestauranteRequest request = RestauranteRequest.builder()
                .nome("Pizzaria Bella Napoli")
                .horarioFuncionamento("18:00 - 23:00")
                .categoria(Categoria.ITALIANA)
                .build();

        mockMvc.perform(post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(restauranteService).cadastrar(any(RestauranteRequest.class));
    }

    @Test
    void deveListarRestaurantesPorUsuario() throws Exception {
        Long idUsuario = 1L;

        RestauranteResponse response = RestauranteResponse.builder()
                .nome("Bella Napoli")
                .horarioFuncionamento("18:00 - 23:00")
                .categoria(Categoria.ITALIANA)
                .build();

        when(restauranteService.listarTodos(idUsuario)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/restaurantes/usuario/{idUsuario}", idUsuario))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Bella Napoli"))
                .andExpect(jsonPath("$[0].horarioFuncionamento").value("18:00 - 23:00"))
                .andExpect(jsonPath("$[0].categoria").value("ITALIANA"));

        verify(restauranteService).listarTodos(idUsuario);
    }

    @Test
    void deveBuscarRestaurantePorId() throws Exception {
        Long id = 1L;
        Long idUsuario = 2L;

        RestauranteResponse response = RestauranteResponse.builder()
                .nome("Bella Napoli")
                .horarioFuncionamento("18:00 - 23:00")
                .categoria(Categoria.ITALIANA)
                .build();

        when(restauranteService.buscarPorId(id, idUsuario)).thenReturn(response);

        mockMvc.perform(get("/api/v1/restaurantes/{id}/usuario/{idUsuario}", id, idUsuario))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Bella Napoli"))
                .andExpect(jsonPath("$.horarioFuncionamento").value("18:00 - 23:00"))
                .andExpect(jsonPath("$.categoria").value("ITALIANA"));

        verify(restauranteService).buscarPorId(id, idUsuario);
    }

    @Test
    void deveExcluirRestaurante() throws Exception {
        Long id = 1L;
        Long idUsuario = 2L;

        mockMvc.perform(delete("/api/v1/restaurantes/{id}/usuario/{idUsuario}", id, idUsuario))
                .andExpect(status().isNoContent());

        verify(restauranteService).excluir(id, idUsuario);
    }

    @Test
    void deveAtualizarRestaurante() throws Exception {
        Long id = 1L;
        Long idUsuario = 2L;

        RestauranteUpdateDTO updateDTO = RestauranteUpdateDTO.builder()
                .nome("Novo Nome")
                .horarioFuncionamento("19:00 - 23:30")
                .categoria(Categoria.BRASILEIRA)
                .build();

        RestauranteResponse response = RestauranteResponse.builder()
                .nome("Novo Nome")
                .horarioFuncionamento("19:00 - 23:30")
                .categoria(Categoria.BRASILEIRA)
                .build();

        when(restauranteService.atualizar(any(RestauranteUpdateDTO.class), eq(id), eq(idUsuario)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/restaurantes/{id}/usuario/{idUsuario}", id, idUsuario)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Novo Nome"))
                .andExpect(jsonPath("$.horarioFuncionamento").value("19:00 - 23:30"))
                .andExpect(jsonPath("$.categoria").value("BRASILEIRA"));

        verify(restauranteService).atualizar(any(RestauranteUpdateDTO.class), eq(id), eq(idUsuario));
    }

}