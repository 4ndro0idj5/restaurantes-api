package io.github.restaurantes_api.infrastructure;

import io.github.restaurantes_api.core.dtos.UsuarioResponse;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UsuarioServiceClient implements UsuarioServiceGateway {

    @Value("${api.usuarios.url}")
    private String usuariosApiUrl;

    private final RestTemplate restTemplate;

    public UsuarioServiceClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public void validarUsuarioAutenticadoEProprietario(Long id) {
        UsuarioResponse usuario = obterUsuarioOuErro(id);

        if (!usuario.isAutenticado() || usuario.getPerfil() != UsuarioResponse.Perfil.PROPRIETARIO) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autorizado");
        }
    }

    @Override
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
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
            }
            return usuario;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
        }
    }
}
