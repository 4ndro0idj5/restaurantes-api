package io.github.restaurantes_api.services;

import io.github.restaurantes_api.dto.UsuarioResponse;
import io.github.restaurantes_api.entities.Perfil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private RestTemplateBuilder builder;

    @Mock
    private RestTemplate restTemplate;

    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        when(builder.build()).thenReturn(restTemplate);
        usuarioService = new UsuarioService(builder);
    }

    @Test
    void devePermitirQuandoUsuarioAutenticadoEProprietario() {
        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setAutenticado(true);
        usuario.setPerfil(Perfil.PROPRIETARIO);

        when(restTemplate.getForObject(anyString(), eq(UsuarioResponse.class))).thenReturn(usuario);

        assertDoesNotThrow(() -> usuarioService.validarUsuarioAutenticadoEProprietario(1L));
    }

    @Test
    void deveLancarExcecaoQuandoNaoAutenticado() {
        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setAutenticado(false);
        usuario.setPerfil(Perfil.CLIENTE);

        when(restTemplate.getForObject(anyString(), eq(UsuarioResponse.class))).thenReturn(usuario);

        assertThrows(ResponseStatusException.class, () -> usuarioService.validarUsuarioAutenticadoEProprietario(1L));
    }
}