package io.github.restaurantes_api.infrastructure.api.controllers;

import io.github.restaurantes_api.core.controllers.RestauranteController;
import io.github.restaurantes_api.core.dtos.RestauranteRequest;
import io.github.restaurantes_api.core.dtos.RestauranteResponse;
import io.github.restaurantes_api.core.dtos.RestauranteUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurantes")
@RequiredArgsConstructor
public class RestauranteRestController {

    private final RestauranteController controller;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody RestauranteRequest request) {
        controller.cadastrar(request);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<RestauranteResponse>> listar(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(controller.listar(idUsuario));
    }

    @GetMapping("/{id}/usuario/{idUsuario}")
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id, @PathVariable Long idUsuario) {
        return ResponseEntity.ok(controller.buscarPorId(id, idUsuario));
    }

    @DeleteMapping("/{id}/usuario/{idUsuario}")
    public ResponseEntity<Void> excluir(@PathVariable Long id, @PathVariable Long idUsuario) {
        controller.excluir(id, idUsuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/usuario/{idUsuario}")
    public ResponseEntity<RestauranteResponse> atualizar(@RequestBody RestauranteUpdateDTO dto,
                                                         @PathVariable Long id,
                                                         @PathVariable Long idUsuario) {
        return ResponseEntity.ok(controller.atualizar(dto, id, idUsuario));
    }
}
