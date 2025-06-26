package io.github.restaurantes_api.controller;

import io.github.restaurantes_api.dto.RestauranteRequest;
import io.github.restaurantes_api.dto.RestauranteResponse;
import io.github.restaurantes_api.dto.UsuarioResponse;
import io.github.restaurantes_api.entities.Restaurante;
import io.github.restaurantes_api.services.RestauranteService;
import io.github.restaurantes_api.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;
    private final UsuarioService usuarioService;

    public RestauranteController(RestauranteService restauranteService, UsuarioService usuarioService) {
        this.restauranteService = restauranteService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<RestauranteResponse> cadastrar(@RequestBody RestauranteRequest request) {

        UsuarioResponse usuario = usuarioService.buscarUsuarioPorId(request.getProprietarioId());

        usuarioService.validarUsuarioAutenticadoEProprietario(request.getProprietarioId());
        Restaurante restaurante = restauranteService.cadastrar(request);

        RestauranteResponse response = new RestauranteResponse(
                "Restaurante cadastrado com sucesso!",
                restaurante.getNome(),
                usuario.getNome()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
