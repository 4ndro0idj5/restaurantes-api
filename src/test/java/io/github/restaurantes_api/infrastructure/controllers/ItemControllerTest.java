package io.github.restaurantes_api.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.restaurantes_api.core.domain.entities.enums.Categoria;
import io.github.restaurantes_api.core.dtos.ItemDTO;
import io.github.restaurantes_api.core.dtos.ItemUpdateDTO;
import io.github.restaurantes_api.infrastructure.data.entities.EnderecoEntity;
import io.github.restaurantes_api.infrastructure.data.entities.RestauranteEntity;
import io.github.restaurantes_api.infrastructure.data.repositories.ItemRepository;
import io.github.restaurantes_api.infrastructure.data.repositories.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ItemRepository itemRepository;

    private ItemDTO itemDTO;
    private RestauranteEntity restaurante;

    @BeforeEach
    void setup() {
        // Limpa tudo antes de cada teste
        itemRepository.deleteAll();
        restauranteRepository.deleteAll();

        EnderecoEntity endereco = EnderecoEntity.builder()
                .rua("Rua Teste")
                .numero("123")
                .bairro("Centro")
                .cidade("Cidade")
                .cep("00000-000")
                .build();

        restaurante = RestauranteEntity.builder()
                .nome("Restaurante Teste")
                .categoria(Categoria.ITALIANA)
                .horarioFuncionamento("18:00h - 23:00h")
                .proprietarioId(1L)
                .endereco(endereco)
                .build();

        restaurante = restauranteRepository.save(restaurante);

        itemDTO = ItemDTO.builder()
                .nome("Pizza")
                .descricao("Deliciosa pizza")
                .preco("29.90")
                .foto("http://foto.com/pizza.jpg")
                .consumoLocal(true)
                .build();
    }

    @Test
    void deveCadastrarItem() throws Exception {
        mockMvc.perform(post("/restaurantes/" + restaurante.getId() + "/itens/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveValidarCampoObrigatorio() throws Exception {
        ItemDTO invalido = ItemDTO.builder().build();

        mockMvc.perform(post("/restaurantes/" + restaurante.getId() + "/itens/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(4)));
    }

    @Test
    void deveAtualizarItem() throws Exception {
        mockMvc.perform(post("/restaurantes/" + restaurante.getId() + "/itens/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(status().isCreated());


        Long itemId = itemRepository.findAll().get(0).getId();

        ItemUpdateDTO updateDTO = ItemUpdateDTO.builder()
                .nome("Pizza Atualizada")
                .build();

        mockMvc.perform(put("/restaurantes/" + restaurante.getId() + "/itens/" + itemId + "/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveExcluirItem() throws Exception {
        mockMvc.perform(post("/restaurantes/" + restaurante.getId() + "/itens/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(status().isCreated());

        Long itemId = itemRepository.findAll().get(0).getId();

        mockMvc.perform(delete("/restaurantes/" + restaurante.getId() + "/itens/" + itemId + "/usuario/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveListarItens() throws Exception {
        mockMvc.perform(post("/restaurantes/" + restaurante.getId() + "/itens/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/restaurantes/" + restaurante.getId() + "/itens/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Pizza"));
    }

    @Test
    void deveBuscarItemPorId() throws Exception {
        mockMvc.perform(post("/restaurantes/" + restaurante.getId() + "/itens/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(status().isCreated());

        Long itemId = itemRepository.findAll().get(0).getId();

        mockMvc.perform(get("/restaurantes/" + restaurante.getId() + "/itens/" + itemId + "/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pizza"));
    }
}
