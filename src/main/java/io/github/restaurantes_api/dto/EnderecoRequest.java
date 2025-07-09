package io.github.restaurantes_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoRequest {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;
}
