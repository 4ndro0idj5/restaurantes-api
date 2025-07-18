package io.github.restaurantes_api.controller;

import io.github.restaurantes_api.docs.RestauranteApiDoc;
import io.github.restaurantes_api.dto.RestauranteRequest;
import io.github.restaurantes_api.dto.RestauranteResponse;
import io.github.restaurantes_api.dto.RestauranteUpdateDTO;
import io.github.restaurantes_api.services.RestauranteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurantes")
@AllArgsConstructor
public class RestauranteController implements RestauranteApiDoc {

    private final RestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody RestauranteRequest request) {
        restauranteService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<RestauranteResponse>> listar(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(restauranteService.listarTodos(idUsuario));
    }

    @GetMapping("/{id}/usuario/{idUsuario}")
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id, @PathVariable Long idUsuario) {
        return ResponseEntity.ok(restauranteService.buscarPorId(id, idUsuario));
    }

    @DeleteMapping("/{id}/usuario/{idUsuario}")
    public ResponseEntity<Void> excluir(@PathVariable Long id, @PathVariable Long idUsuario) {
        restauranteService.excluir(id, idUsuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/usuario/{idUsuario}")
    public ResponseEntity<RestauranteResponse> atualizar(@RequestBody RestauranteUpdateDTO dto,
                                                         @PathVariable Long id,
                                                         @PathVariable Long idUsuario) {
        RestauranteResponse atualizado = restauranteService.atualizar(dto, id, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(atualizado);
    }
}
