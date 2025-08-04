package io.github.restaurantes_api.application.usecases.restaurante;

import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ListarRestaurantesUseCaseImplTest {

    @InjectMocks
    private ListarRestaurantesUseCaseImpl useCase;

    @Mock
    private RestauranteGateway gateway;

    @Mock
    private UsuarioServiceGateway usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarRestaurantes_ComSucesso() {
        when(gateway.listarTodos()).thenReturn(Collections.singletonList(new Restaurante()));

        var lista = useCase.executar(1L);

        assertEquals(1, lista.size());
        verify(usuarioService).validarUsuarioAutenticado(1L);
    }
}
