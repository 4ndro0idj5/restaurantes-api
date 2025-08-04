package io.github.restaurantes_api.application.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.gateways.ItemGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BuscarItemPorIdUseCaseImplTest {

    @InjectMocks
    private BuscarItemPorIdUseCaseImpl buscarItemPorIdUseCase;

    @Mock
    private ItemGateway itemGateway;

    @Mock
    private UsuarioServiceGateway usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarItem_QuandoEncontrado() {
        when(itemGateway.restauranteExiste(1L)).thenReturn(true);
        when(itemGateway.buscarPorIdEPorRestauranteId(1L, 1L)).thenReturn(Optional.of(new Item()));

        Optional<Item> result = buscarItemPorIdUseCase.executar(1L, 1L, 1L);

        assertTrue(result.isPresent());
        verify(usuarioService).validarUsuarioAutenticado(1L);
    }

    @Test
    void deveLancarNotFoundException_QuandoRestauranteNaoExiste() {
        when(itemGateway.restauranteExiste(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> buscarItemPorIdUseCase.executar(1L, 1L, 1L));
    }

    @Test
    void deveLancarNotFoundException_QuandoItemNaoExiste() {
        when(itemGateway.restauranteExiste(1L)).thenReturn(true);
        when(itemGateway.buscarPorIdEPorRestauranteId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> buscarItemPorIdUseCase.executar(1L, 1L, 1L));
    }
}
