package io.github.restaurantes_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.restaurantes_api.dto.ItemDTO;
import io.github.restaurantes_api.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .alwaysDo(print())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void deveCadastrarItemComSucesso() throws Exception {
        Long restauranteId = 1L;
        Long usuarioId = 100L;

        ItemDTO novoItem = ItemDTO.builder()
                .nome("Pizza Margherita")
                .descricao("Molho de tomate, mussarela e manjericão")
                .preco(39.90)
                .foto("https://cdn.exemplo.com/pizza.jpg")
                .consumoLocal(true)
                .build();

        when(itemService.cadastrarItem(eq(restauranteId), any(ItemDTO.class), eq(usuarioId)))
                .thenReturn(novoItem);

        mockMvc.perform(post("/api/v1/restaurantes/{restauranteId}/itens/usuario/{usuarioId}", restauranteId, usuarioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Pizza Margherita"))
                .andExpect(jsonPath("$.consumoLocal").value(true));

        verify(itemService).cadastrarItem(eq(restauranteId), any(ItemDTO.class), eq(usuarioId));
    }
    @Test
    void deveAtualizarItemComSucesso() throws Exception {
        Long restauranteId = 1L;
        Long itemId = 99L;
        Long usuarioId = 10L;

        ItemDTO atualizado = ItemDTO.builder()
                .nome("Pizza Frango")
                .descricao("Frango com catupiry")
                .preco(44.50)
                .foto("https://cdn.exemplo.com/frango.jpg")
                .consumoLocal(false)
                .build();

        when(itemService.atualizar(restauranteId, atualizado, itemId, usuarioId))
                .thenReturn(atualizado);

        mockMvc.perform(put("/api/v1/restaurantes/{restauranteId}/itens/{id}/usuario/{usuarioId}",
                        restauranteId, itemId, usuarioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Pizza Frango"));
    }

    @Test
    void deveExcluirItemComSucesso() throws Exception {
        Long restauranteId = 1L;
        Long itemId = 99L;
        Long usuarioId = 10L;

        doNothing().when(itemService).excluir(restauranteId, itemId, usuarioId);

        mockMvc.perform(delete("/api/v1/restaurantes/{restauranteId}/itens/{id}/usuario/{usuarioId}",
                        restauranteId, itemId, usuarioId))
                .andExpect(status().isNoContent());

        verify(itemService).excluir(restauranteId, itemId, usuarioId);
    }

    @Test
    void deveBuscarItemPorId() throws Exception {
        Long restauranteId = 1L;
        Long itemId = 99L;
        Long usuarioId = 10L;

        ItemDTO item = ItemDTO.builder()
                .nome("Pizza Marguerita")
                .descricao("Tomate e manjericão")
                .preco(39.90)
                .foto("https://cdn.exemplo.com/marguerita.jpg")
                .consumoLocal(false)
                .build();

        when(itemService.buscarPorId(itemId, restauranteId, usuarioId))
                .thenReturn(Optional.of(item));

        mockMvc.perform(get("/api/v1/restaurantes/{restauranteId}/itens/{id}/usuario/{usuarioId}",
                        restauranteId, itemId, usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pizza Marguerita"));
    }
}