package io.github.restaurantes_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationErrorDTO {
    List<String> erros;
    int status;
}