package io.github.restaurantes_api.dto;

import lombok.Data;

@Data
public class EnderecoRequest {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;
}
