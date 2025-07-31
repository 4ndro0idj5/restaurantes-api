package io.github.restaurantes_api.infrastructure.controllers.docs;


import io.github.restaurantes_api.core.dtos.RestauranteRequest;
import io.github.restaurantes_api.core.dtos.RestauranteResponse;
import io.github.restaurantes_api.core.dtos.RestauranteUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Restaurante", description = "Gerenciamento de Restaurantes")
public interface RestauranteApiDoc {

    @Operation(summary = "Cadastrar um novo restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    ResponseEntity<Void> cadastrar(RestauranteRequest request);

    @Operation(summary = "Listar todos os restaurantes de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    ResponseEntity<List<RestauranteResponse>> listar(Long idUsuario);

    @Operation(summary = "Buscar restaurante por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    ResponseEntity<RestauranteResponse> buscarPorId(Long id, Long idUsuario);

    @Operation(summary = "Excluir restaurante por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    ResponseEntity<Void> excluir(Long id, Long idUsuario);

    @Operation(summary = "Atualizar restaurante por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    ResponseEntity<Void> atualizar(RestauranteUpdateDTO dto, Long id, Long idUsuario);
}
