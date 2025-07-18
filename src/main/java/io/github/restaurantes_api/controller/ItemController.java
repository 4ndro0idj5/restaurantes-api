package io.github.restaurantes_api.controller;

import io.github.restaurantes_api.docs.ItemApiDoc;
import io.github.restaurantes_api.dto.ItemDTO;
import io.github.restaurantes_api.dto.ItemUpdateDTO;
import io.github.restaurantes_api.services.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurantes/{restauranteId}/itens")
public class ItemController implements ItemApiDoc {

    @Autowired
    private ItemService itemService;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<ItemDTO> cadastrarItem(
            @PathVariable Long restauranteId,
            @Valid @RequestBody ItemDTO item,
            @PathVariable Long usuarioId) {

        ItemDTO novoItem = itemService.cadastrarItem(restauranteId, item, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ItemDTO>> listarItens(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId) {

        List<ItemDTO> itens = itemService.listarItensPorRestaurante(restauranteId, usuarioId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/{id}/usuario/{idUsuario}")
    public ResponseEntity<ItemDTO> buscarPorId(
            @PathVariable Long id,
            @PathVariable Long restauranteId,
            @PathVariable Long idUsuario) {

        return itemService.buscarPorId(id, restauranteId, idUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/usuario/{idUsuario}")
    public ResponseEntity<ItemUpdateDTO> atualizar(
            @PathVariable Long restauranteId,
            @Valid @RequestBody ItemUpdateDTO dto,
            @PathVariable Long id,
            @PathVariable Long idUsuario) {

        ItemUpdateDTO atualizado = itemService.atualizar(restauranteId, dto, id, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(atualizado);
    }

    @DeleteMapping("/{id}/usuario/{idUsuario}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long restauranteId,
            @PathVariable Long id,
            @PathVariable Long idUsuario) {

        itemService.excluir(restauranteId, id, idUsuario);
        return ResponseEntity.noContent().build();
    }
}

