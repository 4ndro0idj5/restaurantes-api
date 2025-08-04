package io.github.restaurantes_api.application.usecases.restaurante;

import io.github.restaurantes_api.core.domain.entities.Endereco;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.entities.enums.Categoria;
import io.github.restaurantes_api.core.domain.exceptions.ForbiddenException;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.dtos.EnderecoRequest;
import io.github.restaurantes_api.core.dtos.RestauranteUpdateDTO;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AtualizarRestauranteUseCaseImplTest {

    @InjectMocks
    private AtualizarRestauranteUseCaseImpl useCase;

    @Mock
    private RestauranteGateway gateway;

    @Mock
    private UsuarioServiceGateway usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAtualizarRestaurante_ComSucesso() {
        Long restauranteId = 1L;
        Long usuarioId = 2L;

        Endereco endereco = Endereco.builder().rua("Antiga").build();
        Restaurante restaurante = Restaurante.builder().id(restauranteId).proprietarioId(usuarioId).endereco(endereco).build();

        RestauranteUpdateDTO dto = RestauranteUpdateDTO.builder()
                .nome("Novo Nome")
                .categoria(Categoria.BRASILEIRA)
                .horarioFuncionamento("08:00 - 18:00")
                .enderecoRequest(EnderecoRequest.builder().rua("Nova Rua").build())
                .build();

        when(gateway.buscarPorId(restauranteId)).thenReturn(Optional.of(restaurante));
        when(gateway.salvar(any())).thenReturn(restaurante);

        useCase.executar(dto, restauranteId, usuarioId);

        verify(usuarioService).validarUsuarioAutenticado(usuarioId);
        verify(gateway).salvar(restaurante);
    }

    @Test
    void deveLancarNotFoundException_QuandoNaoEncontrado() {
        when(gateway.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> useCase.executar(RestauranteUpdateDTO.builder().build(), 1L, 1L));
    }

    @Test
    void deveLancarForbiddenException_QuandoNaoProprietario() {
        Restaurante restaurante = Restaurante.builder().id(1L).proprietarioId(99L).build();
        when(gateway.buscarPorId(1L)).thenReturn(Optional.of(restaurante));

        assertThrows(ForbiddenException.class,
                () -> useCase.executar(RestauranteUpdateDTO.builder().build(), 1L, 1L));
    }
}
