package io.github.restaurantes_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDTO {
    private String nome;
    private String descricao;
    private double preco;
    private String foto;
    private boolean consumoLocal;
}

