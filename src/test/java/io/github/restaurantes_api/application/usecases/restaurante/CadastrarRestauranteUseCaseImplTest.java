package io.github.restaurantes_api.application.usecases.restaurante;

import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class CadastrarRestauranteUseCaseImplTest {

    @InjectMocks
    private CadastrarRestauranteUseCaseImpl useCase;

    @Mock
    private RestauranteGateway gateway;

    @Mock
    private UsuarioServiceGateway usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarRestaurante_ComSucesso() {
        Restaurante restaurante = Restaurante.builder().proprietarioId(1L).build();

        useCase.executar(restaurante);

        verify(usuarioService).validarUsuarioAutenticadoEProprietario(1L);
        verify(gateway).salvar(restaurante);
    }
}
