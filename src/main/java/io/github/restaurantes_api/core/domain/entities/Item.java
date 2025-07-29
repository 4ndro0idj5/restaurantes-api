package io.github.restaurantes_api.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private Long id;
    private String nome;
    private String descricao;
    private String preco;
    private String foto;
    private boolean consumoLocal;
    private Long restauranteId;
}
