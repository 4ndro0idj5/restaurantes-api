package io.github.restaurantes_api.docs;

import io.github.restaurantes_api.dto.ItemDTO;
import io.github.restaurantes_api.dto.ItemUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Itens", description = "Gerenciamento de Itens do Cardápio")
public interface ItemApiDoc {

    @Operation(summary = "Cadastrar um novo item",
            description = "Permite cadastrar um novo item no cardápio de um restaurante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    ResponseEntity<ItemDTO> cadastrarItem(
            @Parameter(description = "ID do restaurante") Long restauranteId,
            @Valid ItemDTO item,
            @Parameter(description = "ID do usuário proprietário") Long usuarioId);

    @Operation(summary = "Listar todos os itens do restaurante",
            description = "Retorna todos os itens cadastrados para um restaurante específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens listados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class)))
    })
    ResponseEntity<List<ItemDTO>> listarItens(
            @Parameter(description = "ID do restaurante") Long restauranteId,
            @Parameter(description = "ID do usuário autenticado") Long usuarioId);

    @Operation(summary = "Buscar item por ID",
            description = "Busca os detalhes de um item específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content)
    })
    ResponseEntity<ItemDTO> buscarPorId(
            @Parameter(description = "ID do item") Long id,
            @Parameter(description = "ID do restaurante") Long restauranteId,
            @Parameter(description = "ID do usuário autenticado") Long idUsuario);

    @Operation(summary = "Atualizar um item",
            description = "Atualiza as informações de um item específico do cardápio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemUpdateDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    ResponseEntity<ItemUpdateDTO> atualizar(
            @Parameter(description = "ID do restaurante") Long restauranteId,
            @Valid ItemUpdateDTO dto,
            @Parameter(description = "ID do item") Long id,
            @Parameter(description = "ID do usuário autenticado") Long idUsuario);

    @Operation(summary = "Excluir um item",
            description = "Remove um item específico do cardápio do restaurante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content)
    })
    ResponseEntity<Void> excluir(
            @Parameter(description = "ID do restaurante") Long restauranteId,
            @Parameter(description = "ID do item") Long id,
            @Parameter(description = "ID do usuário autenticado") Long idUsuario);
}
