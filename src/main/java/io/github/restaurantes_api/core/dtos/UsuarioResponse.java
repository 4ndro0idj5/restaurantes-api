package io.github.restaurantes_api.core.dtos;


import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private Perfil perfil;
    private boolean autenticado;

    public enum Perfil {
        PROPRIETARIO, CLIENTE
    }
}
