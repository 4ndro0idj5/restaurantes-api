package io.github.restaurantes_api.core.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDTO {

    @NotBlank(message = "O nome do item é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "A descrição do item é obrigatória")
    @Size(min = 5, max = 255, message = "A descrição deve ter entre 5 e 255 caracteres")
    private String descricao;

    @Pattern(
            regexp = "([1-9]\\d*|0)\\.\\d{2}",
            message = "O preço deve conter apenas dígitos, ponto decimal e exatamente duas casas decimais (ex: 10.00)"
    )
    private String preco;

    @NotBlank(message = "A URL da foto é obrigatória")
    @Size(max = 255, message = "A URL da foto deve ter no máximo 255 caracteres")
    private String foto;

    @NotNull(message = "Informe se o item é para consumo local ou não")
    private Boolean consumoLocal;
}
