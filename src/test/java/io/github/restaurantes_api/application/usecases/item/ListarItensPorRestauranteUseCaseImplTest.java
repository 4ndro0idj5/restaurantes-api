package io.github.restaurantes_api.application.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.gateways.ItemGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ListarItensPorRestauranteUseCaseImplTest {

    @InjectMocks
    private ListarItensPorRestauranteUseCaseImpl listarItensUseCase;

    @Mock
    private ItemGateway itemGateway;

    @Mock
    private UsuarioServiceGateway usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarItens_ComSucesso() {
        when(itemGateway.restauranteExiste(1L)).thenReturn(true);
        when(itemGateway.listarPorRestauranteId(1L)).thenReturn(Collections.singletonList(new Item()));

        List<Item> itens = listarItensUseCase.executar(1L, 1L);

        assertEquals(1, itens.size());
        verify(usuarioService).validarUsuarioAutenticado(1L);
    }

    @Test
    void deveLancarNotFoundException_QuandoRestauranteNaoExiste() {
        when(itemGateway.restauranteExiste(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> listarItensUseCase.executar(1L, 1L));
    }
}
