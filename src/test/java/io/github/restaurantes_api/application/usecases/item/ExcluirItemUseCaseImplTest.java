package io.github.restaurantes_api.application.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.exceptions.ForbiddenException;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.gateways.ItemGateway;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ExcluirItemUseCaseImplTest {

    @InjectMocks
    private ExcluirItemUseCaseImpl excluirItemUseCase;

    @Mock
    private RestauranteGateway restauranteGateway;

    @Mock
    private ItemGateway itemGateway;

    @Mock
    private UsuarioServiceGateway usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveExcluirItem_ComSucesso() {
        Restaurante restaurante = Restaurante.builder().id(1L).proprietarioId(1L).build();
        Item item = Item.builder().id(1L).restauranteId(1L).build();

        when(restauranteGateway.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(itemGateway.buscarPorIdEPorRestauranteId(1L, 1L)).thenReturn(Optional.of(item));

        excluirItemUseCase.executar(1L, 1L, 1L);

        verify(usuarioService).validarUsuarioAutenticado(1L);
        verify(itemGateway).excluir(item);
    }

    @Test
    void deveLancarNotFoundException_QuandoRestauranteNaoEncontrado() {
        when(restauranteGateway.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> excluirItemUseCase.executar(1L, 1L, 1L));
    }

    @Test
    void deveLancarForbiddenException_QuandoUsuarioNaoProprietario() {
        Restaurante restaurante = Restaurante.builder().id(1L).proprietarioId(2L).build();
        when(restauranteGateway.buscarPorId(1L)).thenReturn(Optional.of(restaurante));

        assertThrows(ForbiddenException.class, () -> excluirItemUseCase.executar(1L, 1L, 1L));
    }

    @Test
    void deveLancarForbiddenException_QuandoItemNaoEncontrado() {
        Restaurante restaurante = Restaurante.builder().id(1L).proprietarioId(1L).build();
        when(restauranteGateway.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(itemGateway.buscarPorIdEPorRestauranteId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(ForbiddenException.class, () -> excluirItemUseCase.executar(1L, 1L, 1L));
    }
}
