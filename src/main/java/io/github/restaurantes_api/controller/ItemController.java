package io.github.restaurantes_api.controller;

import io.github.restaurantes_api.dto.ItemDTO;
import io.github.restaurantes_api.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurantes/{restauranteId}/itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDTO> cadastrarItem(@PathVariable Long restauranteId, @RequestBody ItemDTO item) {
        ItemDTO novoItem = itemService.cadastrarItem(restauranteId, item);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> listarItens(@PathVariable Long restauranteId) {
        List<ItemDTO> itens = itemService.listarItensPorRestaurante(restauranteId);
        return ResponseEntity.ok(itens);
    }
}

