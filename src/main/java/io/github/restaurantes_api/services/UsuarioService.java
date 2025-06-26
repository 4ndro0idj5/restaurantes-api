package io.github.restaurantes_api.services;

import io.github.restaurantes_api.dto.UsuarioResponse;
import io.github.restaurantes_api.entities.Perfil;
import org.springframework.beans.factory.annotation.Value;
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

    public UsuarioService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void validarUsuarioAutenticadoEProprietario(Long id) {
        String url = usuariosApiUrl + "/" + id;
        try {
            UsuarioResponse usuario = restTemplate.getForObject(url, UsuarioResponse.class);

            if (usuario == null || !usuario.isAutenticado() || usuario.getPerfil() != Perfil.PROPRIETARIO) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não autorizado");
            }
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado");
        }
    }

    public UsuarioResponse buscarUsuarioPorId(Long id) {
        String url = usuariosApiUrl + "/" + id;
        try {
            return restTemplate.getForObject(url, UsuarioResponse.class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado");
        }
    }

}