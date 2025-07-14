package io.github.restaurantes_api.services;

import io.github.restaurantes_api.dto.ItemDTO;
import io.github.restaurantes_api.dto.ItemUpdateDTO;
import io.github.restaurantes_api.entities.Item;
import io.github.restaurantes_api.entities.Restaurante;
import io.github.restaurantes_api.mapper.ItemMapper;
import io.github.restaurantes_api.respositories.ItemRepository;
import io.github.restaurantes_api.respositories.RestauranteRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private UsuarioService usuarioService;

    private Restaurante restaurante;
    private Item item;
    private ItemDTO itemDTO;
    private ItemUpdateDTO itemUpdateDTO;

    @BeforeEach
    void setUp() {
        restaurante = Restaurante.builder()
                .id(1L)
                .proprietarioId(10L)
                .build();

        item = Item.builder()
                .id(1L)
                .nome("Pizza")
                .descricao("Pizza saborosa")
                .preco("50.00")
                .consumoLocal(true)
                .restaurante(restaurante)
                .build();

        itemDTO = ItemDTO.builder()
                .nome("Pizza")
                .descricao("Pizza saborosa")
                .preco("50.00")
                .consumoLocal(true)
                .build();

        itemUpdateDTO = ItemUpdateDTO.builder()
                .nome("Pizza grande")
                .build();
    }

    @Test
    void deveCadastrarItemComSucesso() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(itemMapper.toItemDTO(any(Item.class))).thenReturn(itemDTO);

        ItemDTO result = itemService.cadastrarItem(1L, itemDTO, 10L);

        assertNotNull(result);
        verify(usuarioService).validarUsuarioAutenticado(10L);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void deveListarItensPorRestaurante() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(itemRepository.findByRestaurante(restaurante)).thenReturn(List.of(item));
        when(itemMapper.toItemDTO(item)).thenReturn(itemDTO);

        List<ItemDTO> result = itemService.listarItensPorRestaurante(1L, 10L);

        assertFalse(result.isEmpty());
        verify(usuarioService).validarUsuarioAutenticado(10L);
    }

    @Test
    void deveBuscarItemPorId() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(itemRepository.findByIdAndRestauranteId(1L, 1L)).thenReturn(Optional.of(item));
        when(itemMapper.toItemDTO(item)).thenReturn(itemDTO);

        Optional<ItemDTO> result = itemService.buscarPorId(1L, 1L, 10L);

        assertTrue(result.isPresent());
        verify(usuarioService).validarUsuarioAutenticado(10L);
    }

    @Test
    void deveAtualizarItemComSucesso() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(itemRepository.findByIdAndRestauranteId(1L, 1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(itemMapper.toItemUpdateDTO(any(Item.class))).thenReturn(itemUpdateDTO);

        ItemUpdateDTO result = itemService.atualizar(1L, itemUpdateDTO, 1L, 10L);

        assertNotNull(result);
        verify(usuarioService).validarUsuarioAutenticado(10L);
    }

    @Test
    void deveExcluirItemComSucesso() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(itemRepository.findByIdAndRestauranteId(1L, 1L)).thenReturn(Optional.of(item));

        itemService.excluir(1L, 1L, 10L);

        verify(usuarioService).validarUsuarioAutenticado(10L);
        verify(itemRepository).delete(item);
    }

    @Test
    void deveLancarExcecao_QuandoCadastrarItem_RestauranteNaoEncontrado() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                itemService.cadastrarItem(1L, itemDTO, 10L));

        assertEquals("Restaurante não encontrado", ex.getMessage());
    }

    @Test
    void deveLancarExcecao_QuandoCadastrarItem_UsuarioNaoAutorizado() {
        restaurante = Restaurante.builder()
                .id(1L)
                .proprietarioId(99L) // Proprietário diferente
                .build();

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                itemService.cadastrarItem(1L, itemDTO, 10L));

        assertEquals("Usuário não tem permissão para incluir este prato.", ex.getMessage());
    }

    @Test
    void deveLancarExcecao_QuandoListarItens_RestauranteNaoEncontrado() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                itemService.listarItensPorRestaurante(1L, 10L));

        assertEquals("Restaurante não encontrado", ex.getMessage());
    }

    @Test
    void deveLancarExcecao_QuandoBuscarItemPorId_ItemNaoEncontrado() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(itemRepository.findByIdAndRestauranteId(1L, 1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                itemService.buscarPorId(1L, 1L, 10L));

        assertEquals("Item não encontrado para este restaurante", ex.getMessage());
    }

    @Test
    void deveLancarExcecao_QuandoExcluir_ItemNaoEncontrado() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(itemRepository.findByIdAndRestauranteId(1L, 1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                itemService.excluir(1L, 1L, 10L));

        assertEquals("Item não encontrado", ex.getMessage());
    }

}
