package io.github.restaurantes_api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import io.github.restaurantes_api.dto.*;
import io.github.restaurantes_api.entities.Categoria;
import io.github.restaurantes_api.entities.Endereco;
import io.github.restaurantes_api.entities.Item;
import io.github.restaurantes_api.entities.Restaurante;
import io.github.restaurantes_api.mapper.ItemMapper;
import io.github.restaurantes_api.mapper.RestauranteMapper;
import io.github.restaurantes_api.respositories.EnderecoRepository;
import io.github.restaurantes_api.respositories.ItemRepository;
import io.github.restaurantes_api.respositories.RestauranteRepository;
import org.junit.jupiter.api.DisplayName;
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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

class RestauranteServiceTest {

    @InjectMocks
    private RestauranteService restauranteService;

    @Mock
    private RestauranteMapper restauranteMapper;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarRestaurante() {
        RestauranteRequest request = RestauranteRequest.builder()
                .nome("Bella Napoli")
                .categoria(Categoria.ITALIANA)
                .horarioFuncionamento("18:00 - 23:00")
                .proprietarioId(1L)
                .enderecoRequest(EnderecoRequest.builder().rua("Rua A").build())
                .build();

        Restaurante restaurante = Restaurante.builder().id(1L).build();

        when(restauranteMapper.fromDTO(request)).thenReturn(restaurante);
        when(restauranteRepository.save(restaurante)).thenReturn(restaurante);

        Restaurante resultado = restauranteService.cadastrar(request);

        verify(usuarioService).validarUsuarioAutenticadoEProprietario(1L);
        verify(restauranteRepository).save(restaurante);

        assertThat(resultado).isNotNull();
    }

    @Test
    void deveListarTodosOsRestaurantes() {
        Restaurante restaurante = Restaurante.builder().nome("Bella Napoli").build();
        RestauranteResponse response = RestauranteResponse.builder().nome("Bella Napoli").build();

        when(restauranteRepository.findAll()).thenReturn(List.of(restaurante));
        when(restauranteMapper.toResponseDTO(restaurante)).thenReturn(response);

        List<RestauranteResponse> resultado = restauranteService.listarTodos(1L);

        verify(usuarioService).validarUsuarioAutenticado(1L);
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Bella Napoli");
    }

    @Test
    void deveBuscarRestaurantePorId() {
        Long id = 1L;
        Restaurante restaurante = Restaurante.builder().nome("Bella Napoli").build();
        RestauranteResponse response = RestauranteResponse.builder().nome("Bella Napoli").build();

        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));
        when(restauranteMapper.toResponseDTO(restaurante)).thenReturn(response);

        RestauranteResponse resultado = restauranteService.buscarPorId(id, 2L);

        verify(usuarioService).validarUsuarioAutenticado(2L);
        assertThat(resultado.getNome()).isEqualTo("Bella Napoli");
    }

    @Test
    void deveExcluirRestaurante() {
        Long id = 1L;
        Long idUsuario = 2L;
        Restaurante restaurante = Restaurante.builder()
                .id(id)
                .proprietarioId(idUsuario)
                .build();

        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));

        restauranteService.excluir(id, idUsuario);

        verify(usuarioService).validarUsuarioAutenticado(idUsuario);
        verify(restauranteRepository).delete(restaurante);
    }

    @Test
    void deveAtualizarRestaurante() {
        Long id = 1L;
        Long idUsuario = 2L;
        Endereco endereco = Endereco.builder().id(1L).build();

        Restaurante restaurante = Restaurante.builder()
                .id(id)
                .nome("Antigo")
                .proprietarioId(idUsuario)
                .endereco(endereco)
                .build();

        RestauranteUpdateDTO dto = RestauranteUpdateDTO.builder()
                .nome("Novo Nome")
                .categoria(Categoria.ITALIANA)
                .horarioFuncionamento("10:00 - 22:00")
                .enderecoRequest(EnderecoRequest.builder().rua("Nova Rua").build())
                .build();

        Restaurante atualizado = Restaurante.builder()
                .nome("Novo Nome")
                .categoria(Categoria.ITALIANA)
                .horarioFuncionamento("10:00 - 22:00")
                .endereco(endereco)
                .build();

        RestauranteResponse response = RestauranteResponse.builder().nome("Novo Nome").build();

        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));
        when(enderecoRepository.findById(endereco.getId())).thenReturn(Optional.of(endereco));
        when(restauranteRepository.save(restaurante)).thenReturn(atualizado);
        when(restauranteMapper.toResponseDTO(atualizado)).thenReturn(response);

        RestauranteResponse resultado = restauranteService.atualizar(dto, id, idUsuario);

        verify(usuarioService).validarUsuarioAutenticado(idUsuario);
        assertThat(resultado.getNome()).isEqualTo("Novo Nome");
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não é encontrado ao buscar por ID")
    void deveLancarExcecaoQuandoRestauranteNaoEncontradoAoBuscarPorId() {
        Long id = 1L;
        when(restauranteRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restauranteService.buscarPorId(id, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Restaurante não encontrado");

        verify(usuarioService).validarUsuarioAutenticado(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não é encontrado ao excluir")
    void deveLancarExcecaoQuandoRestauranteNaoEncontradoAoExcluir() {
        Long id = 1L;
        when(restauranteRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restauranteService.excluir(id, 2L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Restaurante não encontrado");

        verify(usuarioService).validarUsuarioAutenticado(2L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não tem permissão para excluir restaurante")
    void deveLancarExcecaoQuandoUsuarioNaoTemPermissaoParaExcluir() {
        Long id = 1L;
        Long idUsuario = 99L;

        Restaurante restaurante = Restaurante.builder()
                .id(id)
                .proprietarioId(2L)
                .build();

        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));

        assertThatThrownBy(() -> restauranteService.excluir(id, idUsuario))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Usuário não tem permissão para excluir este restaurante");

        verify(usuarioService).validarUsuarioAutenticado(idUsuario);
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não é encontrado ao atualizar")
    void deveLancarExcecaoQuandoRestauranteNaoEncontradoAoAtualizar() {
        Long id = 1L;
        when(restauranteRepository.findById(id)).thenReturn(Optional.empty());

        RestauranteUpdateDTO dto = RestauranteUpdateDTO.builder().build();

        assertThatThrownBy(() -> restauranteService.atualizar(dto, id, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Restaurante não encontrado");

        verify(usuarioService).validarUsuarioAutenticado(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não tem permissão para atualizar restaurante")
    void deveLancarExcecaoQuandoUsuarioNaoTemPermissaoParaAtualizar() {
        Long id = 1L;
        Long idUsuario = 99L;

        Endereco endereco = Endereco.builder().id(1L).build();

        Restaurante restaurante = Restaurante.builder()
                .id(id)
                .proprietarioId(2L)
                .endereco(endereco)
                .build();

        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));

        RestauranteUpdateDTO dto = RestauranteUpdateDTO.builder()
                .enderecoRequest(EnderecoRequest.builder().build())
                .build();

        assertThatThrownBy(() -> restauranteService.atualizar(dto, id, idUsuario))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Usuário não tem permissão para atualizar este restaurante");

        verify(usuarioService).validarUsuarioAutenticado(idUsuario);
    }

    @Test
    @DisplayName("Deve lançar exceção quando endereço não é encontrado ao atualizar restaurante")
    void deveLancarExcecaoQuandoEnderecoNaoEncontradoAoAtualizar() {
        Long id = 1L;
        Long idUsuario = 2L;

        Endereco endereco = Endereco.builder().id(1L).build();

        Restaurante restaurante = Restaurante.builder()
                .id(id)
                .proprietarioId(idUsuario)
                .endereco(endereco)
                .build();

        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));
        when(enderecoRepository.findById(endereco.getId())).thenReturn(Optional.empty());

        RestauranteUpdateDTO dto = RestauranteUpdateDTO.builder()
                .enderecoRequest(EnderecoRequest.builder().build())
                .build();

        assertThatThrownBy(() -> restauranteService.atualizar(dto, id, idUsuario))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Endereço não encontrado");

        verify(usuarioService).validarUsuarioAutenticado(idUsuario);
    }
}