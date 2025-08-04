package io.github.restaurantes_api.application.usecases.restaurante;

import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.exceptions.ForbiddenException;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ExcluirRestauranteUseCaseImplTest {

    @InjectMocks
    private ExcluirRestauranteUseCaseImpl useCase;

    @Mock
    private RestauranteGateway gateway;

    @Mock
    private UsuarioServiceGateway usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveExcluirRestaurante_ComSucesso() {
        Restaurante restaurante = Restaurante.builder().id(1L).proprietarioId(1L).build();

        when(gateway.buscarPorId(1L)).thenReturn(Optional.of(restaurante));

        useCase.executar(1L, 1L);

        verify(usuarioService).validarUsuarioAutenticado(1L);
        verify(gateway).excluir(restaurante);
    }

    @Test
    void deveLancarNotFoundException_QuandoNaoEncontrado() {
        when(gateway.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.executar(1L, 1L));
    }

    @Test
    void deveLancarForbiddenException_QuandoNaoProprietario() {
        Restaurante restaurante = Restaurante.builder().id(1L).proprietarioId(2L).build();
        when(gateway.buscarPorId(1L)).thenReturn(Optional.of(restaurante));

        assertThrows(ForbiddenException.class, () -> useCase.executar(1L, 1L));
    }
}
