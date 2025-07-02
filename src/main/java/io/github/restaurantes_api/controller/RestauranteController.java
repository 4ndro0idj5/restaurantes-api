package io.github.restaurantes_api.controller;

import io.github.restaurantes_api.dto.RestauranteRequest;
import io.github.restaurantes_api.dto.RestauranteResponse;
import io.github.restaurantes_api.services.RestauranteService;
import io.github.restaurantes_api.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<RestauranteResponse>> listar() {
        return ResponseEntity.ok(restauranteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(restauranteService.buscarPorId(id));
    }
}
