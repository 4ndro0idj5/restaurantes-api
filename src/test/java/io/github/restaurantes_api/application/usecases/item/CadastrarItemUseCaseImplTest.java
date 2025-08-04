package io.github.restaurantes_api.application.usecases.item;

import io.github.restaurantes_api.application.mapper.ItemMapper;
import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.exceptions.ForbiddenException;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.dtos.ItemDTO;
import io.github.restaurantes_api.core.gateways.ItemGateway;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CadastrarItemUseCaseImplTest {

    @InjectMocks
    private CadastrarItemUseCaseImpl cadastrarItemUseCase;

    @Mock
    private RestauranteGateway restauranteGateway;

    @Mock
    private ItemGateway itemGateway;

    @Mock
    private UsuarioServiceGateway usuarioService;

    @Mock
    private ItemMapper itemMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarItem_ComSucesso() {
        // Arrange
        Long restauranteId = 1L;
        Long usuarioId = 2L;

        ItemDTO dto = ItemDTO.builder()
                .nome("Pizza")
                .descricao("Pizza Calabresa")
                .preco("25.00")
                .foto("https://url.com/foto.jpg")
                .consumoLocal(true)
                .build();

        Restaurante restaurante = Restaurante.builder()
                .id(restauranteId)
                .proprietarioId(usuarioId)
                .build();

        Item item = new Item();

        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.of(restaurante));
        when(itemMapper.fromDTO(dto)).thenReturn(item);

        // Act
        cadastrarItemUseCase.executar(restauranteId, dto, usuarioId);

        // Assert
        verify(usuarioService, times(1)).validarUsuarioAutenticado(restaurante.getProprietarioId());
        verify(itemGateway, times(1)).salvar(item);
    }

    @Test
    void deveLancarNotFoundException_QuandoRestauranteNaoEncontrado() {
        Long restauranteId = 1L;
        Long usuarioId = 2L;
        ItemDTO dto = ItemDTO.builder()
                .nome("Pizza")
                .descricao("Pizza Calabresa")
                .preco("25.00")
                .foto("https://url.com/foto.jpg")
                .consumoLocal(true)
                .build();

        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                cadastrarItemUseCase.executar(restauranteId, dto, usuarioId));

        verify(itemGateway, never()).salvar(any());
    }

    @Test
    void deveLancarForbiddenException_QuandoUsuarioNaoForProprietario() {
        Long restauranteId = 1L;
        Long proprietarioId = 10L;
        Long usuarioId = 2L;

        Restaurante restaurante = Restaurante.builder()
                .id(restauranteId)
                .proprietarioId(proprietarioId)
                .build();

        ItemDTO dto = ItemDTO.builder()
                .nome("Pizza")
                .descricao("Pizza Calabresa")
                .preco("25.00")
                .foto("https://url.com/foto.jpg")
                .consumoLocal(true)
                .build();

        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.of(restaurante));

        assertThrows(ForbiddenException.class, () ->
                cadastrarItemUseCase.executar(restauranteId, dto, usuarioId));

        verify(itemGateway, never()).salvar(any());
    }

}
