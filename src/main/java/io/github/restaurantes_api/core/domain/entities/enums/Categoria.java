package io.github.restaurantes_api.core.domain.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Categoria {
    ITALIANA,
    JAPONESA,
    FASTFOOD,
    PORTUGUESA,
    BRASILEIRA,
    PERUANA;

    @JsonCreator
    public static Categoria from(String value) {
        for (Categoria categoria : values()) {
            if (categoria.name().equalsIgnoreCase(value)) {
                return categoria;
            }
        }

        return null;
    }
}
