package io.github.restaurantes_api.core.domain.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) { super(message); }
}
