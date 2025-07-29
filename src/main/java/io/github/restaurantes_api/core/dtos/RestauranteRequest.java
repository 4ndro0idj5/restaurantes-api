package io.github.restaurantes_api.core.dtos;

import io.github.restaurantes_api.core.domain.entities.enums.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestauranteRequest {

    @NotBlank(message = "O nome do restaurante é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "A categoria não foi preenchida ou é inexistente.")
    private Categoria categoria;

    @NotBlank(message = "O horário de funcionamento é obrigatório")
    @Pattern(
            regexp = "^\\d{2}:\\d{2}h\\s-\\s\\d{2}:\\d{2}h$",
            message = "O horário de funcionamento deve estar no formato HH:mmh - HH:mmh (ex: 11:30h - 23:00h)"
    )
    private String horarioFuncionamento;

    @NotNull(message = "O ID do proprietário é obrigatório")
    private Long proprietarioId;

    @NotNull(message = "O endereço é obrigatório")
    private EnderecoRequest enderecoRequest;
}

