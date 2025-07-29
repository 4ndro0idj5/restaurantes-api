package io.github.restaurantes_api.core.gateways;

public interface UsuarioServiceGateway {
    void validarUsuarioAutenticadoEProprietario(Long id);
    void validarUsuarioAutenticado(Long id);
}

