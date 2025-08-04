package io.github.restaurantes_api.infrastructure.controllers;

import io.github.restaurantes_api.core.dtos.ItemDTO;
import io.github.restaurantes_api.core.dtos.ItemUpdateDTO;
import io.github.restaurantes_api.application.presenters.ItemPresenter;
import io.github.restaurantes_api.infrastructure.controllers.docs.ItemApiDoc;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/itens")
@RequiredArgsConstructor
public class ItemController implements ItemApiDoc {

    private final ItemPresenter itemPresenter;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> cadastrarItem(
            @PathVariable Long restauranteId,
            @Valid @RequestBody ItemDTO itemRequest,
            @PathVariable Long usuarioId) {

        itemPresenter.cadastrar(restauranteId, itemRequest, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ItemDTO>> listarItens(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId) {

        List<ItemDTO> itens = itemPresenter.listar(restauranteId, usuarioId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/{id}/usuario/{usuarioId}")
    public ResponseEntity<ItemDTO> buscarPorId(
            @PathVariable Long restauranteId,
            @PathVariable Long id,
            @PathVariable Long usuarioId) {

        return itemPresenter.buscarPorId(id, restauranteId, usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/usuario/{usuarioId}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long restauranteId,
            @Valid @RequestBody ItemUpdateDTO dto,
            @PathVariable Long id,
            @PathVariable Long usuarioId
            ) {

        itemPresenter.atualizar(restauranteId, id, dto, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/usuario/{usuarioId}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long restauranteId,
            @PathVariable Long id,
            @PathVariable Long usuarioId) {

        itemPresenter.excluir(restauranteId, id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
