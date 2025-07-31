package io.github.restaurantes_api.infrastructure.data.gateways;


import io.github.restaurantes_api.core.domain.exceptions.ForbiddenException;
import io.github.restaurantes_api.core.domain.exceptions.NotFoundException;
import io.github.restaurantes_api.core.domain.exceptions.UnauthorizedException;
import io.github.restaurantes_api.core.dtos.UsuarioResponse;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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

        if (!usuario.isAutenticado()) {
            throw new UnauthorizedException("Usuário não autenticado");
        }

        if (usuario.getPerfil() != UsuarioResponse.Perfil.PROPRIETARIO) {
            throw new ForbiddenException("Usuário não é proprietário");
        }
    }

    @Override
    public void validarUsuarioAutenticado(Long id) {
        UsuarioResponse usuario = obterUsuarioOuErro(id);

        if (!usuario.isAutenticado()) {
            throw new ForbiddenException("Usuário não autenticado");
        }
    }

    private UsuarioResponse obterUsuarioOuErro(Long id) {
        String url = usuariosApiUrl + "/" + id;
        try {
            UsuarioResponse usuario = restTemplate.getForObject(url, UsuarioResponse.class);
            if (usuario == null) {
                throw new NotFoundException("Usuário não encontrado");
            }
            return usuario;
        } catch (HttpClientErrorException e) {
            throw new NotFoundException("Usuário não encontrado");
        }
    }
}
