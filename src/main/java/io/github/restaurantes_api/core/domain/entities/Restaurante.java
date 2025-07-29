package io.github.restaurantes_api.core.domain.entities;


import io.github.restaurantes_api.core.domain.entities.enums.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurante {
    private Long id;
    private String nome;
    private Categoria categoria;
    private String horarioFuncionamento;
    private Long proprietarioId;
    private Endereco endereco;
    private List<Item> itens;
}
