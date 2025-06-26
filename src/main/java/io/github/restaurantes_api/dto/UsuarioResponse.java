package io.github.restaurantes_api.dto;

import io.github.restaurantes_api.entities.Perfil;
import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private Perfil perfil;
    private boolean autenticado;
}
