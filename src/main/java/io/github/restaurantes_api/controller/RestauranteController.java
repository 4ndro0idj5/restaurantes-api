package io.github.restaurantes_api.controller;

import io.github.restaurantes_api.dto.RestauranteRequest;
import io.github.restaurantes_api.services.RestauranteService;
import io.github.restaurantes_api.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurantes")
@AllArgsConstructor
public class RestauranteController {

    private final RestauranteService restauranteService;
    private final UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody RestauranteRequest request) {

        restauranteService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
