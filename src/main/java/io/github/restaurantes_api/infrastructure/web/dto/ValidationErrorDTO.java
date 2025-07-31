package io.github.restaurantes_api.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationErrorDTO {
    private List<String> errors;
    private int status;
}