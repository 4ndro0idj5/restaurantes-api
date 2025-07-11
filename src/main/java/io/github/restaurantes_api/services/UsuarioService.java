package io.github.restaurantes_api.services;

import io.github.restaurantes_api.dto.UsuarioResponse;
import io.github.restaurantes_api.entities.Perfil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    @Value("${api.usuarios.url}")
    private String usuariosApiUrl;

    private final RestTemplate restTemplate;

    public UsuarioService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public void validarUsuarioAutenticadoEProprietario(Long id) {
        UsuarioResponse usuario = obterUsuarioOuErro(id);

        if (!usuario.isAutenticado() || usuario.getPerfil() != Perfil.PROPRIETARIO) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não autorizado");
        }
    }

    public void validarUsuarioAutenticado(Long id) {
        UsuarioResponse usuario = obterUsuarioOuErro(id);

        if (!usuario.isAutenticado()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não autenticado");
        }
    }


    private UsuarioResponse obterUsuarioOuErro(Long id) {
        String url = usuariosApiUrl + "/" + id;
        try {
            UsuarioResponse usuario = restTemplate.getForObject(url, UsuarioResponse.class);
            if (usuario == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado");
            }
            return usuario;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado");
        }
    }
}