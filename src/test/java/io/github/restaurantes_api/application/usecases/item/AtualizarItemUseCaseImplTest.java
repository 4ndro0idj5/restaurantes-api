package io.github.restaurantes_api.application.usecases.item;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.exceptions.ForbiddenException;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.dtos.ItemUpdateDTO;
import io.github.restaurantes_api.core.gateways.ItemGateway;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AtualizarItemUseCaseImplTest {

    @InjectMocks
    private AtualizarItemUseCaseImpl atualizarItemUseCase;

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
    void deveAtualizarItem_ComSucesso() {
        Long restauranteId = 1L;
        Long itemId = 1L;
        Long usuarioId = 2L;

        Restaurante restaurante = Restaurante.builder().id(restauranteId).proprietarioId(usuarioId).build();
        Item item = Item.builder().id(itemId).restauranteId(restauranteId).nome("Pizza").build();
        ItemUpdateDTO dto = ItemUpdateDTO.builder().nome("Pizza Atualizada").build();

        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.of(restaurante));
        when(itemGateway.buscarPorIdEPorRestauranteId(itemId, restauranteId)).thenReturn(Optional.of(item));

        atualizarItemUseCase.executar(restauranteId, dto, itemId, usuarioId);

        verify(usuarioService).validarUsuarioAutenticado(usuarioId);
        verify(itemGateway).salvar(any(Item.class));
    }

    @Test
    void deveLancarNotFoundException_QuandoRestauranteNaoEncontrado() {
        when(restauranteGateway.buscarPorId(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> atualizarItemUseCase.executar(1L, ItemUpdateDTO.builder().build(), 1L, 1L));
    }

    @Test
    void deveLancarForbiddenException_QuandoUsuarioNaoForProprietario() {
        Restaurante restaurante = Restaurante.builder().id(1L).proprietarioId(99L).build();
        when(restauranteGateway.buscarPorId(1L)).thenReturn(Optional.of(restaurante));

        assertThrows(ForbiddenException.class, () -> atualizarItemUseCase.executar(1L, ItemUpdateDTO.builder().build(), 1L, 1L));
    }

    @Test
    void deveLancarNotFoundException_QuandoItemNaoEncontrado() {
        Restaurante restaurante = Restaurante.builder().id(1L).proprietarioId(1L).build();
        when(restauranteGateway.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(itemGateway.buscarPorIdEPorRestauranteId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> atualizarItemUseCase.executar(1L, ItemUpdateDTO.builder().build(), 1L, 1L));
    }
}
