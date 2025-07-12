package io.github.restaurantes_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoRequest {

    @NotBlank(message = "A rua é obrigatória")
    @Size(min = 3, max = 100, message = "A rua deve ter entre 3 e 100 caracteres")
    private String rua;

    @NotBlank(message = "O número é obrigatório")
    @Size(max = 10, message = "O número deve ter no máximo 10 caracteres")
    private String numero;

    @NotBlank(message = "O bairro é obrigatório")
    @Size(min = 3, max = 50, message = "O bairro deve ter entre 3 e 50 caracteres")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(min = 3, max = 50, message = "A cidade deve ter entre 3 e 50 caracteres")
    private String cidade;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter 8 dígitos numéricos (ex: 12345678)")
    private String cep;
}

